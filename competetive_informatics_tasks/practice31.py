testcases = int(input())
for testcase in range(0,testcases):
    bitmap = {}
    dim = [int(x) for x in input().split()]
    for y in range(0,dim[0]):
        row = input()
        for bitindex in range(0,len(row)):
            bitmap[(bitindex,y)] = int(row[bitindex])

    bitmapdist = {}

    for x in range(0,dim[1]):
        for y in range(0,dim[0]):
            queue = []
            queue.append((x,y,0))
            wasinqueue = []
            wasinqueue.append((x,y))
            while(len(queue)>0):
                current = queue.pop(0)
                if bitmap[(current[0],current[1])] == 1:
                    bitmapdist[(x,y)] = current[2]
                    break

                if(not (current[0]-1 < 0) and not ((current[0]-1,current[1]) in wasinqueue)):
                    queue.append((current[0]-1,current[1],current[2]+1))
                    wasinqueue.append((current[0]-1,current[1]))

                if(not (current[0]+1 >= dim[1]) and not ((current[0]+1,current[1]) in wasinqueue)):
                    queue.append((current[0]+1,current[1],current[2]+1))
                    wasinqueue.append((current[0]+1,current[1]))

                if(not (current[1]-1 < 0) and not ((current[0],current[1]-1) in wasinqueue)):
                    queue.append((current[0],current[1]-1,current[2]+1))
                    wasinqueue.append((current[0],current[1]-1))

                if(not (current[1]+1 >= dim[0]) and not ((current[0],current[1]+1) in wasinqueue)):
                    queue.append((current[0],current[1]+1,current[2]+1))
                    wasinqueue.append((current[0],current[1]+1))

    for y in range(0,dim[0]):
        outrow = str(bitmapdist[(0,y)])
        for x in range(1,dim[1]):
            outrow += " " + str(bitmapdist[(x,y)])
        print(outrow)


