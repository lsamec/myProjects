while(True):
    noofcol = int(input())
    if(noofcol == 0):
        exit()
    text = input()
    chunkifiedtext = [text[0+i:noofcol+i] for i in range(0, len(text), noofcol)]
    for i in range(0,len(chunkifiedtext)):
        if (i%2==1):
            listed = list(chunkifiedtext[i])
            listed.reverse()
            chunkifiedtext[i] = "".join(listed)
    finalstring = ""
    for j in range(0,noofcol):
        for i in range(0,len(chunkifiedtext)):
            finalstring = finalstring + chunkifiedtext[i][j]
    print(finalstring)