import math

def round_half_up(n, decimals=0):
    multiplier = 10 ** decimals
    return math.floor(n*multiplier + 0.5) / multiplier

def povrsinaTrokuta(A,B,C):
    a = math.sqrt(math.pow(A[0]-B[0],2)+ math.pow(A[1]-B[1],2))
    b = math.sqrt(math.pow(B[0]-C[0],2)+ math.pow(B[1]-C[1],2))
    c = math.sqrt(math.pow(A[0]-C[0],2)+ math.pow(A[1]-C[1],2))
    if(a+b<=c or a+c<=b or b+c<=a):
        return 0.0
    P = (c * math.sqrt(a*a - math.pow((a*a-b*b+c*c)/(2*c),2)) )/2
    return P

def vanjskicavli(cavli):
    vanjskicavliindex = []

    for i in range(0,len(cavli)-1):
        for j in range(i+1,len(cavli)):
            if(i in vanjskicavliindex and j in vanjskicavliindex):
                continue
            A,B,C = pravac(cavli[i],cavli[j])
            udaljenostodpravca = [A*cavli[x][0]+B*cavli[x][1]+C for x in range(0,len(cavli))]
            test1 = list(filter(lambda x: x < 0, udaljenostodpravca))
            test2 = list(filter(lambda x: x > 0, udaljenostodpravca))
            if(len(test1)==0 or len(test2)==0):
                if(i not in vanjskicavliindex):
                    vanjskicavliindex.append(i)
                if(j not in vanjskicavliindex):
                    vanjskicavliindex.append(j)
    vanjskicavli = []
    for index in vanjskicavliindex:
        vanjskicavli.append(cavli[index])
    return vanjskicavli

def cavliukrug(vanjskicavli):
    miny = min(vanjskicavli, key=lambda x: x[1])
    vanjskicavli.remove(miny)
    lijevo = list(filter(lambda x: x[0] <= miny[0], vanjskicavli))
    desno = list(filter(lambda x: x[0] > miny[0], vanjskicavli))

    lijevo.sort(key=lambda x: x[1],reverse=True)
    desno.sort(key=lambda x: x[1])

    lijevo.append(miny)
    cavliukrug = lijevo + desno
    return cavliukrug

def pravac(A,B):
    if((B[0]-A[0])==0):
        return 1,0,-A[0]
    return ((B[1]-A[1])/(B[0]-A[0])),-1,((B[1]-A[1])/(B[0]-A[0]))*(-A[0])+A[1]

brojcavli = int(input())
cavli = []
for i in range(0,brojcavli):
    cavao = [int(x) for x in input().split()]
    cavli.append(cavao)

micanje = input()

vcavli = vanjskicavli(cavli)

ucavli = cavliukrug(vcavli)


pov = 0
for i in range(1,len(ucavli)-1):
    pov += povrsinaTrokuta(ucavli[0],ucavli[i],ucavli[i+1])
print(round_half_up(pov,1))

for move in micanje:
    if(len(cavli)==3):
        break
    if (move =='L'):
        cavli.remove(min(cavli,key=lambda x:x[0]))
    if (move =='R'):
        cavli.remove(max(cavli,key=lambda x:x[0]))
    if (move =='U'):
        cavli.remove(max(cavli,key=lambda x:x[1]))
    if (move =='D'):
        cavli.remove(min(cavli,key=lambda x:x[1]))


    vcavli = vanjskicavli(cavli)

    ucavli = cavliukrug(vcavli)

    pov = 0
    for i in range(1,len(ucavli)-1):
        pov += povrsinaTrokuta(ucavli[0],ucavli[i],ucavli[i+1])
    print(round_half_up(pov,1))