
customers = {
    "Mark" : { "attrs" : ["young",    #age category
                          "student", #occupation
                          "gaming"   #hobby
                        ],
               "bought": ["pokemoncards",
                          "fantasybook",
                          "schoolbook",
                          "backpack",
                          "shirt",
                          "pencil",
                          "runningshoes",
                          "worldofwarcraft",
                          "crysis"
                        ],

              "onlylookedat": ["suit",
                               "car",
                               "expensivewatch",
                               "guildwars2",
                               "glasses",
                               "officeshoes",
                               "motorcycle"
                              ]
           },

    "Bob" : { "attrs" : [ "adult",   #age category
                          "worker",  #occupation
                          "golf"     #hobby
                        ],

              "bought": ["glasses",
                         "diehard",
                         "expensivewatch",
                         "officeshoes",
                         "bigshirt",
                         "runningshoesXL",
                         "golfclub"
                         ],

              "onlylookedat": ["car",
                               "motorcycle",
                               "guildwars2",
                               "worldofwarcraft"
                             ]
           },

    "Lisa" : { "attrs" : [ "adult",   #age category
                           "worker",  #occupation
                           "tvseries" #hobby
                         ],

               "bought": ["glasses",
                          "skirt"
                         ],

               "onlylookedat": ["car",
                                "motorcycle",
                                "guildwars2",
                                "worldofwarcraft"
                               ]
           },

    "Tim" : { "attrs" : [ "senior",   #age category
                           "retired",  #occupation
                           "gardening" #hobby
                         ],

               "bought": ["guildwars2"
                         ],

               "onlylookedat": ["glasses"
                               ]
           }

    }

items = {"pokemoncards": { "attrs": [ "foryoung",
                                      "forgaming"
                                    ]

                         },
         "fantasybook": { "attrs": [ "foryoung",
                                      "forreading"
                                    ]

                         },
         "schoolbook": { "attrs": [ "foryoung",
                                      "forstudent"
                                    ]

                         },
         "backpack": { "attrs": [ "forall",
                                      "forhiking"
                                    ]

                         },
         "shirt": { "attrs": [ "forall"
                                    ]

                         },
         "pencil": { "attrs": [ "forall",
                                      "forstudent"
                                    ]

                         },
         "runningshoes": { "attrs": [ "forall",
                                      "forsport"
                                    ]

                         },
         "worldofwarcraft": { "attrs": [ "foryoung",
                                      "forgaming"
                                    ]

                         },
         "crysis": { "attrs": [ "foryoung",
                                      "forgaming"
                                    ]

                         },
         "suit": { "attrs": [ "foradult",
                              "foroffice"
                                    ]

                         },
         "car": { "attrs": [ "foradult"
                                    ]

                         },
         "expensivewatch": { "attrs": [ "foradult"
                                    ]

                         },
         "guildwars2": { "attrs": [ "foryoung",
                                      "forgaming"
                                    ]

                         },
         "glasses": { "attrs": [ "forall"
                                    ]

                         },
         "officeshoes": { "attrs": [ "foradult",
                                      "foroffice"
                                    ]

                         },
         "motorcycle": { "attrs": [ "foradult"
                                    ]

                         },
         "diehard": { "attrs": [ "forall",
                                      "fortvseries"
                                    ]

                         },
         "runningshoes": { "attrs": [ "forall",
                                      "forsports"
                                    ]

                         },
         "golfclub": { "attrs": [ "foradult",
                                      "forsports"
                                    ]

                         },
         "bigshirt": { "attrs": [ "foradult"
                                    ]

                         },
         "runningshoesXL": { "attrs": [ "foradult"
                                    ]

                         }
         }

def similarityOfCusts(custName1,custName2):
    cust1 = customers[custName1]
    cust2 = customers[custName2]

    A = len(set(cust1["attrs"]).intersection(set(cust2["attrs"])))
    KUP = len(set(cust1["bought"]).intersection(set(cust2["bought"])))
    PREG = len((set(cust1["bought"]).union(set(cust1["onlylookedat"]))).intersection((set(cust2["bought"]).union(set(cust2["onlylookedat"])))))

    return 10.0*A + 5.0*KUP + 1.0*PREG

def similarityOfItems(itemName1, itemName2):
    item1 = items[itemName1]
    item2 = items[itemName2]

    A = len(set(item1["attrs"]).intersection(set(item2["attrs"])))

    return 10.0*A;

def hasBoughtBinary(custName,itemName):
    if(itemName in customers[custName]["bought"]):
        return 1
    else:
        return 0

def hasLookedAtBinary(custName,itemName):
    if(itemName in customers[custName]["onlylookedat"] or itemName in customers[custName]["bought"]):
        return 1
    else:
        return 0

def levelOrRecommendation(custName,itemName):
    sum = 0.0

    for aCustomer in customers.keys():
        if aCustomer != custName:
            sum += 5.0*similarityOfCusts(custName,aCustomer)*hasBoughtBinary(aCustomer,itemName)

    for aCustomer in customers.keys():
        if aCustomer != custName:
            sum += similarityOfCusts(custName,aCustomer)*hasLookedAtBinary(aCustomer,itemName)

    for aItem in items.keys():
        if aItem != itemName:
            sum += 5.0 * similarityOfItems(aItem,itemName)*hasBoughtBinary(custName,aItem)

    for aItem in items.keys():
        if aItem != itemName:
            sum += similarityOfItems(aItem,itemName)*hasLookedAtBinary(custName,aItem)

    return sum

def recommend(custName, numberOfItems):
    itemLevelDict = {}
    for item in items.keys():
        itemLevelDict[item] = levelOrRecommendation(custName,item)

    itemLevelDictSorted = sorted(itemLevelDict.items(), key=lambda kv: -kv[1])

    print(itemLevelDictSorted[0:numberOfItems])

recommend("Tim",1000)