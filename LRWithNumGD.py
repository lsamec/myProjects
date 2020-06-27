import math
from statistics import stdev

X = []
divisions = 100
for x in range(-divisions,divisions):
    X.append(x/(1.0*divisions))

fxsinx = []
for x in X:
    fxsinx.append(math.sin(x))

fxx = X

def se(x):
    sum = 0.0
    for i in range(0,len(X)):
        sum += (fxsinx[i] - x * fxx[i])*(fxsinx[i] - x * fxx[i])
    return sum

bounds = [-5.0,5.0]
delta = 0.01

minresultx = math.inf
minx = math.inf
numberOfIterationsWithoutDecreasing = 0
currentx = 0.0

while(True):
    currentxplus = currentx + delta
    currentxminus = currentx - delta

    resultx = se(currentx)
    resultxplus = se(currentxplus)
    resultxminus = se(currentxminus)

    if(minresultx > resultx):
        minresultx = resultx
        minx = currentx
        numberOfIterationsWithoutDecreasing = 0
        numberOfIterationsWithoutDecreasing += 1
    else:
        if(numberOfIterationsWithoutDecreasing > 100):
            break

    if resultxminus < resultx and resultx < resultxplus:
        currentx = currentx - delta

    if resultxminus > resultx and resultx > resultxplus:
        currentx = currentx + delta

    if resultxminus > resultx and resultx < resultxplus:
        break

print(minresultx)