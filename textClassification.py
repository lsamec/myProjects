import collections

texts = { "twoletter" : [ "aa a bb a aa b aa",
                          "bb b a bb a aaa bb a",
                          "aa bb a b aa bb",
                          "bb bb a aa bbbb bb cc"
                        ],

          "threeletter" : [ "aaa bbb a b aaa aa bbb",
                             "aaa bbb a b aaa cccc bbb",
                             "a b aaa b a aaaa a b a",
                             "aaa ccc a aaa aaa"
                          ],

          "fourletter" : [ "aaaa bbbb a b aaaa bbbb",
                           "bbbb a b aaaa bbbb aaa cccc",
                           "cccc bbbb aaaa a a a a",
                           "aaaa bbbb a aaaa b aa"
                          ],

           "fiveletter" : [ "aaaaa bbbbb a b aaaaa bbbbb",
                            "bbbbb a b aaaaa a a b aa"
                            "bbbbb aaaaa bbbbb a aaa b"
                            "bbbbb aa aaaaa aaaaa aaaaa bbbbb cc"
                          ]
        }

def normalize(aDict):
    sumOfValues = 0.0
    for key in aDict.keys():
        value = aDict[key]
        sumOfValues += value

    for key in aDict.keys():
        aDict[key] = aDict[key] / sumOfValues

    return aDict

def mapTheRarity(aDict):
    rarityMap = {}
    multiplier = len(aDict)
    aDictInTupleSorted = sorted(aDict.items(), key=lambda kv: kv[1])
    oldTuple = None
    accumulation = 0
    for tuple in aDictInTupleSorted:
        if oldTuple is not None:
            if oldTuple[1] == tuple[1]:
                multiplier += 1
                accumulation += 1
            else:
                multiplier -= accumulation
                accumulation = 0
        rarityMap[tuple[0]] = multiplier
        oldTuple = tuple
        multiplier -= 1
        if multiplier <= 0:
            multiplier = 1

    return rarityMap

def scaleByRarity(aDict,rarityMap):
    newDict = {}
    for tuple in aDict.items():
        newDict[tuple[0]] = tuple[1]*rarityMap[tuple[0]]*3

    return newDict

def negativeSimilarity(aDict1,aDict2):
    keys = list(set(aDict1.keys()).union(set(aDict2.keys())) )
    negSim = 0.0
    for key in keys:
        dict1Value = 0.0
        dict2Value = 0.0
        if key in aDict1.keys():
            dict1Value = aDict1[key]
        if key in aDict2.keys():
            dict2Value = aDict2[key]
        negSim += abs(dict1Value - dict2Value)
    return negSim

#merge all the counts together
joinedCounter = collections.Counter()
for topic in texts.keys():
    for text in texts[topic]:
        joinedCounter += collections.Counter(text.split(" "))

rarityMap = mapTheRarity(dict(joinedCounter))

topicCounter = {}
for topic in texts.keys():
    topicCounter[topic] = collections.Counter()
    for text in texts[topic]:
        topicCounter[topic] += collections.Counter(text.split(" "))

topicScaledNormalized = {}
for topic in topicCounter.keys():
    topicScaledNormalized[topic] = normalize(scaleByRarity(dict(topicCounter[topic]),rarityMap))

testText = "aa bb a aa a a a a a aaa aaaa"

topicNegSim = {}

for topic in topicScaledNormalized.keys():
    topicNegSim[topic] = negativeSimilarity(topicScaledNormalized[topic], normalize(scaleByRarity(dict(collections.Counter(testText.split(" "))),rarityMap) ))

topicNegSimSorted = sorted(topicNegSim.items(), key=lambda kv: kv[1])

print(topicNegSimSorted[0])
