import random
import copy
import numpy as np

def softmax(x):
    """Compute softmax values for each sets of scores in x."""
    e_x = np.exp(x)
    return e_x / e_x.sum()

def discountedAccRewards(rewardList,gamma):
    discounted_r = np.zeros_like(rewardList)
    running_add = 0
    for t in range(len(rewardList))[::-1]:
        running_add = running_add * gamma + rewardList[t]
        discounted_r[t] = running_add
    return discounted_r

def rewardFunc1(gameStatus):
    if gameStatus == 0 or gameStatus == 3:
        return 0.0
    elif gameStatus == 1:
        return 10.0
    elif gameStatus == 2:
        return -10.0

def rewardFunc2(gameStatus):
    if gameStatus == 0 or gameStatus == 3:
        return 2.0
    elif gameStatus == 1:
        return 6.0
    elif gameStatus == 2:
        return -6.0

def rewardFunc3(gameStatus):
    if gameStatus == 0 or gameStatus == 1:
        return 2.0
    elif gameStatus == 2 or gameStatus == 3:
        return 0.0

def rewardFunc4(gameStatus):
    if gameStatus == 0:
        return 0.0
    elif gameStatus == 3:
        return 50.0
    elif gameStatus == 1:
        return 75.0
    elif gameStatus == 2:
        return 25.0


class Board:
    def __init__(self, sizeX, sizeY):
        self.sizeX = sizeX
        self.sizeY = sizeY

    def newDeepCopy(self):

        newBoard = Board(self.sizeX,self.sizeY)
        player1 = Player(self.player1.name)
        player1.passedTiles = copy.deepcopy(self.player1.passedTiles)
        player1.currentPosition = copy.deepcopy(self.player1.currentPosition)
        player1.currentOrientation = copy.deepcopy(self.player1.currentOrientation)
        player1.status = copy.deepcopy(self.player1.status)

        player2 = Player(self.player2.name)
        player2.passedTiles = copy.deepcopy(self.player2.passedTiles)
        player2.currentPosition = copy.deepcopy(self.player2.currentPosition)
        player2.currentOrientation = copy.deepcopy(self.player2.currentOrientation)
        player2.status = copy.deepcopy(self.player2.status)

        newBoard.setPlayers(player1,player2)
        return newBoard

    def setPlayers(self, player1, player2):
        self.player1 = player1
        self.player2 = player2
        self.playerByName = {}
        self.playerByName[player1.name] = player1
        self.playerByName[player2.name] = player2

    def startGameOnDifferentEnds(self):
        self.player1.currentOrientation = 0
        self.player1.currentPosition = (0,0)
        self.player1.passedTiles.append(self.player1.currentPosition)

        self.player2.currentOrientation = 0
        self.player2.currentPosition = (self.sizeX-1, 0)
        self.player2.passedTiles.append(self.player2.currentPosition)

    def startGameWithPseudoRandomStartPositions(self):
        if self.sizeX % 2 != 0 or self.sizeY % 2 != 0:
            raise ValueError('SizeX and sizeY must be even numbers')

        self.player1.currentOrientation = random.randint(0,3)
        self.player1.currentPosition = (random.randint(1,(self.sizeX-2)/2), random.randint(1,self.sizeX-2))
        self.player1.passedTiles.append(self.player1.currentPosition)

        if self.player1.currentOrientation == 1:
            self.player2.currentOrientation = 3
        if self.player1.currentOrientation == 3:
            self.player2.currentOrientation = 1
        if self.player1.currentOrientation == 0 or self.player1.currentOrientation == 2:
            self.player2.currentOrientation = self.player1.currentOrientation
        self.player2.currentPosition = (self.sizeX-1-self.player1.currentPosition[0], self.player1.currentPosition[1])
        self.player2.passedTiles.append(self.player2.currentPosition)

    def startGameWithRandomStartPositions(self):
        self.player1.currentOrientation = random.randint(0,3)
        self.player1.currentPosition = (random.randint(0,self.sizeX - 1) , random.randint(0,self.sizeY - 1))
        self.player1.passedTiles.append(self.player1.currentPosition)

        self.player2.currentOrientation = random.randint(0,3)
        self.player2.currentPosition = (random.randint(0,self.sizeX - 1) , random.randint(0,self.sizeY - 1))
        while(self.player1.currentPosition == self.player2.currentPosition):
            self.player2.currentPosition = (random.randint(0,self.sizeX - 1) , random.randint(0,self.sizeY - 1))
        self.player2.passedTiles.append(self.player2.currentPosition)


    def updatePlayerStatus(self, player):
        player.status = 0
        if player.currentPosition[0] < 0 or player.currentPosition[0] >= self.sizeX or player.currentPosition[1] < 0 or player.currentPosition[1] >= self.sizeY:
            player.status = -1

        for passedTile in self.player1.passedTiles:
            if player.currentPosition == passedTile:
                player.status = -1

        for passedTile in self.player2.passedTiles:
            if player.currentPosition == passedTile:
                player.status = -1

    def updatePlayersStatuses(self):

        self.updatePlayerStatus(self.player1)
        self.updatePlayerStatus(self.player2)
        if self.player1.currentPosition == self.player2.currentPosition:
            self.player1.status = -1
            self.player2.status = -1

    def movePlayers(self,direction1,direction2):
        self.player1.move(direction1)
        self.player2.move(direction2)
        self.updatePlayersStatuses()
        self.player1.passedTiles.append(self.player1.currentPosition)
        self.player2.passedTiles.append(self.player2.currentPosition)

    #for AI
    def moveOnePlayer(self,player,direction):
        player.move(direction)
        self.updatePlayerStatus(player)
        player.passedTiles.append(player.currentPosition)

    # 0 - still playing; 1 - player1 wins; 2 - player2 wins; 3 - draw
    def checkGameStatus(self):
        if self.player1.status == -1 and self.player2.status == -1:
            return 3
        if self.player1.status == 0 and self.player2.status == -1:
            return 1
        if self.player1.status == -1 and self.player2.status == 0:
            return 2
        return 0


    def drawByOrientation(self,orientation):
        if orientation == 0:
            print("^",end='')
        if orientation == 1:
            print(">",end='')
        if orientation == 2:
            print("v",end='')
        if orientation == 3:
            print("<",end='')

    def drawBoard(self):
        for y in range(0,self.sizeY)[::-1]:
            for x in range(0,self.sizeX):
                print(" ",end='')
                if (x,y) in self.player1.passedTiles and (x,y) != self.player1.currentPosition:
                    print('#',end='')
                    continue
                if (x,y) in self.player2.passedTiles and (x,y) != self.player2.currentPosition:
                    print('*',end='')
                    continue
                if (x,y) == self.player1.currentPosition:
                    self.drawByOrientation(self.player1.currentOrientation)
                    continue
                if (x,y) == self.player2.currentPosition:
                    self.drawByOrientation(self.player2.currentOrientation)
                    continue
                print("-",end='')
            print()
        print()

    def play(self, drawBoard):
        if(drawBoard):
            print(self.player1.name +" position: ",self.player1.currentPosition)
            print(self.player2.name +" position: ",self.player2.currentPosition)
            self.drawBoard()
        while(True):
            self.movePlayers(self.player1.getDirection(),self.player2.getDirection())
            if(drawBoard):
                self.drawBoard()
            gameStatus = self.checkGameStatus()
            if gameStatus in [1,2,3]:
                return gameStatus

    def to01ReducedFeatures(self, player, howFar):
        features = []
        playerXpos = player.currentPosition[0]
        playerYpos = player.currentPosition[1]
        for x in range(playerXpos-howFar,playerXpos+howFar+1):
            for y in range(playerYpos-howFar,playerYpos+howFar+1):
                if x < 0 or x >= self.sizeX or y < 0 or y >= self.sizeY or (x,y) in self.player1.passedTiles or (x,y) in self.player2.passedTiles:
                    features.append(1)
                else:
                    features.append(0)

        for o in [0,1,2,3]:
            if o == player.currentOrientation:
                features.append(1)
            else:
                features.append(0)
        return np.array(features)

    def to01ReducedFeaturesWithOpponent(self, player, opp, howFar):
        features = []
        playerXpos = player.currentPosition[0]
        playerYpos = player.currentPosition[1]
        for x in range(playerXpos-howFar,playerXpos+howFar+1):
            for y in range(playerYpos-howFar,playerYpos+howFar+1):
                if x < 0 or x >= self.sizeX or y < 0 or y >= self.sizeY or (x,y) in self.player1.passedTiles or (x,y) in self.player2.passedTiles:
                    features.append(1)
                else:
                    features.append(0)

        for o in [0,1,2,3]:
            if o == player.currentOrientation:
                features.append(1)
            else:
                features.append(0)

        for x in range(playerXpos-howFar,playerXpos+howFar+1):
            for y in range(playerYpos-howFar,playerYpos+howFar+1):
                if (x,y) == opp.currentPosition:
                    features.append(1)
                else:
                    features.append(0)

        for o in [0,1,2,3]:
            if o == opp.currentOrientation:
                features.append(1)
            else:
                features.append(0)

        return np.array(features)

    #me,opp - my player, opponent player
    def to01Features(self,me,opp):
        features = []
        for x in range(0, self.sizeX):
            for y in range(0, self.sizeY):
                if (x,y) in me.passedTiles:
                    features.append(1)
                else:
                    features.append(0)

        for x in range(-1, self.sizeX+1):
            for y in range(-1, self.sizeY+1):
                if (x,y) == me.currentPosition:
                    features.append(1)
                else:
                    features.append(0)

        for o in [0,1,2,3]:
            if o == me.currentOrientation:
                features.append(1)
            else:
                features.append(0)

        for x in range(0, self.sizeX):
            for y in range(0, self.sizeY):
                if (x,y) in opp.passedTiles:
                    features.append(1)
                else:
                    features.append(0)

        for x in range(-1, self.sizeX+1):
            for y in range(-1, self.sizeY+1):
                if (x,y) == opp.currentPosition:
                    features.append(1)
                else:
                    features.append(0)

        for o in [0,1,2,3]:
            if o == opp.currentOrientation:
                features.append(1)
            else:
                features.append(0)

        return np.array(features)

# orientations: 0 - ^; 1 - >; 2 - v; 3 - <;
# directions: 0 - straight; -1 - left; 1 - right
# globalDirection: 0 - up; 1 - right; 2 - down; 3 - left
# status: 0 - still playing; -1 - out of play
class Player:
    def __init__(self, name):
        self.passedTiles = []
        self.currentPosition = None
        self.currentOrientation = None
        self.status = 0
        self.name = name

    #dont use
    def getGlobalDirection(self,orientation,direction):
        rawGlobalDirection = orientation + direction
        if rawGlobalDirection == -1: return 3
        if rawGlobalDirection == 4: return 0
        return rawGlobalDirection

    def move(self, direction):
        globalDirection = self.getGlobalDirection(self.currentOrientation, direction)
        newPosition = None
        if(globalDirection == 0): newPosition = (self.currentPosition[0], self.currentPosition[1]+1)
        if(globalDirection == 1): newPosition = (self.currentPosition[0]+1, self.currentPosition[1])
        if(globalDirection == 2): newPosition = (self.currentPosition[0], self.currentPosition[1]-1)
        if(globalDirection == 3): newPosition = (self.currentPosition[0]-1, self.currentPosition[1])
        self.currentPosition = newPosition
        self.currentOrientation = globalDirection

    def getDirection(self):
        None

class RandomPlayer(Player):
    def __init__(self, name):
        Player.__init__(self,name)

    def getDirection(self):
        return random.randint(0,2)-1

class AITrainingPlayer(Player):
    def __init__(self, name, board, model):
        Player.__init__(self,name)
        self.board = board
        self.model = model
        self.vals = None

    def getDirection(self):
        playerNames = list(self.board.playerByName.keys())
        if playerNames[0] == self.name:
            myName = playerNames[0]
            oppName = playerNames[1]
        else:
            myName = playerNames[1]
            oppName = playerNames[0]
        features = self.board.to01Features(self.board.playerByName[myName], self.board.playerByName[oppName]).reshape(1,8+self.board.sizeX*self.board.sizeY*2+(self.board.sizeX+2)*(self.board.sizeY+2)*2)
        try:
            #Qvals
            vals = self.model.predict(features, batch_size=1)
        except AttributeError:
            #NEAT output
            vals = self.model.activate(features[0])
        self.vals = vals
        return np.argmax(vals) - 1

#reducirana informacija bez protivnika
class AITrainingReduFeatPlayer(Player):
    def __init__(self, name, board, model, howFar):
        Player.__init__(self,name)
        self.board = board
        self.model = model
        self.vals = None
        self.howFar = howFar

    def getDirection(self):
        features = self.board.to01ReducedFeatures(self,self.howFar).reshape(1,4+(2*self.howFar+1)*(2*self.howFar+1))
        try:
            #Qvals
            vals = self.model.predict(features, batch_size=1)
        except AttributeError:
            #NEAT output
            vals = self.model.activate(features[0])
        #print(vals)
        self.vals = vals
        return np.argmax(vals) - 1

#reducirana informacija s protivnikom
class AITrainingReduFeatWOPlayer(Player):
    def __init__(self, name, board, model, howFar):
        Player.__init__(self,name)
        self.board = board
        self.model = model
        self.vals = None
        self.howFar = howFar

    def getDirection(self):
        playerNames = list(self.board.playerByName.keys())
        if playerNames[0] == self.name:
            myName = playerNames[0]
            oppName = playerNames[1]
        else:
            myName = playerNames[1]
            oppName = playerNames[0]
        features = self.board.to01ReducedFeaturesWithOpponent(self.board.playerByName[myName], self.board.playerByName[oppName], self.howFar).reshape(1,2*(4+(2*self.howFar+1)*(2*self.howFar+1)))
        try:
            #Qvals
            vals = self.model.predict(features, batch_size=1)
        except AttributeError:
            #NEAT output
            vals = self.model.activate(features[0])
        #print(vals)
        self.vals = vals
        return np.argmax(vals) - 1

#probabilistički igrač, potpuna informacija
class AITrainingSoftmaxProbPlayer(AITrainingPlayer):
    def __init__(self, name, board, model):
        AITrainingPlayer.__init__(self, name, board, model)
    def getDirection(self):
        AITrainingPlayer.getDirection(self)
        probs = softmax(self.vals)
        action_value = np.random.choice(probs[0],p=probs[0])
        return np.argmax(probs[0] == action_value) - 1

#probabilistički igrač, reducirana informacija, način s protivnikom
class AITrainingSoftmaxProbReduFeatWOPlayer(AITrainingReduFeatPlayer):
    def __init__(self, name, board, model,howFar):
        AITrainingReduFeatWOPlayer.__init__(self, name, board, model,howFar)
    def getDirection(self):
        AITrainingReduFeatWOPlayer.getDirection(self)
        probs = softmax(self.vals)
        action_value = np.random.choice(probs[0],p=probs[0])
        return np.argmax(probs[0] == action_value) - 1

#finds move which enables n further moves but just looking at current board
class AILook1Player(Player):
    def __init__(self, name, board, maxLook):
        Player.__init__(self,name)
        self.board = board
        self.maxLook = maxLook

    def getDirection(self):
        boardStack = []
        finalDirectionTuple = None
        possibleDirections = [-1,0,1]
        random.shuffle(possibleDirections)
        for direction in possibleDirections:
            boardCopy = self.board.newDeepCopy()
            myself = boardCopy.playerByName[self.name]
            boardCopy.moveOnePlayer(myself,direction)
            if myself.status == 0:
                boardStack.append((boardCopy,direction,1))
                finalDirectionTuple = (direction,1)
        while(len(boardStack) != 0):
            currentBoardTuple = boardStack.pop()
            if currentBoardTuple[2] >= self.maxLook:
                finalDirectionTuple = (currentBoardTuple[1],currentBoardTuple[2])
                break
            currentBoard = currentBoardTuple[0]
            random.shuffle(possibleDirections)
            for direction in possibleDirections:
                boardCopy = currentBoard.newDeepCopy()
                myself = boardCopy.playerByName[self.name]
                boardCopy.moveOnePlayer(myself,direction)
                if myself.status == 0:
                    boardStack.append((boardCopy,currentBoardTuple[1],currentBoardTuple[2]+1))
                    if finalDirectionTuple[1] < currentBoardTuple[2]+1:
                        finalDirectionTuple = (currentBoardTuple[1],currentBoardTuple[2]+1)
        if finalDirectionTuple is None: return 0
        #print(finalDirectionTuple)
        return finalDirectionTuple[0]

# w - straight, a - left, d - right
class HumanPlayer(Player):
    def getDirection(self):
        while(True):
            rawDirection = input(self.name + " plays: ")
            if rawDirection == 'w': return 0
            if rawDirection == 'a': return -1
            if rawDirection == 'd': return 1
            print("Incorrect input "+self.name)
