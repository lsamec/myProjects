from keras.models import Sequential
from keras.layers.core import Dense, Dropout
from keras.optimizers import RMSprop
from keras.models import load_model, clone_model
import random
import numpy as np
import game

#opponentConstr is None = selfPlay, oppModel fixed
#startingModel is None = from scratch
def trainModelWithDS(p_boardSizeX, p_boardSizeY, p_episodes, savePath, p_temperature=20.0, p_gamma=0.9, howFar=2, rewardFunc=game.rewardFunc2, selfPlayFixOpp=False, startingModel=None, opponentConstr=None, opponentStrength=None):

    model = startingModel

    if startingModel is None:
        model = Sequential() #initially 100 60 30
        model.add(Dense(70, kernel_initializer='lecun_uniform', activation='relu', input_shape=(2*(4+(2*howFar+1)*(2*howFar+1)),)))

        model.add(Dense(35, kernel_initializer='lecun_uniform', activation='relu'))

        model.add(Dense(3, kernel_initializer='lecun_uniform', activation='linear'))
        #linear output so we can have range of real-valued outputs

        model.compile(loss='mean_squared_error', optimizer=RMSprop())

    episodes = p_episodes
    gamma = p_gamma
    startingTemperature = p_temperature
    temperature = p_temperature
    batchSize = 1
    buffer = 1
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

        #Boltzman action selection
        board.player1.getDirection()
        Qprobs = game.softmax(board.player1.vals/temperature)
        action_value = np.random.choice(Qprobs[0],p=Qprobs[0])
        action = np.argmax(Qprobs[0] == action_value) - 1

        while(board.checkGameStatus() == 0):

            #Take action, observe new state S'

            oldState = board.to01ReducedFeaturesWithOpponent(board.player1, board.player2, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))
            board.movePlayers(action, board.player2.getDirection())
            newState = board.to01ReducedFeaturesWithOpponent(board.player1, board.player2, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))

            gameStatus = board.checkGameStatus()

            #Observe reward
            reward = rewardFunc(gameStatus)

            #Boltzman action selection
            board.player1.getDirection()
            Qprobs = game.softmax(board.player1.vals/temperature)
            action_value = np.random.choice(Qprobs[0],p=Qprobs[0])
            action2 = np.argmax(Qprobs[0] == action_value) - 1

            if (len(replay) < buffer): #if buffer not filled, add to it
                replay.append((oldState, action, reward, gameStatus, newState, action2))
            else:
                if (h < (buffer-1)):
                    h += 1
                else:
                    h = 0
                replay[h] = (oldState, action, reward, gameStatus, newState, action2)
                    #randomly sample our experience replay memory
                minibatch = random.sample(replay, batchSize)
                X_train = []
                y_train = []

                for memory in minibatch:
                    oldState, action, reward, gameStatus, newState, action2 = memory
                    oldQvals = model.predict(oldState, batch_size=1)
                    newQvals = model.predict(newState, batch_size=1)
                    takenQval = newQvals[0][action2+1]
                    y = np.zeros((1,3))
                    y[:] = oldQvals[:]
                    if gameStatus == 0 : #non-terminal state
                        update = (reward + (gamma * takenQval))
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

            action = action2

        if i % 10000 == 0:
            model.save(savePath)
        if temperature > 1.0:
            temperature -= (startingTemperature/episodes)
        else:
            temperature = 1.0
    model.save(savePath)

trainModelWithDS(p_boardSizeX=6,p_boardSizeY=6,p_episodes=50000,p_temperature=20.0,savePath='resultsPSR/techn/modelDS',startingModel=None,opponentConstr=game.AILook1Player,opponentStrength=5)


