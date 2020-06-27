import copy

testcases = int(input())
for testcase in range(0,testcases):
    n, k = [int(x) for x in input().split()]
    finsum = n - k

    stack = []
    for i in range(0,finsum+1):
        stack.append([i])
    count = 0
    while(len(stack)>0):
        current = stack.pop()
        sumcurrent = sum(current)
        if(len(current)==k):
            count += 1
            continue

        if(len(current)==k-1):
            currentCopy = copy.deepcopy(current)
            currentCopy.append(finsum-sum(current))
            stack.append(currentCopy)
        else:
            for i in range(0,finsum+1-sumcurrent):
                currentCopy = copy.deepcopy(current)
                currentCopy.append(i)
                stack.append(currentCopy)

    print(count)