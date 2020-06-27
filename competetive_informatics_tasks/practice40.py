testcases = int(input())
for testcase in range(0,testcases):
    text = input()
    subtexts = []
    for size in range(1,len(text)+1):
        for start in range(0,len(text)-size+1):
            subtext = text[start:(start+size)]
            subtexts.append(subtext)
    print(len(set(subtexts)))