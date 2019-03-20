
from __future__ import print_function
import os
import neat
import training.NEATRF3PSR as tr
import pickle


def run(config_file):

    p = neat.Checkpointer.restore_checkpoint('RF3-PSR-628')

    p.add_reporter(neat.StdOutReporter(True))
    stats = neat.StatisticsReporter()
    p.add_reporter(stats)

    winner = p.run(tr.eval_against_all,1)

    #save winner
    with open('RF3-PSR', 'wb') as f:
        pickle.dump(winner, f)




if __name__ == '__main__':
    # Determine path to configuration file. This path manipulation is
    # here so that the script will run successfully regardless of the
    # current working directory.
    local_dir = os.path.dirname(__file__)
    config_path = os.path.join(local_dir, 'config-neat-RF3.txt')
    run(config_path)