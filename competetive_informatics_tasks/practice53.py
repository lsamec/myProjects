def constructReDiag(matrix,xstart,ystart):
    reDiag = []
    reDiag.append(matrix[ystart][xstart])
    x = xstart
    y = ystart
    while(True):
        x = x - 1
        y = y + 1
        if x < 0 or y >= N:
            break
        reDiag.append(matrix[y][x])
    return reDiag

N = int(input())
elements = str(input()).split(' ')
matrix = []
for x in range(0,N):
    matrix.append([])
for x in range(0,N):
    for y in range(0,N):
        matrix[x].append(elements[x*N+y])

diags = []
for x in range(0,N):
    diags.append(constructReDiag(matrix,x,0))

for y in range(1,N):
    diags.append(constructReDiag(matrix,N-1,y))

print(diags)

