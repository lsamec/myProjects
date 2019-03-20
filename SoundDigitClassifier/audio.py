from pydub import AudioSegment
import binascii
from os import listdir
import numpy as np
from sklearn.model_selection import train_test_split

def numToOneHot(num):
    oneHotV = [0]*10
    oneHotV[num] = 1
    return oneHotV
wavs =[]
lens = []
for fol, ind in zip(listdir("C:/Users/leonx64/Desktop/comp_intel_project"),range(10)):
    wavs.append([])
    for file in listdir("C:/Users/leonx64/Desktop/comp_intel_project/"+fol):
        print("C:/Users/leonx64/Desktop/comp_intel_project/"+fol+"/"+file)
        song = AudioSegment.from_wav("C:/Users/leonx64/Desktop/comp_intel_project/"+fol+"/"+file)
        strCoded = str(binascii.hexlify(song._data))[2:-1]
        chunks = [strCoded[i:i+4] for i in range(0, len(strCoded), 4)]
        floated = [int(x,16)*1.0 for x in chunks]
        maxFloated = max(floated)
        sclInted = [(65535.0/maxFloated)*x for x in floated]
        """
        while (len(sclInted)<70320):
            sclInted.append(0.0)
        """
        #sclInted = list(filter(lambda a: a!=0,sclInted))
        sclInted = sclInted[0::int(len(sclInted)/1000)]
        #while (len(sclInted)<605):
        #    sclInted.append(0.0)
        #sclInted = sclInted[:50]
        lens.append(len(sclInted))
        wavs[ind].append([sclInted,numToOneHot(ind)])
print(max(lens))
print(min(lens))

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
    """
    for x in X_train:
        X_trainAll.append(x)
    for x in X_test:
        X_testAll.append(x)
    for x in y_train:
        y_trainAll.append(x)
    for x in y_test:
        y_testAll.append(x)
    """
    X_trainAll = X_trainAll + X_train
    X_testAll = X_testAll + X_test
    y_trainAll = y_trainAll + y_train
    y_testAll = y_testAll + y_test

X_trainAll = np.array(X_trainAll)
X_testAll = np.array(X_testAll)
y_trainAll = np.array(y_trainAll)
y_testAll = np.array(y_testAll)

print(X_trainAll.shape)
print(X_testAll.shape)
print(y_trainAll.shape)
print(y_testAll.shape)



print(wavs[0][0])
"""
song = AudioSegment.from_wav("C:/Users/leonx64/Desktop/comp_intel_project/0/0Da1.wav")

strCoded = str(binascii.hexlify(song._data))[2:-1]
chunks = [strCoded[i:i+4] for i in range(0, len(strCoded), 4)]
floated = [int(x,16)*1.0 for x in chunks]
maxFloated = max(floated)
sclInted = [(65535.0/max(floated))*x for x in floated]
while (len(sclInted)<63600):
    sclInted.append(0.0)
print(len(sclInted))
"""