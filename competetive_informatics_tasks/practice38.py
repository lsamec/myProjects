import copy
try:
    testcases = int(input())
    for testcase in range(0,testcases):
        oxy, nitro = [int(x) for x in input().split()]
        noofconts = int(input())
        conts = []
        for cont in range(0,noofconts):
            conts.append([int(x) for x in input().split()] + [cont])

        stack = []
        for contindex in range(0,len(conts)):
            cont = conts[contindex]
            stack.append( [[cont],[cont[3]],cont[0],cont[1],cont[2]] )

        minsum = -1
        while(len(stack)>0):
            current = stack.pop()
            if(minsum != -1):
                if current[4] >= minsum:
                    continue
            if(current[2]>=oxy and current[3]>=nitro):
                if(minsum==-1):
                    minsum = current[4]
                else:
                    if(current[4]<minsum):
                        minsum = current[4]
                continue
            remainingconts = list(set(list(range(0,noofconts))).difference(set(current[1])))
            for contindex in remainingconts:
                currentCopy = copy.deepcopy(current)
                newCont = conts[contindex]
                currentCopy[0].append(newCont)
                currentCopy[1].append(newCont[3])
                currentCopy[2] = currentCopy[2] + newCont[0]
                currentCopy[3] = currentCopy[3] + newCont[1]
                currentCopy[4] = currentCopy[4] + newCont[2]
                stack.append(currentCopy)

        print(minsum)
except:
    pass
