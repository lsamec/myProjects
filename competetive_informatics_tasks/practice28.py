length = int(input())
firstArray = input().split()
secondArray = input().split()
variables = {}
for index in range(0,length):
    if(not firstArray[index].isdigit()):
        variables[firstArray[index]] = None
    if(not secondArray[index].isdigit()):
        variables[secondArray[index]] = None
    if(firstArray[index].isdigit() and secondArray[index].isdigit()):
        if(firstArray[index] != secondArray[index]):
            print("NE")
            exit()

for index in range(0,length):
    if(firstArray[index].isdigit() and (not secondArray[index].isdigit())):
        variables[secondArray[index]] = firstArray[index]

    if((not firstArray[index].isdigit()) and secondArray[index].isdigit()):
        variables[firstArray[index]] = secondArray[index]

for index in range(0,length):
    if((not firstArray[index].isdigit()) and variables[firstArray[index]] is not None) :
        firstArray[index] = variables[firstArray[index]]
    if((not secondArray[index].isdigit()) and variables[secondArray[index]] is not None) :
        secondArray[index] = variables[secondArray[index]]

for index in range(0,length):
    if(firstArray[index].isdigit() and secondArray[index].isdigit()):
        if(firstArray[index] != secondArray[index]):
            print("NE")
            exit()
print("DA")



