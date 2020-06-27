operations = ["+","-","*","/"]
brackets = ["(",")"]
operationsandbrackets = ["+","-","*","/","(",")"]

testcases = int(input())
for testcase in range(0,testcases):
    expr = input()
    nextIter = True

    while(nextIter):
        nextIter = False
        exprlist = list(expr)
        bracklist = {}
        for charindex in range(0,len(exprlist)):
            if exprlist[charindex] == "(":
                bracklist[charindex] = None

        for startbrack in bracklist.keys():
            brackcount = 0
            for charindex in range(startbrack+1,len(exprlist)):
                if(brackcount == 0 and exprlist[charindex]==")"):
                    bracklist[startbrack] = charindex
                    break
                if(exprlist[charindex]=="("):
                    brackcount+=1
                if(exprlist[charindex]==")"):
                    brackcount-=1

        brackdict = {}
        keys = list(bracklist.keys())
        for i in range(0,len(bracklist.keys())):
            brackdict[i]= [ [ keys[i],bracklist[keys[i]] ] ]

        for key in brackdict.keys():
            startbrack = brackdict[key][0][0]
            endbrack = brackdict[key][0][1]
            if(startbrack-1<0):
                brackdict[key].append(0)
            else:
                if (exprlist[startbrack-1] not in operationsandbrackets):
                    brackdict[key].append(0)
                else:
                    brackdict[key].append(exprlist[startbrack-1])
            if(endbrack+1>=len(exprlist)):
                brackdict[key].append(0)
            else:
                if (exprlist[endbrack+1] not in operationsandbrackets):
                    brackdict[key].append(0)
                else:
                    brackdict[key].append(exprlist[endbrack+1])

        for key in brackdict.keys():
            brackdict[key].append([])
            startbrack = brackdict[key][0][0]
            endbrack = brackdict[key][0][1]
            brackcount = 0
            for charindex in range(startbrack+1,endbrack):
                if(brackcount == 0 and exprlist[charindex] in operations):
                    brackdict[key][3].append(exprlist[charindex])
                if(exprlist[charindex]=="("):
                    brackcount+=1
                if(exprlist[charindex]==")"):
                    brackcount-=1

        for key in brackdict.keys():
            if(brackdict[key][1]=="(" and brackdict[key][2]==")"):
                nextIter=True
                continue
            if (len(brackdict[key][3])==0):
                if(len(brackdict[key])==4):
                    brackdict[key].append(True)
            else:
                if (brackdict[key][1] in ["+",0,"("] and brackdict[key][2] in ["+",0,"-",")"]):
                    if(len(brackdict[key])==4):
                        brackdict[key].append(True)
                else:
                    if (brackdict[key][1] in ["*",0,"("] and brackdict[key][2] in ["*",0,"/",")"] and len(set(brackdict[key][3]).difference(set(["*","/"])))==0):
                        if(len(brackdict[key])==4):
                            brackdict[key].append(True)
                    else:
                        if (brackdict[key][1] in ["-"] and len(set(brackdict[key][3]).difference(set(["*","/"])))==0):
                            if(len(brackdict[key])==4):
                                brackdict[key].append(True)
        #print(expr)
        #print(brackdict)

        indexesfordelete = []
        for key in brackdict.keys():
            if (len(brackdict[key])==5):
                indexesfordelete.extend(brackdict[key][0])

        indexesfordelete.sort()

        minuscounter = 0
        for index in indexesfordelete:
            exprlist.pop(index-minuscounter)
            minuscounter+=1

        expr = "".join(exprlist)

    print(expr)