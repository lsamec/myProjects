from keras.models import Sequential
from keras.layers.core import Dense, Dropout
from keras.optimizers import RMSprop
from keras.models import load_model, clone_model
import random
import numpy as np
import game

def discountedAccRewards(rewardList,gamma):
    discounted_r = np.zeros_like(rewardList)
    running_add = 0
    for t in range(len(rewardList))[::-1]:
        running_add = running_add * gamma + rewardList[t]
        discounted_r[t] = running_add
    return discounted_r

#opponentConstr is None = selfPlay, oppModel fixed
#startingModel is None = from scratch
def trainModelWithDMC(p_boardSizeX, p_boardSizeY, p_episodes, savePath, p_temperature=15.0, p_gamma=0.9, howFar=2, rewardFunc=game.rewardFunc2, selfPlayFixOpp=False, startingModel=None, opponentConstr=None, opponentStrength=None):

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
    batchSize = 1
    buffer = 1
    replay = []
    h = 0
    updateStep = 0
    for i in range(episodes):

        rewardList1 = []
        stateList1 = []
        actionList1 = []

        rewardList2 = []
        stateList2 = []
        actionList2 = []

        #init board
        board = game.Board(p_boardSizeX,p_boardSizeY)

        #set opponent
        if(opponentConstr is None):
            if(selfPlayFixOpp == True):
                oppModel = clone_model(model)
                oppModel.set_weights(model.get_weights())
                board.setPlayers(game.AITrainingReduFeatWOPlayer("1",board,model,howFar), game.AITrainingReduFeatWOPlayer("2",board,oppModel,howFar))
            else:
                #default
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
            action1 = np.argmax(Qprobs[0] == action_value) - 1
            actionList1.append(action1)

            board.player2.getDirection()
            Qprobs = game.softmax(board.player2.vals/temperature)
            action_value = np.random.choice(Qprobs[0],p=Qprobs[0])
            action2 = np.argmax(Qprobs[0] == action_value) - 1
            actionList2.append(action2)

            #Take action, observe new state S'

            state1 = board.to01ReducedFeaturesWithOpponent(board.player1, board.player2, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))
            stateList1.append(state1)
            state2 = board.to01ReducedFeaturesWithOpponent(board.player2, board.player1, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))
            stateList2.append(state2)

            board.movePlayers(action1, action2)

            gameStatus = board.checkGameStatus()

            #only with rewardFunc1
            reward = game.rewardFunc2(gameStatus)

            reward2 = 1.0*reward

            if(gameStatus == 1) or (gameStatus == 2):
                reward2 = -1.0*reward2

            rewardList1.append(reward)
            rewardList2.append(reward2)

        dAC1 = discountedAccRewards(rewardList1,gamma)
        dAC2 = discountedAccRewards(rewardList2,gamma)

        stateList1 = stateList1 + stateList2
        actionList1 = actionList1 + actionList2
        dAC1 = np.append(dAC1,dAC2)
        for stepNum in range(len(stateList1)):

            if (len(replay) < buffer):
                replay.append((stateList1[stepNum], actionList1[stepNum], dAC1[stepNum]))
            else:
                if (h < (buffer-1)):
                    h += 1
                else:
                    h = 0
                replay[h] = (stateList1[stepNum], actionList1[stepNum], dAC1[stepNum])

                #randomly sample our experience replay memory
                minibatch = random.sample(replay, batchSize)
                X_train = []
                y_train = []

                for memory in minibatch:
                    state, action, accReward = memory
                    Qvals = model.predict(state, batch_size=1)
                    y = np.zeros((1,3))
                    y[:] = Qvals[:]
                    y[0][action+1] = accReward #action + 1 because actions are -1,0,1
                    X_train.append(state.reshape(2*(4+(2*howFar+1)*(2*howFar+1)),))
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

trainModelWithDMC(p_boardSizeX=6,p_boardSizeY=6,p_episodes=1000000,savePath='resultsPSR/opp/modelSelfPlayBig2',startingModel=None)


