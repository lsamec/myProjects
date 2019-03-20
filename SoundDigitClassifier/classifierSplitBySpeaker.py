from __future__ import print_function

import tensorflow as tf
from tensorflow.contrib import rnn
from os import listdir
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix
import librosa
from sklearn import preprocessing
import matplotlib.pyplot as plt

def numToOneHot(num):
    oneHotV = [0]*10
    oneHotV[num] = 1
    return oneHotV

def split(a, n):
    k, m = divmod(len(a), n)
    return (a[i * k + min(i, m):(i + 1) * k + min(i + 1, m)] for i in range(n))
ymax = 0
wavs =[]
for highfolder in ["C:/Users/leonx64/Desktop/comp_intel_project/sepBySpeaker/fsddTrain"]:
    for fol, ind in zip(listdir(highfolder),range(10)):
        wavs.append([])
        for file in listdir(highfolder+"/"+fol):
            y, sr = librosa.load(highfolder+"/"+fol+"/"+file)
            #ymax = max(ymax,len(y))
            if len(y) > 70000:
                continue

            noOfAppendZeros = 70000 - len(y)
            y = np.append(y,[0.0]*noOfAppendZeros)

            """
            rmse = librosa.feature.rmse(y=y).tolist()[0]
            delta = librosa.feature.delta(y)

            delta_chunks = list(split(delta, 50))
            delta_mean = [(sum(abs(chunk))*1.0)/len(chunk) for chunk in delta_chunks]
            """
            mfccs = librosa.feature.mfcc(y,n_mfcc=12).tolist()
            mfccs_flat = [item for sublist in mfccs for item in sublist]
            features = mfccs_flat

            wavs[ind].append([features,numToOneHot(ind)])
print(ymax)
ymax = 0
X_trainAll = []
y_trainAll = []
for allInOneClass in wavs:
    features = []
    labels = []
    for pair in allInOneClass:
        features.append(pair[0])
        labels.append(pair[1])

    X_trainAll = X_trainAll + features
    y_trainAll = y_trainAll + labels


X_trainAll = np.array(X_trainAll)
y_trainAll = np.array(y_trainAll)

wavs =[]
for highfolder in ["C:/Users/leonx64/Desktop/comp_intel_project/sepBySpeaker/fsddTest"]:
    for fol, ind in zip(listdir(highfolder),range(10)):
        wavs.append([])
        for file in listdir(highfolder+"/"+fol):
            y, sr = librosa.load(highfolder+"/"+fol+"/"+file)
            #ymax = max(ymax,len(y))
            if len(y) > 70000:
                continue

            noOfAppendZeros = 70000 - len(y)
            y = np.append(y,[0.0]*noOfAppendZeros)
            """
            rmse = librosa.feature.rmse(y=y).tolist()[0]
            delta = librosa.feature.delta(y)

            delta_chunks = list(split(delta, 50))
            delta_mean = [(sum(abs(chunk))*1.0)/len(chunk) for chunk in delta_chunks]
            """
            mfccs = librosa.feature.mfcc(y,n_mfcc=12).T.tolist()
            mfccs_flat = [item for sublist in mfccs for item in sublist]

            features = mfccs_flat

            wavs[ind].append([features,numToOneHot(ind)])

X_testAll = []
y_testAll = []
for allInOneClass in wavs:
    features = []
    labels = []
    for pair in allInOneClass:
        features.append(pair[0])
        labels.append(pair[1])

    X_testAll = X_testAll + features
    y_testAll = y_testAll + labels

X_testAll = np.array(X_testAll)
y_testAll = np.array(y_testAll)



print("Separating data over...")


# Training Parameters
learning_rate = 0.001
training_steps = 50000
batch_size = 120
display_step = 50

# Network Parameters
num_input = 12 # data input
timesteps = 137 # timesteps
num_hidden = 100 # hidden layer num of features
num_classes = 10 # total classes (0-9 digits)
num_layers = 3

# tf Graph input
X = tf.placeholder("float", [None, timesteps, num_input])
Y = tf.placeholder("float", [None, num_classes])
dropout = tf.placeholder(tf.float32)

# Define weights
weights = {
    'out': tf.Variable(tf.random_normal([num_hidden, num_classes]))
}
biases = {
    'out': tf.Variable(tf.random_normal([num_classes]))
}
print("Setting hyperparameters over...")

def RNN(x, weights, biases,d_o):

    # Prepare data shape to match `rnn` function requirements
    # Current data input shape: (batch_size, timesteps, n_input)
    # Required shape: 'timesteps' tensors list of shape (batch_size, n_input)

    # Unstack to get a list of 'timesteps' tensors of shape (batch_size, n_input)
    x = tf.unstack(x, timesteps, 1)
    lstm_cells = []
    for _ in range(num_layers):
        # Define a lstm cell with tensorflow
        lstm_cell = rnn.LSTMCell(num_hidden)
        lstm_cell = rnn.DropoutWrapper(lstm_cell, d_o)
        lstm_cells.append(lstm_cell)
    lstm_cells = rnn.MultiRNNCell(lstm_cells)
    # Get lstm cell output
    outputs, states = rnn.static_rnn(lstm_cells, x, dtype=tf.float32)

    # Linear activation, using rnn inner loop last output
    return tf.matmul(outputs[-1], weights['out']) + biases['out']



logits = RNN(X, weights, biases, dropout)

print("Init RNN over...")
prediction = tf.nn.softmax(logits)

predScalCls = tf.argmax(prediction, 1)
yScalCls = tf.argmax(Y, 1)

# Define loss and optimizer
loss_op = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(
    logits=logits, labels=Y))
optimizer = tf.train.AdamOptimizer(learning_rate=learning_rate)
train_op = optimizer.minimize(loss_op)

# Evaluate model (with test logits, for dropout to be disabled)
correct_pred = tf.equal(tf.argmax(prediction, 1), tf.argmax(Y, 1))
accuracy = tf.reduce_mean(tf.cast(correct_pred, tf.float32))

print("Setting tensorflow functions over...")

# Initialize the variables (i.e. assign their default value)
init = tf.global_variables_initializer()

# Start training
with tf.Session() as sess:

    # Run the initializer
    sess.run(init)
    highestTestAcc = 0.0
    train_acc_list = []
    train_err_list = []
    test_acc_list = []
    test_err_list = []
    confMatrixList = []
    outStep = 0
    for step in range(1, training_steps+1):

        idx = np.random.choice(np.arange(len(X_trainAll)), batch_size, replace=False)
        X_trainAllChunk = X_trainAll[idx]
        y_trainAllChunk = y_trainAll[idx]

        X_trainAllChunk = X_trainAllChunk.reshape((batch_size, timesteps, num_input))
        # Run optimization op (backprop)
        sess.run(train_op, feed_dict={X: X_trainAllChunk, Y: y_trainAllChunk, dropout: 0.5})
        if step % display_step == 0 or step == 1:
            # Calculate batch loss and accuracy

            loss, acc = sess.run([loss_op, accuracy], feed_dict={X: X_trainAll.reshape((-1,timesteps, num_input)),
                                                                 Y: y_trainAll, dropout: 1.0})
            print("Step " + str(step) + ", Minibatch Loss= " + \
                  "{:.4f}".format(loss) + ", Training Accuracy= " + \
                  "{:.3f}".format(acc))
            # Calculate accuracy
            test_data = X_testAll.reshape((-1, timesteps, num_input))
            test_label = y_testAll
            test_loss, test_acc = sess.run([loss_op, accuracy], feed_dict={X: test_data, Y: test_label, dropout: 1.0})
            print("Testing Accuracy:", test_acc)

            predSC, ySC = sess.run([predScalCls,yScalCls], feed_dict={X: test_data, Y: test_label, dropout: 1.0})
            confMatrixList.append(confusion_matrix(ySC, predSC))

            train_acc_list.append(acc)
            train_err_list.append(loss)
            test_acc_list.append(test_acc)
            test_err_list.append(test_loss)

            if (test_acc > highestTestAcc):
                highestTestAcc = test_acc
                outStep = 0
            else:
                outStep += 1
            if outStep >= 40:
                break


    print("Best test acc:", test_acc_list[-1-outStep])
    print("Corresponding train acc:", train_acc_list[-1-outStep])
    print("Confusion Matrix:")
    print(confMatrixList[-1-outStep])

    fig,ax = plt.subplots(1)
    ax.plot(test_err_list, color='green', label='test', lw=2)
    ax.plot(train_err_list, color='blue', label='training', lw=2)
    ax.set_xlabel('Number of update (x50)')
    ax.set_ylabel('Cross-entropy')
    plt.legend()
    plt.show()

    fig,ax = plt.subplots(1)
    ax.plot(test_acc_list, color='green', label='test', lw=2)
    ax.plot(train_acc_list, color='blue', label='training', lw=2)
    ax.set_xlabel('Number of update (x50)')
    ax.set_ylabel('Accuracy')
    plt.legend()
    plt.show()


