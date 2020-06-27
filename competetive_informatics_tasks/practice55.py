import copy

elements = list(map(lambda x: int(x), str(input()).split(' ')))
finalSum = 10

goodSets = []

stack = []
for i in range(0,len(elements)):
    stack.append([[elements[i]],i+1])
while(len(stack) > 0):
    pack = stack.pop()
    set, index = pack[0], pack[1]
    if sum(set) == finalSum:
        goodSets.append(set)
    if index >= len(elements):
        continue
    for i in range(index,len(elements)):
        setCopy = copy.deepcopy(set)
        setCopy.append(elements[i])
        stack.append([setCopy,i+1])

print(goodSets)