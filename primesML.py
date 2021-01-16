from tensorflow.keras.layers import Dense
from tensorflow.keras.models import Sequential
import numpy as np
import tensorflow as tf
import math
import random

def is_prime(n):
  if n == 2 or n == 3: return 1
  if n < 2 or n%2 == 0: return 0
  if n < 9: return 1
  if n%3 == 0: return 0
  r = int(n**0.5)
  # since all primes > 3 are of the form 6n ± 1
  # start with f=5 (which is prime)
  # and test f, f+2 for being prime
  # then loop by 6.
  f = 5
  while f <= r:
    if n % f == 0: return 0
    if n % (f+2) == 0: return 0
    f += 6
  return 1

maxlayer1 = 25000
parts = 10
layer1s = []
for i in range(1, parts):
    layer1s.append(maxlayer1*((1.0*i)/(parts*1.0)))

for layer1 in layer1s:
    model = Sequential()
    model.add(Dense(units=int(layer1), input_dim=1, activation='relu'))
    model.add(Dense(units=int(layer1)/2, activation='relu'))
    model.add(Dense(units=int(layer1)/4, activation='relu'))
    model.add(Dense(units=int(layer1)/8, activation='relu'))
    model.add(Dense(units=1, activation='sigmoid'))

    end_number = 3000

    class_weight = {1: (end_number - int(end_number/math.log(end_number,math.e)))/(end_number*1.0),
                    0: int(end_number/math.log(end_number,math.e))/(end_number*1.0)}

    model.compile(loss='binary_crossentropy',
                  optimizer='sgd',
                  metrics=['accuracy', tf.keras.metrics.TruePositives(),tf.keras.metrics.TrueNegatives(),tf.keras.metrics.FalsePositives(),tf.keras.metrics.FalseNegatives()])

    numbers = []
    numbersIsPrime = []
    for i in range(1, end_number):
        num = random.randint(1, end_number)
        numbers.append([num * 1.0])
        numbersIsPrime.append([is_prime(num) * 1.0])

    x_train = np.array(numbers)
    y_train = np.array(numbersIsPrime)

    #print(x_train)
    #print(y_train)

    #print("Starting train")
    # x_train and y_train are Numpy arrays --just like in the Scikit-Learn API.
    model.fit(x_train, y_train, epochs=5, batch_size=60, class_weight=class_weight)
    #print("Finished train")

    numbers = []
    numbersIsPrime = []
    for i in range(1, end_number):
        num = random.randint(1, end_number)
        numbers.append([num * 1.0])
        numbersIsPrime.append([is_prime(num) * 1.0])

    x_test = np.array(numbers)
    y_test = np.array(numbersIsPrime)

    #print("Starting test")
    loss_and_metrics = model.evaluate(x_test, y_test, batch_size=128)
    #print("Finished test")

    print(loss_and_metrics)

    classes = model.predict(x_test, batch_size=128)

    #print(classes)

