
n,k = [int(x) for x in input().split()]

jobs = {}
for x in range(0,n):
    for y in range(0,n):
        jobs[(x,y)] = False

availableJobs = []
availableJobs.append((0,0))
cycles = 0
while(len(availableJobs)>0):
    cycles+=1
    finishedJobs = []
    for comp in range(0,k):
        if(len(availableJobs)>0):
             job = availableJobs.pop()
             finishedJobs.append(job)
        else:
            break
    for job in finishedJobs:
        jobs[job] = True
    for job in finishedJobs:
        potentiallyOpenJobs = [(job[0]+1,job[1]),(job[0],job[1]+1)]
        forRemove = []
        for poj in potentiallyOpenJobs:
            if (poj[0]>n-1 or poj[1]>n-1 or poj in availableJobs):
                forRemove.append(poj)
        for rem in forRemove:
            potentiallyOpenJobs.remove(rem)
        for poj in potentiallyOpenJobs:
            if(((poj[0]-1,poj[1]) not in jobs.keys() or jobs[(poj[0]-1,poj[1])] == True) and ((poj[0],poj[1]-1) not in jobs.keys() or jobs[(poj[0],poj[1]-1)] == True)):
                if(poj[0] == n-1 or poj[1] == n-1):
                    availableJobs.insert(0,poj)
                else:
                    availableJobs.append(poj)

print(cycles)