import copy

def calcminimumdistance(cowpositions):
    mindist = -1
    for i in range(0,len(cowpositions)-1):
        distance = cowpositions[i+1] - cowpositions[i]
        if(mindist == -1):
            mindist = distance
        else:
            if(distance<mindist):
                mindist = distance
    return mindist

nc = input().split()
n = int(nc[0])
c = int(nc[1])
positions = [int(x) for x in input().split()]
positions.sort()
cowpositions = []
stack = []
for pos in positions:
    cowpositions = []
    cowpositions.append(pos)
    stack.append(cowpositions)

maxdist= 0
while(not len(stack)==0):
    cowpositions = stack.pop()
    for pos in positions:
        if pos > cowpositions[-1]:
            cowpositionscopy = copy.deepcopy(cowpositions)
            cowpositionscopy.append(pos)
            stack.append(cowpositionscopy)
    if(len(cowpositions) == c):
        mindist = calcminimumdistance(cowpositions)
        if(mindist>maxdist):
            maxdist = mindist


print(maxdist)
