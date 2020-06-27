digits = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
digits = list(map(lambda x: str(x), digits))

x = int(input())

converted = []
while(x > 0):
    modulo = x % 62
    converted.append(digits[modulo])
    x = int(x / 62)

print("".join(reversed(converted)))