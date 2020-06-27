testcases = int(input())
for testcase in range(0,testcases):
    n = int(input())
    arr = [int(x) for x in input().split()]
    stack = []
    for ind in range(0,len(arr)):
        stack.append([[arr[ind]],ind+1])
    count = 0
    while(len(stack)>0):
        current = stack.pop()
        if (sum(current[0])==0):
            count+=1
        if(current[1] >= len(arr)):
            continue
        current[0].append(arr[current[1]])
        current[1] = current[1]+1
        stack.append(current)
    print(count)