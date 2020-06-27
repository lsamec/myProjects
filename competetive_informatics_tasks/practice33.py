word = input()

mapping = {0:'H',1:'O',2:'N',3:'I'}
state = 0
numofHonies = 0
for letter in word:
    if letter == mapping[state]:
        if state == 3:
            numofHonies+=1
        state = (state+1)%4
print(numofHonies)