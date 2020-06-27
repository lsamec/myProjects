import copy

def convertToBase10(number,base):
    conv = 0
    mulitpl = 1
    for digitIndex in range(0,len(number)):
        conv+= number[len(number)-1-digitIndex]*mulitpl
        mulitpl = mulitpl*base
    return conv

def check(number,base):
    numberSwitch = copy.deepcopy(number)
    numberSwitch = numberSwitch[0:(len(numberSwitch)-1)]
    numberSwitch.insert(0,number[len(number)-1])
    if(2*convertToBase10(number,base) == convertToBase10(numberSwitch,base)):
        return number,True
    number.insert(0,0)
    numberSwitch = copy.deepcopy(number)
    numberSwitch = numberSwitch[0:(len(numberSwitch)-1)]
    numberSwitch.insert(0,number[len(number)-1])
    if(2*convertToBase10(number,base) == convertToBase10(numberSwitch,base)):
        return number,True
    return None,False

testcases = int(input())
for testcase in range(0,testcases):
    base = int(input())
    number = [1]
    digit = 0
    while(True):
        number[len(number)-1] = digit
        digit += 1
        if(len(number) ==1 and number[0]==0):
            continue
        tested, checkbool = check(number,base)
        if(checkbool):
            print(" ".join([str(x) for x in tested]))
            break
        if (digit == base):
            digit = 0
            for digitIndex in range(len(number)-1,-1,-1):
                if(number[digitIndex]==base-1):
                    number[digitIndex] = 0
                    if(digitIndex==0):
                        number.insert(0,1)
                else:
                    number[digitIndex]= number[digitIndex]+1
                    break
