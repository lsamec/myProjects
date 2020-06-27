def deg(edges, index, side):
    deg = 0
    for edge in edges:
        if(edge[side] == index):
            deg+=1
    return deg

raw = input()
raw = raw.split()
n = int(raw[0])
m = int(raw[1])
d = int(raw[2])
D = int(raw[3])
edges = []
nodes = []

for index in range(1,n+1):
    nodes.append((index,0))
    nodes.append((index,1))
for edgeindex in range(0,m):
    nodesSorted = sorted(nodes, key=lambda e: (deg(edges,e[0],e[1])))
    mindeg = nodesSorted[0]
    secondmindeg = None
    for node,index in zip(nodesSorted,range(0,len(nodesSorted))):
        if (index ==0):
            continue
        if(node[1] != mindeg[1]):
            if(node[1] == 1):
                if((mindeg[0],node[0]) in edges):
                    continue
            else:
                if((node[0],mindeg[0]) in edges):
                    continue
            secondmindeg = node
            break

    firstnode = None
    secondnode = None
    if(mindeg[1] == 0):
        firstnode = mindeg
        secondnode = secondmindeg
    else:
        firstnode = secondmindeg
        secondnode = mindeg
    edges.append((firstnode[0],secondnode[0]))

nodesSorted = sorted(nodes, key=lambda e: (deg(edges,e[0],e[1])))
nodesSortedRev = sorted(nodes, key=lambda e: (deg(edges,e[0],e[1])), reverse = True)

if(deg(edges,nodesSorted[0][0],nodesSorted[0][1]) < d):
    print(-1)
    exit()

if(deg(edges,nodesSortedRev[0][0],nodesSortedRev[0][1]) > D):
    print(-1)
    exit()

for edge in edges:
    print(edge)
