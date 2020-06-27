
testcases = int(input())
for testcase in range(0,testcases):
    num = input()
    if(len(set(num).difference("9"))==0):
        print("".join(["1","0"*(len(num)-1),"1"]))
        continue
    if(len(num)%2 == 1):
        firstPart = num[0:((len(num)//2))]
        middle = num[(len(num)//2)]
        secondPart = num[((len(num)//2) + 1):]
        firstPartRev = firstPart[::-1]
        if(middle != "9"):
            if(firstPartRev>secondPart):
                secondPart = firstPartRev
                print("".join([firstPart,middle,secondPart]))
                continue
            if(firstPartRev<=secondPart):
                middle = str(int(middle)+1)
                secondPart = firstPartRev
                print("".join([firstPart,middle,secondPart]))
                continue
        else:
            if(firstPartRev>secondPart):
                secondPart = firstPartRev
                print("".join([firstPart,middle,secondPart]))
                continue
            if(firstPartRev<=secondPart):
                firstPart = str(int(firstPart)+1)
                middle = "0"
                secondPart = firstPart[::-1]
                print("".join([firstPart,middle,secondPart]))
                continue
    if(len(num)%2 == 0):
        firstPart = num[0:((len(num)//2))]
        secondPart = num[((len(num)//2)):]
        firstPartRev = firstPart[::-1]
        if(firstPartRev>secondPart):
            secondPart = firstPartRev
            print("".join([firstPart,secondPart]))
            continue
        if(firstPartRev<=secondPart):
            firstPart = str(int(firstPart)+1)
            secondPart = firstPart[::-1]
            print("".join([firstPart,secondPart]))
            continue


