class State:
    def __init__(self,aFill,bFill,steps):
        self.aFill = aFill
        self.bFill = bFill
        self.steps = steps

    def __eq__(self, other):
        """Overrides the default implementation"""
        if isinstance(other, State):
            return (self.aFill == other.aFill) and (self.bFill == other.bFill)
        return False
    def __str__(self):
        return self.aFill,self.bFill,self.steps

testcases = int(input())
for testcase in range(0,testcases):
    a = int(input())
    b = int(input())
    c = int(input())

    if(c<0 and c> max(a,b)):
        print(-1)
        exit()

    stack = []
    stack.append(State(0,0,0))
    wasonstack = []
    wasonstack.append(State(0,0,0))

    while(len(stack)>0):
        current = stack.pop(0)
        if(current.aFill == c or current.bFill == c):
            print(current.steps)
            exit()

        if(current.aFill < a):
            new = State(a,current.bFill,current.steps+1)
            if(new not in wasonstack):
                wasonstack.append(new)
                stack.append(new)

        if(current.bFill < b):
            new = State(current.aFill,b,current.steps+1)
            if(new not in wasonstack):
                wasonstack.append(new)
                stack.append(new)

        if(current.aFill > 0):
            new = State(0,current.bFill,current.steps+1)
            if(new not in wasonstack):
                wasonstack.append(new)
                stack.append(new)

        if(current.bFill > 0):
            new = State(current.aFill,0,current.steps+1)
            if(new not in wasonstack):
                wasonstack.append(new)
                stack.append(new)

        if(current.aFill > 0 and current.bFill < b):
            newaFill = 0
            newbFill = current.bFill+current.aFill
            if(newbFill>b):
                newaFill = newbFill - b
                newbFill = b
            new = State(newaFill,newbFill,current.steps+1)
            if(new not in wasonstack):
                wasonstack.append(new)
                stack.append(new)

        if(current.bFill > 0 and current.aFill < a):
            newbFill = 0
            newaFill = current.aFill+current.bFill
            if(newaFill>a):
                newbFill = newaFill - a
                newaFill = a
            new = State(newaFill,newbFill,current.steps+1)
            if(new not in wasonstack):
                wasonstack.append(new)
                stack.append(new)

    print(-1)

