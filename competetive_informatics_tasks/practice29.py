import copy

firstArray = [int(x) for x in input().split()]
secondArray = [int(x) for x in input().split()]

if (len(firstArray)<len(secondArray)):
    firstArray,secondArray = secondArray,firstArray

n = len(firstArray)
m = len(secondArray)

firstArray.sort()
secondArray.sort()

stack = []
pos = 0
for i in range(0,n-m+1):
    stack.append([i])
print(firstArray,secondArray,m,n)
minugl = -1
while(not len(stack)==0):
    current = stack.pop()
    print(current)
    if(len(current)==m):
        ugl = max([abs(firstArray[current[i]] - secondArray[i]) for i in range(0,len(current))])
        if(minugl == -1):
            minugl = ugl
        else:
            if(minugl> ugl):
                minugl = ugl
        continue
    for i in range(current[len(current)-1]+1,n-m+1+(len(current)-1)+1):
        currentCopy = copy.deepcopy(current)
        currentCopy.append(i)
        stack.append(currentCopy)
print(minugl)