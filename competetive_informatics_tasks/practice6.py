
def pentNumber(step):
    if step == 1:
        return 1
    if step == 2:
        return 6
    return 5 + 5 * (step-2) + pentNumber(step-1)

inp = input()
print(pentNumber(int(inp)))
