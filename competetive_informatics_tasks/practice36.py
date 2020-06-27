import copy

nodenum = int(input())
graph = {}
for i in range(1,nodenum+1):
    graph[i] = []
while(True):
    try:
        a,b = [int(x) for x in input().split()]
        graph[a].append(b)
        graph[b].append(a)
    except ValueError:
        break

graphCopy = copy.deepcopy(graph)

lights = []

while(len(graph.keys())>0):
    light = None
    for key in graph.keys():
        if(len(graph[key])==1):
            light = graph[key][0]
            lights.append(light)
            break

    for key in graph.keys():
        try:
            graph[key].remove(light)
        except ValueError:
            pass

    del graph[light]

    dellist = []
    for key in graph.keys():
        if(len(graph[key])==0):
            dellist.append(key)

    for key in dellist:
        del graph[key]

print(len(lights))
