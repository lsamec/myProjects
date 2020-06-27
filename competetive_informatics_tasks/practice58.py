import copy

elements = list(map(lambda x: int(x), str(input()).split(' ')))
difference = max(elements) - min(elements)

def additionDictGood(additionDict):
    for value in additionDict.values():
        if abs(value) > difference:
            return False
    return True

def getNumberOfOperationInAdditionDict(additionDict):
    noOfOperation = 0
    for key in additionDict.keys():
        noOfOperation += abs(additionDict[key])
    return noOfOperation

def applyAdditionDict(additionDict):
    elementsCopy = copy.deepcopy(elements)
    for i in range(0,len(elements)):
        elementsCopy[i] += additionDict[i]
    return elementsCopy

def isSorted(elements):
    for i in range(0,len(elements)-1):
        if elements[i] > elements[i+1]:
            return False
    return True


additionDict = {}

for i in range(0, len(elements)):
    additionDict[i] = 0

elementsNew = applyAdditionDict(additionDict)
if(isSorted(elementsNew)):
    print(elementsNew,getNumberOfOperationInAdditionDict(additionDict))
    exit(0)

stack = []
wasOnStack = []

for i in range(0,len(elements)):
    additionDictCopy = copy.deepcopy(additionDict)
    additionDictCopy[i] += 1
    stack.insert(0,additionDictCopy)
    wasOnStack.append(additionDictCopy)

for i in range(0,len(elements)):
    additionDictCopy = copy.deepcopy(additionDict)
    additionDictCopy[i] -= 1
    stack.insert(0,additionDictCopy)
    wasOnStack.append(additionDictCopy)

while(len(stack)>0):
    additionDict = stack.pop()

    elementsNew = applyAdditionDict(additionDict)
    if(isSorted(elementsNew)):
        print(elementsNew,getNumberOfOperationInAdditionDict(additionDict))
        exit(0)

    for i in range(0,len(elements)):
        additionDictCopy = copy.deepcopy(additionDict)
        if additionDictCopy[i] >= 0:
            additionDictCopy[i] += 1
            if additionDictGood(additionDictCopy) and additionDictCopy not in wasOnStack:
                stack.insert(0,additionDictCopy)
                wasOnStack.append(additionDictCopy)

    for i in range(0,len(elements)):
        additionDictCopy = copy.deepcopy(additionDict)
        if additionDictCopy[i] <= 0:
            additionDictCopy[i] -= 1
            if additionDictGood(additionDictCopy) and additionDictCopy not in wasOnStack:
                stack.insert(0,additionDictCopy)
                wasOnStack.append(additionDictCopy)