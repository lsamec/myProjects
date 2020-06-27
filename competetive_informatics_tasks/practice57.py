elements = str(input()).split(' ')

countDict = {}
for elem in range():
    countDict[elem] = 0

for elem in elements:
    countDict[elem] += 1

zero = ""
two = ""
for key in countDict.keys():
    if countDict[key] == 0:
        zero = key
    if countDict[key] == 2:
        two = key

print(zero,two)