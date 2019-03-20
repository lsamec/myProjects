import game
from util import loadNEATmodel
from keras.models import load_model


#upotrijebiti odgovarajućeg AITrainingPlayer-a za svoj model

board = game.Board(6, 6)
#board.setPlayers(game.AITrainingPlayer("1",board,loadNEATmodel('training/resultsPSR/NEAT/RF3-PSR','training/config-neat-RF3.txt')),game.AILook1Player("2",board,1))
board.setPlayers(game.AITrainingReduFeatWOPlayer("1",board,load_model('training/resultsPSR/techn/modelDQLF'),2),game.HumanPlayer("2"))
#board.setPlayers(game.HumanPlayer("1"),game.HumanPlayer("2"))
board.startGameWithPseudoRandomStartPositions()
print("Game ends with:", board.play(True))
