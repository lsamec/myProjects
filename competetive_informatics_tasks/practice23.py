import copy

testcases = int(input())
n=None
k=None
for testcase in range(0,testcases):
    raw = [int(x) for x in input().split()]
    n = raw[0]
    k = raw[1]

orderedSeq = range(1,n+1)
stack = []
for num in orderedSeq:
    stack.append([num])

kCounter = 0
while(len(stack)>0):
    currentSeq = stack.pop()
    if(len(currentSeq)==n):
        inversions = 0
        for i in range(0, len(currentSeq)-1):
            for j in range(i, len(currentSeq)):
                if (currentSeq[i]>currentSeq[j]):
                    inversions+=1
        if(inversions==k):
            kCounter+=1
    for num in orderedSeq:
        if num in currentSeq:
            continue
        currentSeqCopy = copy.deepcopy(currentSeq)
        currentSeqCopy.append(num)
        stack.append(currentSeqCopy)

print(kCounter)