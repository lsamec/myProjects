from keras.models import Sequential
from keras.layers.core import Dense, Dropout
from keras.optimizers import RMSprop
from keras.models import load_model, clone_model
from keras.initializers import Constant
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
def trainModelWithDMC(p_boardSizeX, p_boardSizeY, p_episodes, savePath, p_temperature=20.0, p_gamma=0.9,howFar=2,rewardFunc=game.rewardFunc2, startingModel=None, player1Constr=None ,player1Strength=None, player2Constr=None ,player2Strength=None):

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

        rewardList = []
        stateList = []
        actionList = []

        #init board
        board = game.Board(p_boardSizeX,p_boardSizeY)

        board.setPlayers(player1Constr("1",board,player1Strength), player2Constr("2",board,player2Strength))

        #set start procedure
        board.startGameWithPseudoRandomStartPositions()
        while(board.checkGameStatus() == 0):

            action = board.player1.getDirection()
            state =  board.to01ReducedFeaturesWithOpponent(board.player1, board.player2, howFar).reshape(1,2*(4+(2*howFar+1)*(2*howFar+1)))

            actionList.append(action)
            stateList.append(state)

            board.movePlayers(action, board.player2.getDirection())

            gameStatus = board.checkGameStatus()

            reward = rewardFunc(gameStatus)

            rewardList.append(reward)

        dAC = discountedAccRewards(rewardList,gamma)
        for stepNum in range(len(rewardList)):

            if (len(replay) < buffer):
                replay.append((stateList[stepNum], actionList[stepNum], dAC[stepNum]))
            else:
                if (h < (buffer-1)):
                    h += 1
                else:
                    h = 0
                replay[h] = (stateList[stepNum], actionList[stepNum], dAC[stepNum])

                minibatch = random.sample(replay, batchSize)
                X_train = []
                y_train = []

                for memory in minibatch:
                    state, action, accReward = memory
                    Qvals = model.predict(state, batch_size=1)
                    y = np.zeros((1,3))
                    y[:] = Qvals[:]
                    y[0][action+1] = accReward
                    X_train.append(state.reshape(2*(4+(2*howFar+1)*(2*howFar+1))))
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

trainModelWithDMC(p_boardSizeX=6,p_boardSizeY=6,p_episodes=50000,savePath='resultsPSR/opp/modelWatch',startingModel=None, player1Constr=game.AILook1Player ,player1Strength=5, player2Constr=game.AILook1Player ,player2Strength=5)


