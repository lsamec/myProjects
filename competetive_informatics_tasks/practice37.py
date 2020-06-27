testcases = int(input())
for testcase in range(0,testcases):
    x,y = [int(x) for x in input().split()]

    if(x==y):
        if(x%2==0):
            print((x//2)*1+(x//2)*3)
        else:
            print((x//2)*1+(x//2)*3+1)
    else:
        if (y == x - 2):
            if(y%2==0):
                print(2+(y//2)*1+(y//2)*3)
            else:
                print(2+(y//2)*1+(y//2)*3+1)
        else:
            print("No Number")
