import matplotlib.pyplot

def nextSum(previousSum, summed):
    newSum = []
    highestInt = None
    indexes = list(range(0,len(previousSum)))
    indexes.reverse()
    for index in indexes:
        if(previousSum[index] > 1):
            newSum = previousSum[0:index]
            newSum.append(previousSum[index]-1)
            highestInt = previousSum[index]-1
            break
    while(sum(newSum) < summed):
        currentSum = sum(newSum)
        nextInt = highestInt
        while(nextInt >= 1):
            if nextInt+currentSum > summed:
                nextInt -= 1
            else:
                newSum.append(nextInt)
                break
    return newSum

def lastSum(sum):
    for part in sum:
        if (part != 1):
            return False
    return True

"""
print(nextSum([100],100))
"""
sumNumbers = []
for number in range(1,41):
    aSum = [number]
    numberOfAdditions = 1
    while(not lastSum(aSum)):
        aSum = nextSum(aSum,number)
        numberOfAdditions+=1

    sumNumbers.append(numberOfAdditions)
    print(number," ",numberOfAdditions)

matplotlib.pyplot.plot(range(1,41),sumNumbers)
matplotlib.pyplot.show()
