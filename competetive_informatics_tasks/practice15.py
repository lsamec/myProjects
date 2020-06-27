import copy

def inttobool(a):
    if (a==0):
        return False
    if (a==1):
        return True

def andext(a,b):
    if(a == 'x' and b == False):
        return False
    if(b == 'x' and a == False):
        return False
    if(a == 'x' and b == True):
        return 'x'
    if(b == 'x' and a == True):
        return 'x'
    if(b == 'x' and a == 'x'):
        return 'x'
    return (a and b)

def orext(a,b):
    if(a == 'x' and b == False):
        return 'x'
    if(b == 'x' and a == False):
        return 'x'
    if(a == 'x' and b == True):
        return True
    if(b == 'x' and a == True):
        return True
    if(b == 'x' and a == 'x'):
        return 'x'
    return (a or b)

def xorext(a,b):
    if(a == 'x' or b == 'x'):
        return 'x'
    return (a ^ b)

def notext(a):
    if(a == 'x'):
        return 'x'
    return (not a)

noOfCycles = 0
registers ={}
for regIndex in range(0,32):
    registers[regIndex] = 'x'

instructionList = []
instruction = input()
while(instruction != 'STOP'):
    instructionList.append(instruction)
    instruction = input()

instructionpointer = 0
cycles = 0
statestack = []
lastjumps = []
popstack = False
while(instructionpointer < len(instructionList)):
    if(popstack and len(statestack)!= 0):
        pair = statestack.pop()
        instructionpointer = pair[0]
        cycles = pair[1]
        lastjumps = pair[2]
        registers = pair[3]
        if(instructionpointer==len(instructionList)):
            break
    if(popstack and len(statestack)==0):
        print("HANGS")
        exit()
    print(instructionpointer,registers,lastjumps)
    popstack = False
    instruction = instructionList[instructionpointer].split()
    cycles += 1
    if(instruction[0]=="AND"):
        registers[int(instruction[1])] = andext(registers[int(instruction[1])],registers[int(instruction[2])])
    if(instruction[0]=="OR"):
        registers[int(instruction[1])] = orext(registers[int(instruction[1])],registers[int(instruction[2])])
    if(instruction[0]=="XOR"):
        registers[int(instruction[1])] = xorext(registers[int(instruction[1])],registers[int(instruction[2])])
    if(instruction[0]=="XOR"):
        registers[int(instruction[1])] = notext(registers[int(instruction[1])])
    if(instruction[0]=="MOV"):
        registers[int(instruction[1])] = registers[int(instruction[2])]
    if(instruction[0]=="SET"):
        registers[int(instruction[1])] = inttobool(int(instruction[2]))
    if(instruction[0]=="RANDOM"):
        registers[int(instruction[1])] = 'x'
    if(instruction[0]=="JMP"):
        if(instructionpointer in lastjumps):
            popstack = True
            continue
        lastjumps.append(instructionpointer)
        instructionpointer = int(instruction[1])
        continue
    if(instruction[0]=="JZ"):
        if(registers[int(instruction[2])]==False):
            if(instructionpointer in lastjumps):
                popstack = True
                continue
            lastjumps.append(instructionpointer)
            instructionpointer = int(instruction[1])
            continue
        else:
            if(registers[int(instruction[2])]=='x'):
                if(instructionpointer in lastjumps):
                    popstack = True
                    continue
                lastjumps.append(instructionpointer)
                lastjumps1 = copy.deepcopy(lastjumps)
                lastjumps2 = copy.deepcopy(lastjumps)
                statestack.append([instructionpointer+1,cycles,lastjumps1,registers])
                statestack.append([int(instruction[1]),cycles,lastjumps2,registers])
                popstack = True
                continue
    instructionpointer+=1

cycles+=1
print(cycles)

