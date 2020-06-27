
string = input()
string = string.split()
sizeX = len(string[0])
sizeY = len(string)

binaryDict = {}
for part, indexY in zip(string, range(0,sizeY)):
    for letter, indexX in zip(part, range(0, sizeX)):
        binaryDict[(indexX, indexY)] = int(letter)

maximumSquareSize = 1
for indexX in range(0,sizeX):
    for indexY in range(0,sizeY):
        squareSize = 1
        if binaryDict[(indexX,indexY)] == 1:
            while(True):
                contBool = True
                if (indexX+squareSize>sizeX-1 or indexY+squareSize>sizeY-1):
                    break
                for followingY in range(indexY, indexY+squareSize+1):
                    if (binaryDict[(indexX+squareSize,followingY)] == 0):
                        contBool = False
                if(not contBool):
                    break
                for followingX in range(indexX, indexX+squareSize+1):
                    if (binaryDict[(followingX, indexY+squareSize)] == 0):
                        contBool = False
                if(not contBool):
                    break
                squareSize+=1
            if squareSize > maximumSquareSize:
                maximumSquareSize = squareSize

print(maximumSquareSize*maximumSquareSize)


