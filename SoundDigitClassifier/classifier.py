from __future__ import print_function

from scipy import signal
import tensorflow as tf
from tensorflow.contrib import rnn
from os import listdir
import numpy as np
from sklearn.model_selection import train_test_split
import librosa
from sklearn import preprocessing

def numToOneHot(num):
    oneHotV = [0]*10
    oneHotV[num] = 1
    return oneHotV

def split(a, n):
    k, m = divmod(len(a), n)
    return (a[i * k + min(i, m):(i + 1) * k + min(i + 1, m)] for i in range(n))

wavs =[]
hold = []
for highfolder in ["C:/Users/leonx64/Desktop/comp_intel_project/fsdd","C:/Users/leonx64/Desktop/comp_intel_project/eSpeak","C:/Users/leonx64/Desktop/comp_intel_project/joined_distorted"]:
    for fol, ind in zip(listdir(highfolder),range(10)):
        wavs.append([])
        for file in listdir(highfolder+"/"+fol):

            y, sr = librosa.load(highfolder+"/"+fol+"/"+file)
            #print(sr)

            if len(y) > 15000:
                continue

            """
            noSamples = 10000
            if len(y_norm) > noSamples:
                y_norm = y_norm[0::round((1.0*len(y_norm))/(1.0*noSamples))]

            """
            noOfAppendZeros = 15000 - len(y)
            y = np.append(y,[0.0]*noOfAppendZeros)

            rmse = librosa.feature.rmse(y=y).tolist()[0]
            delta = librosa.feature.delta(y)

            delta_chunks = list(split(delta, 50))
            delta_mean = [(sum(abs(chunk))*1.0)/len(chunk) for chunk in delta_chunks]

            mfccs = librosa.feature.mfcc(y,n_mfcc=12).tolist()
            mfccs_flat = [item for sublist in mfccs for item in sublist]

            features = mfccs_flat + rmse + delta_mean

            #print(mfccs_flat)
            #print(len(mfccs_flat))


            """
            #print("C:/Users/leonx64/Desktop/comp_intel_project/"+fol+"/"+file)
            song = AudioSegment.from_wav("C:/Users/leonx64/Desktop/comp_intel_project/"+fol+"/"+file)
            strCoded = str(binascii.hexlify(song._data))[2:-1]
            chunks = [strCoded[i:i+4] for i in range(0, len(strCoded), 4)]
            floated = [int(x,16)*1.0 for x in chunks]
            maxFloated = max(floated)
            sclInted = [(65535.0/maxFloated)*x for x in floated]
            #sclInted = list(filter(lambda a: a!=0,sclInted))
            sclInted = sclInted[0::int(len(sclInted)/1000)]
            while (len(sclInted)<1496):
                sclInted.append(0.0)
            #sclInted = sclInted[:50]
            """

            wavs[ind].append([features,numToOneHot(ind)])


#print(max(hold))
#print(min(hold))

print("Loading data over...")
X_trainAll = []
X_testAll = []
y_trainAll = []
y_testAll = []
for allInOneClass in wavs:
    features = []
    labels = []
    for pair in allInOneClass:
        features.append(pair[0])
        labels.append(pair[1])
    X_train, X_test, y_train, y_test = train_test_split(features,labels , test_size=0.2, random_state=42)

    X_trainAll = X_trainAll + X_train
    X_testAll = X_testAll + X_test
    y_trainAll = y_trainAll + y_train
    y_testAll = y_testAll + y_test

scaler = preprocessing.StandardScaler()
X_trainAll = scaler.fit_transform(np.array(X_trainAll))
X_testAll = scaler.transform(np.array(X_testAll))
y_trainAll = np.array(y_trainAll)
y_testAll = np.array(y_testAll)

print(len(X_trainAll))
print(len(X_testAll))
print(len(y_trainAll))
print(len(y_testAll))
print("Separating data over...")


# Training Parameters
learning_rate = 0.001
training_steps = 10000
batch_size = 120
display_step = 50

# Network Parameters
num_input = 10 # data input
timesteps = 44 # timesteps
num_hidden = 80 # hidden layer num of features
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
        #lstm_cell = tf.nn.dropout(lstm_cell, d_o)
        lstm_cells.append(lstm_cell)
    lstm_cells = rnn.MultiRNNCell(lstm_cells)
    # Get lstm cell output
    outputs, states = rnn.static_rnn(lstm_cells, x, dtype=tf.float32)

    # Linear activation, using rnn inner loop last output
    return tf.matmul(outputs[-1], weights['out']) + biases['out']



logits = RNN(X, weights, biases, dropout)

print("Init RNN over...")
prediction = tf.nn.softmax(logits)

# Define loss and optimizer
loss_op = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(
    logits=logits, labels=Y))
optimizer = tf.train.RMSPropOptimizer(learning_rate=learning_rate)
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
                                                                 Y: y_trainAll, dropout: 1})
            print("Step " + str(step) + ", Minibatch Loss= " + \
                  "{:.4f}".format(loss) + ", Training Accuracy= " + \
                  "{:.3f}".format(acc))
            # Calculate accuracy
            test_data = X_testAll.reshape((-1, timesteps, num_input))
            test_label = y_testAll
            print("Testing Accuracy:", sess.run(accuracy, feed_dict={X: test_data, Y: test_label, dropout: 1}))

    print("Optimization Finished!")


