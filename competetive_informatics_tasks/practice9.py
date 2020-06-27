noOfKing = input()
kingdoms = {}
for kingd in range(0,int(noOfKing)):
    rawking = input()
    rawking = rawking.split()
    kingdoms[(int(rawking[0]),int(rawking[1]))] = True

bombCount = 0
for place,index in zip(kingdoms.keys(),range(0,len(kingdoms.keys()))):
    if(kingdoms[place] == True):
        kingdoms[place] = False
        bombCount+=1
        for nearby, nerbyIndex in zip(kingdoms.keys(),range(0,len(kingdoms.keys()))):
            if(nerbyIndex > index and place[1] >= nearby[0]):
                kingdoms[nearby] = False

print(bombCount)