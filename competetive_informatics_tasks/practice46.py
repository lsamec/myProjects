import copy
boardsize = 8
stack = []
for i in range(1,boardsize+1):
    stack.append([[[1,i]],2,[i]])
count = 0
while(len(stack)>0):
    current = stack.pop()
    if(len(current[0])==boardsize):
        count+=1
    for i in range(1,boardsize+1):
        newqueen = [current[1],i]
        if(i in current[2]):
            continue
        skip = False
        for queen in current[0]:
            if(skip):
                break
            for j in range(1,boardsize+1):
                if(queen[0]==newqueen[0]-j and queen[1]==newqueen[1]-j or queen[0]==newqueen[0]+j and queen[1]==newqueen[1]-j or queen[0]==newqueen[0]-j and queen[1]==newqueen[1]+j or queen[0]==newqueen[0]+j and queen[1]==newqueen[1]+j):
                    skip = True
                    break
        if(skip):
            continue
        new = copy.deepcopy(current)
        new[0].append(newqueen)
        new[1] = new[1]+1
        new[2].append(i)
        stack.append(new)

print(count)