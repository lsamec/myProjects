import copy

def checkvalid(obs, leftspaces):
    leftspaces.sort(reverse=True)
    obs.sort(reverse=True)
    obsindex = 0
    for leftspaceindex in range(0,len(leftspaces)):
        if(obsindex>=len(obs)):
            return True
        if leftspaces[leftspaceindex] > obs[obsindex]:
            obsindex += 1
            continue
        if leftspaces[leftspaceindex] < obs[obsindex]:
            return False
    return True

testcases = int(input())
for testcase in range(0,testcases):
    valid = 0
    n, k = [int(x) for x in input().split()]
    obs = set([int(x) for x in input().split()])
    leftspaces = set(list(range(1,2*n+1)))
    leftspaces = list(leftspaces.difference(obs))
    obs = list(obs)
    leftopenings = n - k
    stack = []
    stack.append((obs,leftspaces,leftopenings))
    wasonstack = [set(obs)]
    while(len(stack)>0):
        current = stack.pop()
        if(current[2]==0):
            isvalid = checkvalid(current[0],current[1])
            if(isvalid):
                valid+=1
            continue
        for leftspace in current[1]:
            newleftspaces = copy.deepcopy(current[1])
            newobs = copy.deepcopy(current[0])
            newobs.append(leftspace)
            if(set(newobs) in wasonstack):
                continue
            newleftspaces.remove(leftspace)
            isvalid = checkvalid(newobs,newleftspaces)
            if(isvalid):
                stack.append((newobs,newleftspaces,current[2]-1))
                wasonstack.append(set(newobs))
    print(valid)



