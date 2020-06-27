testcases = int(input())
for testcase in range(0,testcases):
    n = int(input())
    cards = [None]*n
    movingindex = 0
    putnumber = 1
    while(True):
        counter = 0
        if(putnumber == n):
            cards[cards.index(None)] = putnumber
        else:
            while(True):
                if (cards[movingindex] is None):
                    counter += 1
                else:
                    if(cards[movingindex] > putnumber):
                        counter += 1
                movingindex = (movingindex + 1) % n
                if(counter == putnumber):
                    break
            while(True):
                if(cards[movingindex] is not None):
                    if(cards[movingindex] > putnumber):
                        print(-1)
                        exit()
                else:
                    cards[movingindex] = putnumber
                    break
                movingindex = (movingindex + 1) % n
        putnumber+=1
        if(putnumber>n):
            break
    print(" ".join(map(lambda x: str(x),cards)))


