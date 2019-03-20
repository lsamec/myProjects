from __future__ import print_function

import os
import pickle

import neat

import game

def eval_against_all(genomes, config):
    for genome_id, genome in genomes:
        model = neat.nn.FeedForwardNetwork.create(genome, config)
        genome.fitness = 0.0
        sizeX = 6
        sizeY = 6
        for opp in [(game.RandomPlayer,0),(game.AILook1Player,1),(game.AILook1Player,5)]:
            for episode in range(0,50):
                board = game.Board(sizeX, sizeY)
                if opp[0].__name__ == "RandomPlayer":
                    board.setPlayers(game.AITrainingReduFeatPlayer("1",board,model,2), opp[0]("2"))
                else:
                    board.setPlayers(game.AITrainingReduFeatPlayer("1",board,model,2), opp[0]("2",board,opp[1]))
                board.startGameWithPseudoRandomStartPositions()
                result = board.play(False)
                if result == 1:
                    genome.fitness += 1.0
                elif result == 2:
                    genome.fitness -= 1.0

def run(config_file):
    # Load configuration.
    config = neat.Config(neat.DefaultGenome, neat.DefaultReproduction,
                         neat.DefaultSpeciesSet, neat.DefaultStagnation,
                         config_file)

    # Create the population, which is the top-level object for a NEAT run.
    p = neat.Population(config)

    # Add a stdout reporter to show progress in the terminal.
    p.add_reporter(neat.StdOutReporter(True))
    stats = neat.StatisticsReporter()
    p.add_reporter(stats)
    p.add_reporter(neat.Checkpointer(generation_interval=None, time_interval_seconds=3600, filename_prefix='RF2-PSR-'))

    p.run(eval_against_all)


if __name__ == '__main__':
    # Determine path to configuration file. This path manipulation is
    # here so that the script will run successfully regardless of the
    # current working directory.
    local_dir = os.path.dirname(__file__)
    config_path = os.path.join(local_dir, 'config-neat-RF2.txt')
    run(config_path)