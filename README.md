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


