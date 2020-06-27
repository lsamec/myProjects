
k,n = [int(x) for x in input().split()]

base = k // n
baseList = [base]*n

i=1
for index in range(0,len(baseList)//2):
    index1 = 2*index
    index2 = 2*index + 1
    baseList[index1] = baseList[index1]+i
    baseList[index2] = baseList[index2]-i
    i+=1

baseList.sort()

baseList[len(baseList)-1]+= k % n
print(baseList)

