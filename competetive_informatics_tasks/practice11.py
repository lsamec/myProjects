raw = input()
raw = raw.split()
integers = [int(x) for x in raw]

noOfProgressions = 0
for i in range(0,len(integers)-2):
    for j in range(i+1,len(integers)-1):
        for k in range(j+1,len(integers)):
            if(integers[j]-integers[i]==integers[k]-integers[j]):
                noOfProgressions+=1
                print(integers[i],integers[j],integers[k])