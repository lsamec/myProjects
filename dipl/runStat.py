from keras.models import load_model

import game
from util import loadNEATmodel

gameStats = {}
gameStats[1] = 0
gameStats[2] = 0
gameStats[3] = 0

for episode in range(0,60):
    board = game.Board(6, 6)
    #board.setPlayers(game.AITrainingReduFeatPlayer("1",board,loadNEATmodel('training/resultsPSR/NEAT/RF3-PSR','training/config-neat-RF3.txt'),3),game.AILook1Player("2",board,5))
    board.setPlayers(game.AITrainingReduFeatWOPlayer("1",board,load_model('training/resultsPSR/opp/modelSelfPlayBig2'),2),game.AILook1Player("2",board,5))# game.RandomPlayer("2")
    board.startGameWithPseudoRandomStartPositions()
    result = board.play(False)
    print("Ends with: ", result)
    gameStats[result] += 1
    print("Game: ",episode)
print(gameStats)
print('5')
