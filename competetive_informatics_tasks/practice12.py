n = input()
n = int(n)
tree = {}
for index in range(1,n+1):
    tree[index] = []

raw = input()
while(not raw == "x"):
    raw = raw.split()
    rawINTed = [int(x) for x in raw]
    tree[rawINTed[0]].append((rawINTed[1],rawINTed[2]))
    tree[rawINTed[1]].append((rawINTed[0],rawINTed[2]))
    raw = input()
print(tree)
maximumxor=0
stack = []
for index in range(1,n+1):
    for edge in tree[index]:
        if(edge[1]>maximumxor):
            maximumxor = edge[1]
        stack.append((edge[0],edge[1],-1))
    while(not len(stack)==0):
        stackTuple = stack.pop()
        for edge in tree[stackTuple[0]]:
            if(edge[0]==stackTuple[2]):
                continue
            newxor = edge[1]^stackTuple[1]
            if(newxor>maximumxor):
                maximumxor = newxor
            stack.append((edge[0],newxor,stackTuple[0]))
print(maximumxor)
