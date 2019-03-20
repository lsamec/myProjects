import pickle
import neat
import os

def loadNEATmodel(modelPath,configPath):
    with open(modelPath, 'rb') as f:
        c = pickle.load(f)

    # Load the config file, which is assumed to live in
    # the same directory as this script.
    local_dir = os.path.dirname(__file__)
    config_path = os.path.join(local_dir, configPath)
    config = neat.Config(neat.DefaultGenome, neat.DefaultReproduction,
                     neat.DefaultSpeciesSet, neat.DefaultStagnation,
                     config_path)

    return neat.nn.FeedForwardNetwork.create(c, config)