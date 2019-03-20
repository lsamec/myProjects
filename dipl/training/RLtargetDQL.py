from keras.models import Sequential
from keras.layers.core import Dense, Dropout
from keras.optimizers import RMSprop
from keras.models import load_model, clone_model
import random
import numpy as np
import game

#opponentConstr is None = selfPlay, oppModel fixed
#startingModel is None = from scratch
def trainModelWithDQL(p_boardSizeX, p_boardSizeY, p_episodes, savePath, p_temperature=20.0, p_gamma=0.9, fixedTargetSteps=500, howFar=2,rewardFunc=game.rewardFunc2, selfPlayFixOpp=False, startingModel=None, opponentConstr=None, opponentStrength=None):

    model = startingModel

    if startingModel is None:
        model = Sequential()
        model.add(Dense(70, kernel_initializer='lecun_uniform', activation='relu', input_shape=(2*(4+(2*howFar+1)*(2*howFar+1)),)))

        model.add(Dense(35, kernel_initializer='lecun_uniform', activation='relu'))

        model.add(Dense(3, kernel_initializer='lecun_uniform', activation='linear'))

        model.compile(loss='mean_squared_error', optimizer=RMSprop())

    episodes = p_episodes
    gamma = p_gamma
    startingTemperature = p_temperature
    temperature = p_temperature
    batchSize = 50
    buffer = 500
    replay = []
    h = 0
    targetModel = None
    updateStep = 0
    for i in range(episodes):

        #init board
        board = game.Board(p_boardSizeX,p_boardSizeY)

        #set opponent
        if(opponentConstr is None):
            if(selfPlayFixOpp == True):
                oppModel = clone_model(model)
                oppModel.set_weights(model.get_weights())
                board.setPlayers(game.AITrainingReduFeatWOPlayer("1",board,model,howFar), game.AITrainingReduFeatWOPlayer("2",board,oppModel,howFar))
            else:
                 board.setPlayers(game.AITrainingReduFeatWOPlayer("1",board,model,howFar), game.AITrainingReduFeatWOPlayer("2",board,model,howFar))
        else:
            try:
                opp = opponentConstr("2",board,opponentStrength)
            except TypeError:
                opp = opponentConstr("2")
            board.setPlayers(game.AITrainingReduFeatWOPlayer("1",board,model,howFar), opp)

        #set start strategy
        board.startGameWithPseudoRandomStartPositions()
        while(board.checkGameStatus() == 0):

            #Boltzman action selection
            board.player1.getDirection()
            Qprobs = game.softmax(board.player1.vals/temperature)
            action_value = np.random.choice(Qprobs[0],p=Qprobs[0])
            action = np.argmax(Qprobs[0] == action_value) - 1

            #Take action, observe new state S'

            oldState = board.to01ReducedFeaturesWithOpponent(board.player1, board.player2, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))
            board.movePlayers(action, board.player2.getDirection())
            newState = board.to01ReducedFeaturesWithOpponent(board.player1, board.player2, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))

            gameStatus = board.checkGameStatus()

            #Observe reward
            reward = rewardFunc(gameStatus)

            if (len(replay) < buffer): #if buffer not filled, add to it
                replay.append((oldState, action, reward, gameStatus, newState))
            else:
                if (h < (buffer-1)):
                    h += 1
                else:
                    h = 0
                replay[h] = (oldState, action, reward, gameStatus, newState)
                    #randomly sample our experience replay memory
                minibatch = random.sample(replay, batchSize)
                X_train = []
                y_train = []

                #fixed target model
                if targetModel is None:
                    targetModel = clone_model(model)
                    targetModel.set_weights(model.get_weights())
                elif updateStep % fixedTargetSteps == 0:
                    targetModel = clone_model(model)
                    targetModel.set_weights(model.get_weights())

                for memory in minibatch:
                    oldState, action, reward, gameStatus, newState = memory
                    oldQvals = model.predict(oldState, batch_size=1)
                    newQvals = targetModel.predict(newState, batch_size=1)
                    maxQval = np.max(newQvals)
                    y = np.zeros((1,3))
                    y[:] = oldQvals[:]
                    if gameStatus == 0 : #non-terminal state
                        update = (reward + (gamma * maxQval))
                    else: #terminal state
                        update = reward
                    y[0][action+1] = update #action + 1 because actions are -1,0,1
                    X_train.append(oldState.reshape(2*(4+(2*howFar+1)*(2*howFar+1)),))
                    y_train.append(y.reshape(3,))

                X_train = np.array(X_train)
                y_train = np.array(y_train)
                print("Game #: %s" % (i,))
                model.fit(X_train, y_train, batch_size=batchSize, epochs=1, verbose=1)
                updateStep += 1
        if i % 10000 == 0:
            model.save(savePath)
        if temperature > 1.0:
            temperature -= (startingTemperature/episodes)
        else:
            temperature = 1.0
    model.save(savePath)

trainModelWithDQL(p_boardSizeX=6,p_boardSizeY=6,p_episodes=50000,savePath='resultsPSR/techn/modelDQL',startingModel=None,opponentConstr=game.AILook1Player,opponentStrength=5)


