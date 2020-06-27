testcases = int(input())
for testcase in range(0,testcases):
    a = int(input())
    b = int(input())
    c = int(input())

    if(a < c and b < c):
        print(-1)
        exit()
    solutions = []
    minab = min(a,b)
    maxab = max(a,b)
    if(c==minab - (maxab - minab*(maxab//minab))):
        sol = 2*((maxab//minab)+1)
        solutions.append(sol)
    if(c % minab == 0):
        sol = 2*(c//minab)
        if (sol==2):
            sol=1
        solutions.append(sol)
    if((maxab-c) % minab == 0):
        sol = 1+2*((maxab-c)//minab)-1
        solutions.append(sol)
    if(len(solutions)==0):
        print(-1)
    else:
        print(min(solutions))