import math
testcases = int(input())
for testcase in range(0,testcases):
    n, k = [int(x) for x in input().split()]
    print(int(math.factorial(n-k+k-1)/(math.factorial(n-k)*math.factorial(k-1))))