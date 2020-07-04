from random import randint

def generatenumbers():

    numbers = {}
    for x in range(0,4):
        for y in range(0,4):
            #17 different numbers, 4 x 4 board
            numbers[(x,y)] = randint(1, 17)

    return numbers

def checkwin(numbers):

    for x in range(0,4):
        row = True
        for y in range(0,4):
            if numbers[(x,y)] != numbers[(x,0)]:
                row = False
        if row == True:
            return True

    return False

sum=0.0

#number of playings
for iter in range(0,100000):

    #round price
    sum += 1.0

    win = checkwin(generatenumbers())

    #player wins
    if win:
        sum -= 1000

#your earnings as a machine owner
print(sum)