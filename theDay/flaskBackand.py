from flask import Flask
from flask import json
from flask import request
import pymongo


app = Flask(__name__)

client = pymongo.MongoClient("mongodb://localhost:27017/")
db = client["db"]
col = db["usersAndColors"]

col.delete_many({})
col.insert_one({'Tom':'Blue'})

def transformToReadable(inputList):
    finalDict = {}
    for entry in inputList:
        finalDict[list(entry.keys())[0]] = entry[list(entry.keys())[0]]
    return finalDict

@app.route('/getUsers', methods=['GET'])
def getUsers():
    usersAndColors = transformToReadable(list(col.find({},{ "_id": 0})))
    print(usersAndColors)
    response = app.response_class(
        response=json.dumps(usersAndColors),
        status=200,
        mimetype='application/json'
    )
    return response

@app.route('/addUser', methods=['POST'])
def addUser():
    newUser = request.json['user']
    color = request.json['color']
    newItem = {}
    newItem[newUser] = color
    col.insert_one(newItem)
    response = app.response_class(
        status=200,
        mimetype='application/json'
    )
    return response

@app.route('/changeUserColor', methods=['PUT'])
def changeUserColor():
    user = request.args.get('user')
    color = request.args.get('color')

    query = {}
    regex = {'$regex' : '^[a-zA-Z]'}
    query[user] = regex

    change = {}
    change[user] = color
    newValue = {}
    newValue['$set'] = change

    col.update_many(query, newValue)
    response = app.response_class(
        status=200,
        mimetype='application/json'
    )
    return response


@app.route('/removeUser', methods=['DELETE'])
def removeUser():
    userForDelete = request.args.get('user')
    query = {}
    regex = {'$regex' : '^[a-zA-Z]'}
    query[userForDelete] = regex

    col.delete_many(query)
    response = app.response_class(
        status=200,
        mimetype='application/json'
    )
    return response

if __name__ == '__main__':
    app.run()
