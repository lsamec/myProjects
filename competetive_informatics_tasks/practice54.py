histogram = str(input()).split(' ')
N = len(histogram)

areas = []
for i in range(0,N):
    for j in range(i+1,N+1):
        histogramPart = histogram[i:j]
        minValue = min(map(lambda x: int(x), histogramPart))
        area = int(minValue)*(j-i)
        areas.append(area)

print(max(areas))
