
testcases = int(input())
for testcase in range(0,testcases):
    n=int(input())
    ithlucky = 0
    testHolder = 30
    exitbool = False
    while(True):
        test = testHolder
        primefacts = []
        endbool = False
        for div in range(2,(test//2)+1):
            while(test % div == 0):
                if(div not in primefacts):
                    primefacts.append(div)
                    if(len(primefacts)==3):
                        ithlucky+=1
                        if(ithlucky==n):
                            print(testHolder)
                            exitbool = True
                        endbool = True
                        break
                test = test//div
            if(endbool):
                break
        if(exitbool):
            break
        testHolder+=1