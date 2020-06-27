import copy
import collections
testcases = int(input())
for testcase in range(0,testcases):
    n=int(input())
    listofwords = []
    for wordindex in range(0,n):
        listofwords.append(input())
    counterListOfWords = collections.Counter(listofwords)
    stack = []
    for word in listofwords:
        stack.append([word])

    can = False
    while(not len(stack)==0):
        seq = stack.pop()
        if(len(seq)==len(listofwords)):
            can = True
            break
        for word in listofwords:
            seqcopy = copy.deepcopy(seq)
            seqcopy.append(word)
            if (collections.Counter(seqcopy)[word] <= counterListOfWords[word] and seq[-1][len(seq[-1])-1]==word[0]):
                stack.append(seqcopy)
    if(can):
        print("Ordering is possible.")
    else:
        print("The door cannot be opened.")