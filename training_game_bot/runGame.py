import game
from keras.models import load_model

board = game.Board(5, 5)
board.setPlayers(game.AITrainingPlayer("1",board,load_model('models/temp/modelJustFinishedMC')),game.HumanPlayer("2"))
board.startGameOnDifferentEnds()
print("Game ends with:", board.play(True))
