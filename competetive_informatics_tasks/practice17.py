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

testcases = int(input())
for testcase in range(0,testcases):

    nc = input().split()
    n = int(nc[0])
    c = int(nc[1])
    positions = []
    for pos in range(0,n):
        positions.append(int(input()))
    positions.sort()
    cowpositions = []

    for cow in range(1,c+1):
        if(cow == 1):
            cowpositions.append(positions[0])
            continue
        if (cow==2):
            cowpositions.append(positions[-1])
            continue

        maxdistance = -1
        index = -1
        for pos in positions:
            if(pos not in cowpositions):
                for cowindex in range(0,len(cowpositions)-1):
                    if (pos > cowpositions[cowindex] and pos < cowpositions[cowindex+1]):
                        left = cowpositions[cowindex]
                        right = cowpositions[cowindex+1]
                        break
                distleft = pos - left
                distright = right - pos
                dist = min(distleft,distright)
                if(maxdistance == -1):
                    maxdistance = dist
                    index = positions.index(pos)
                else:
                    if(maxdistance < dist):
                        maxdistance = dist
                        index = positions.index(pos)

        cowpositions.append(positions[index])
        cowpositions.sort()

    print(calcminimumdistance(cowpositions))