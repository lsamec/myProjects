import copy
testcases = int(input())
for testcase in range(0,testcases):
    citiesA = list(input())
    citiesB = list(input())

    stack = []
    stack.append((citiesA,citiesB,0))
    finals = []
    while(not len(stack) == 0):
        cities = stack.pop()
        index = cities[2]
        endBool = False
        if(index >= len(cities[0]) or index >= len(cities[1])):
            continue

        while(cities[0][index] == cities[1][index]):
            index+=1
            if (index >= len(cities[0]) or index >= len(cities[1])):
                final = "".join(cities[0][0:min(len(cities[0]),len(cities[1]))])
                if final in finals:
                    break
                finals.append(final)
                endBool = True
                break
        if(endBool):
            continue
        citiesAcopy1 = copy.deepcopy(cities[0])
        citiesBcopy1 = copy.deepcopy(cities[1])
        citiesAcopy2 = copy.deepcopy(cities[0])
        citiesBcopy2 = copy.deepcopy(cities[1])

        if(index >= len(citiesAcopy1) or index >= len(citiesBcopy2)):
            continue

        del citiesAcopy1[index]
        stack.append((citiesAcopy1,citiesBcopy1,index))
        del citiesBcopy2[index]
        stack.append((citiesAcopy2,citiesBcopy2,index))

    finals.sort(key=lambda x: len(x),reverse=True)

    newfinals = []
    for ele in finals:
        if len(ele) == len(finals[0]):
            newfinals.append(ele)
        else:
            break
    newfinals.sort()
    for ele in newfinals:
        print(ele)