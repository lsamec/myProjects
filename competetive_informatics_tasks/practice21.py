testcases = int(input())
for testcase in range(0,testcases):
    input()
    n = int(input())
    array = []
    for i in range(0,n):
        array.append(int(input()))
    inversions = 0

    arraysorted = sorted(array)


    print(inversions)