testcases = int(input())
for testcase in range(0,testcases):
    input()
    n = int(input())
    array = []
    for i in range(0,n):
        array.append(int(input()))
    inversions = 0
    for i in range(0, len(array)-1):
        for j in range(i, len(array)):
            if (array[i]>array[j]):
                inversions+=1

    print(inversions)