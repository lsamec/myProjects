testcases = int(input())
for testcase in range(0,testcases):
    word = list(input())
    allchars = set(word)
    end = False
    for wsize in range(1,len(word)+1):
        if(end):
            break
        for startingpoint in range(0,len(word)-wsize+1):
            subword = word[startingpoint:startingpoint+wsize]
            if(len(set(subword))==len(allchars)):
                print(wsize)
                end= True
                break