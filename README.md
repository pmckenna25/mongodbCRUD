# mongodbCRUD

## Concepting Mongodb for personal project

  - MongoDB is a document based NoSQL database. 
  - Currently evaluating its potential for a future applications
  - Dependent on a MongoDB connection
  - ~~Connection info, database name and collections have all been hardcoded~~
    - 07/04/2019 update: added resources folder with system.properties file, removed the hardcoding for portability


### List all registered users

**Definition**

`GET /person/list`

**Response**

-`200 OK` on success

```json
[
    {
        "email": "pmckenna25@gmail.co.uk",
        "forename": "Jimmy",
        "password": "6aeDFAgh894",
        "surname": "John"
    }
]
```
### Find one registered user

**Definition**

`GET /person/<email>` e.g. <email> = pmckenna25@gmail.co.uk

**Response**

-`200 OK` on success

```json
{
    "email": "pmckenna25@gmail.co.uk",
    "forename": "Jimmy",
    "password": "6aeDFAgh894",
    "surname": "John"
}
```
### Register new user

**Definition**

`POST /person`

**Arguments**

- `"email":string` the unique identifier for each account
- `"forename":string` the users first name
- `"password":string` the users password hash
- `"surname":string` the users second name

**Response**

`201 Created` on success

```json
{
  "Response": "<user email> added"
}
```
### Update user details

**Definition**

`PUT /person`

**Arguments**

- `"email":string` Must be an existing email address
- `"forename":string` the users first name
- `"password":string` the users password hash
- `"surname":string` the users second name

**Response**

`200 OK` on success

```json
{
  "Response": "<user email> updated"
}
```

### Delete user

**Definition**

`DELETE /person`

**Arguments**

- `"email":string` Must be an existing email address
- `"forename":string` the users first name
- `"password":string` the users password hash
- `"surname":string` the users second name

**Response**

`200 OK` on success

```json
{
  "Response": "<user email> deleted"
}
```

### List all created characters

**Definition**

`GET /character/list`

**Response**

-`200 OK` on success

```json
[
    {
        "attributes": {
            "constitution": 20,
            "dexterity": 13,
            "strength": 18
        },
        "characterClass": "Warrior",
        "characterId": 102,
        "characterName": "Targon Steelwind",
        "level": 5
    }
]
```
### Find one character by id

**Definition**

`GET /character/<characterId>` e.g. <characterId> = 102

**Response**

-`200 OK` on success

```json
{
    "attributes": {
        "constitution": 20,
        "dexterity": 13,
        "strength": 18
    },
    "characterClass": "Warrior",
    "characterId": 102,
    "characterName": "Targon Steelwind",
    "level": 5
}
```
### Create new character

**Definition**

`POST /character`

**Arguments**

- `"attributes":"constitution":int` statistic which effects the characters' health
- `"attributes":"dexterity":int` statistic which effects the characters' speed and dodge
- `"attributes":"strength":int` statistic which effects the characters' damage output
- `"characterClass":string` the name of the type of character
- `"characterId":int` unique character identifier
- `"characterName":string` character name
- `"level":int` the amount of experience gathered by the character

**Response**

`201 Created` on success

```json
{
  "Response": "<characterId> added"
}
```
### Update character

**Definition**

`PUT /character`

**Arguments**

- `"attributes":"constitution":int` statistic which effects the characters' health
- `"attributes":"dexterity":int` statistic which effects the characters' speed and dodge
- `"attributes":"strength":int` statistic which effects the characters' damage output
- `"characterClass":string` the name of the type of character
- `"characterId":int` Must be an existing characterId
- `"characterName":string` character name
- `"level":int` the amount of experience gathered by the character

**Response**

`200 OK` on success

```json
{
  "Response": "<characterId> updated"
}
```

### Delete character

**Definition**

`DELETE /character`

**Arguments**

- `"attributes":"constitution":int` statistic which effects the characters' health
- `"attributes":"dexterity":int` statistic which effects the characters' speed and dodge
- `"attributes":"strength":int` statistic which effects the characters' damage output
- `"characterClass":string` the name of the type of character
- `"characterId":int` Must be an existing characterId
- `"characterName":string` character name
- `"level":int` the amount of experience gathered by the character

**Response**

`200 OK` on success

```json
{
  "Response": "<characterId> deleted"
}
```
