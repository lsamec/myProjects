import copy

while(True):
    noofnums = int(input())
    if(noofnums == 0):
        exit()
    nums = [int(x) for x in input().split()]
    sortednums = sorted(nums)
    stack = []
    stack.append([nums,0])
    minsum = -1
    while(len(stack)>0):
        current = stack.pop()
        notongoodplaces = []
        if(minsum!=-1):
            if(current[1]>=minsum):
                continue
        for i in range(0,len(nums)):
            if (current[0][i]!=sortednums[i]):
                notongoodplaces.append(i)
        if(len(notongoodplaces)==0):
            if(minsum==-1):
                minsum = current[1]
            else:
                if (current[1]< minsum):
                    minsum = current[1]

        for notongoodplace in notongoodplaces:
            numsCopy = copy.deepcopy(current[0])
            ongoodplace = sortednums.index(numsCopy[notongoodplace])
            numsCopy[notongoodplace], numsCopy[ongoodplace] = numsCopy[ongoodplace], numsCopy[notongoodplace]
            sum = current[1]+ numsCopy[notongoodplace] + numsCopy[ongoodplace]
            stack.append([numsCopy,sum])
    print(minsum)
