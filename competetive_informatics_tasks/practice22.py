def getNerbySpaces(x,y):
    return [(x,y-1),(x,y+1),(x+1,y),(x-1,y)];
testcases = int(input())
for testcase in range(0,testcases):
    dim = [int(x) for x in input().split()]
    map = {}
    for lineIndex in range(0,dim[1]):
        line = input()
        for charIndex in range(0,len(line)):
            map[(charIndex,lineIndex)] = line[charIndex]
    startingPoints1 = []
    startingPoints2 = []
    startingPoints = []
    for x in range(1,dim[0]-1):
        for y in range(1,dim[1]-1):
            if(map[(x,y)]=="."):
                nerbyDots = 0
                nerbySpaces = getNerbySpaces(x,y)
                for nerbySpace in nerbySpaces:
                    if(map[nerbySpace] == "."):
                        nerbyDots+=1
                if(nerbyDots==1):
                    startingPoints1.append((x,y))
                else:
                    if(nerbyDots==2):
                        startingPoints2.append((x,y))
    if(len(startingPoints1) == 0):
        startingPoints = startingPoints2
    else:
        startingPoints = startingPoints1

    maxLength = 0
    for startingPoint in startingPoints:
        stack = []
        stack.append([startingPoint,startingPoint,0])
        while(not len(stack) == 0):
            point,lastSpace,upToNowLenght = stack.pop()
            nerbySpaces = getNerbySpaces(point[0],point[1])
            step = False
            for nerbySpace in nerbySpaces:
                if(map[nerbySpace]=="." and nerbySpace != lastSpace):
                    stack.append([nerbySpace,point,upToNowLenght+1])
                    step=True
            if(step==False):
                if(upToNowLenght>maxLength):
                    maxLength = upToNowLenght

    print("Maximum rope length is %d." % (maxLength))