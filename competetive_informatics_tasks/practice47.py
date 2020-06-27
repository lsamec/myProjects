import functools
for tc in range(0,10):
    n = int(input())
    perm = [int(x) for x in input().split()]

    stack = []
    subseq = []
    leng = len(perm)
    for index in range(0,leng):
        stack.append([[perm[index]],index+1])

    maxlen = -1
    while(len(stack)>0):
        current = stack.pop()
        if(current[1]>=leng):
            if(maxlen==-1):
                maxlen = len(current[0])
            else:
                if(len(current[0])>maxlen):
                    maxlen = len(current[0])
            subseq.append(current[0])
            continue
        if(perm[current[1]] > current[0][-1]):
            current[0].append(perm[current[1]])
        current[1] = current[1]+1
        stack.append(current)

    maxsizes = list(filter(lambda x: (len(x) == maxlen),subseq))
    supernums = list(functools.reduce(lambda a,b: set(a).union(set(b)),maxsizes))
    supernums.sort()
    print(len(supernums))
    print(" ".join(list(map(lambda x: str(x),supernums))))




