# geoloc

This repo contains the source code for running the Yahoo Champaign iOS class' Elide web service backend.

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

## Using the API

***NOTE: Do not use real passwords with this service. It is not secure, and is only to be used for teaching purposes.***

### Create User 1:

Send an HTTP post to `/user` and set `name`, `email`, and `password`:

```
POST /user HTTP/1.1
Host: localhost:8080
Content-Type: application/vnd.api+json
Accept: application/vnd.api+json
Cache-Control: no-cache
Postman-Token: 49dcade2-9e91-49da-685e-c996b3580adb

{
    "data": {
        "type": "user",
        "attributes": {
            "name": "Shad Sharma",
            "email": "shadanan@gmail.com",
            "password": "myPassword"
        }
    }
}
```

### Update User 1's Location

Send an HTTP post to `/user/1/locations` with the attributes `latitude` and `longitude`. You need to provide your own user ID in the header as `X-User-Id` and the password as `X-User-Password`.

```
POST /user/1/locations HTTP/1.1
Host: localhost:8080
Content-Type: application/vnd.api+json
Accept: application/vnd.api+json
X-User-Id: 1
X-User-Password: myPassword
Cache-Control: no-cache
Postman-Token: fe119778-191b-4aa7-b5c1-1449e6bf4550

{
    "data": {
        "type": "location",
        "attributes": {
            "latitude": 1.23,
            "longitude": 3.14
        }
    }
}
```

### Change User 1's Info

```
PATCH /user/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/vnd.api+json
Accept: application/vnd.api+json
X-User-Id: 1
X-User-Password: myPassword
Cache-Control: no-cache
Postman-Token: eb7b7a72-1c9d-cc51-b173-c46c40691c5a

{
    "data": {
        "id": "1",
        "type": "user",
        "attributes": {
            "name": "Shadanan Sharma",
            "email": "shadanan@gmail.com"
        }
    }
}
```

### Create User 2 and 3:

```
POST /user HTTP/1.1
Host: localhost:8080
Content-Type: application/vnd.api+json
Accept: application/vnd.api+json
Cache-Control: no-cache
Postman-Token: 0cbfbc39-8d8b-f952-89c9-29230a5c4078

{
    "data": {
        "type": "user",
        "attributes": {
            "name": "Dennis McWherter",
            "email": "dmcwherter@yahoo-inc.com",
            "password": "lolCatz"
        }
    }
}
```

```
POST /user HTTP/1.1
Host: localhost:8080
Content-Type: application/vnd.api+json
Accept: application/vnd.api+json
Cache-Control: no-cache
Postman-Token: 8d0434bc-c7b8-34f5-13fe-ae98f5e9b549

{
    "data": {
        "type": "user",
        "attributes": {
            "name": "Xiaoyao Qian",
            "email": "xiaoyao@yahoo-inc.com",
            "password": "china"
        }
    }
}
```

### Update User 1 to Follow User 2:

```
POST /user/1/relationships/friends HTTP/1.1
Host: localhost:8080
Content-Type: application/vnd.api+json
Accept: application/vnd.api+json
X-User-Id: 1
X-User-Password: myPassword
Cache-Control: no-cache
Postman-Token: 67ad7c78-af91-c344-3f9f-10d75bc3b42b

{
    "data": [
        {
            "id": "2",
            "type": "user"
        }
    ]
}
```

### Look at All Users and Relationships:

```
GET /user HTTP/1.1
Host: localhost:8080
X-User-Id: 1
X-User-Password: myPassword
Cache-Control: no-cache
Postman-Token: e2159e97-1523-0e2a-2438-0ed0597c2b77
```

### Look at User 1's Friends' Current Location:

```
GET /user/1/friends?fields[user]=email,name,location HTTP/1.1
Host: localhost:8080
X-User-Id: 1
X-User-Password: myPassword
Cache-Control: no-cache
Postman-Token: f6ddf9fd-860c-ee57-ae33-6a6f6c61347e
```
