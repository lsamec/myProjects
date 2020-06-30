import collections
import math

texts = {0 :  "a",
         1 :  "a b c c d a f g h",
         2 :  "a a",
         3 :  "b",
         4 :  "a a b b",
         5 :  "a b c",
         6 :  "h g e a",
         7 :  "i j k l m n o p r"
        }

textsCounted = {}

for id in texts.keys():
    textsCounted[id] = dict(collections.Counter(texts[id].split(" ")))

#inverted
itextsCounted = {}
for id in texts.keys():
    for word in textsCounted[id].keys():
        if word in itextsCounted.keys():
            itextsCounted[word] += 1
        else:
            itextsCounted[word] = 1

inquiry = "a b"

#program start
inquiryCounted = dict(collections.Counter(inquiry.split(" ")))

#similarity between text and inquiry
textsSimilarities = {}

for id in texts.keys():
    text = texts[id]
    similarity = 0.0
    for word in inquiryCounted.keys():
        if word in textsCounted[id].keys():
            similarity += (math.log2(textsCounted[id][word]) + 1)/(math.log2(itextsCounted[word]) + 1)

    textsSimilarities[texts[id]] = similarity

textsSimilaritiesSorted = sorted(textsSimilarities.items(), key=lambda kv: -kv[1])

print(textsSimilaritiesSorted)