import copy
d = {"T":["K","T"], "D":["G","D"],"L":["L","R"],"F":["F","R"]}
for t in range(0,10):
    m = [list(input())]
    i = 0
    while(i<len(m[0])):
        ad = []
        for a in m:
            if a[i] in d.keys():
                a[i] = d[a[i]][0]
                ad.append(a)
                a1 = copy.deepcopy(a)
                a1[i] = d[a[i]][1]
                ad.append(a1)
        m.extend(ad)
        i+=1
    print(len(set(list(map(lambda x: "".join(x),m)))))
