[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2fe90b89d1f642909e83e3bbf258e615)](https://www.codacy.com/manual/leonaugust/graduation?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=leonaugust/graduation&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/leonaugust/graduation.svg?branch=master)](https://travis-ci.org/leonaugust/graduation)


Restaurant voting system
===============================

**Description**

    A voting system for deciding where to have lunch.
    Every day, admins of the restaurants update their menu.
    From the start of the day to the end of voting,
    users can choose which restaurant they like the most.

------------------------------

**Implementation Stack:**

• Spring MVC

• Spring Data JPA

• Spring Security

• Hibernate

• HSQLDB Database

------------------------------

**cURL commands:**

#### get All Users
`curl -s http://localhost:8080/graduation/rest/admin/users --user admin:password`

#### get User 100000
`curl -s http://localhost:8080/graduation/rest/admin/users/100000 --user admin:password`

#### create User
`curl -s -X POST -d '{"name":"New User","login":"test-login","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/admin/users --user admin:password`

#### update User 100004
`curl -s -X PUT -d '{"name":"Updated User","login":"updated-login","password":"updated-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/admin/users/100004 --user admin:password`

#### delete User 100004
`curl -s -X DELETE http://localhost:8080/graduation/rest/admin/users/100004 --user admin:password`

#### register User
`curl -s -i -X POST -d '{"name":"New User","login":"some-login","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/profile/register`

#### get all Dishes
`curl -s http://localhost:8080/graduation/rest/dishes/all?date=2020-08-14  --user user:password`

#### get Dish 100008
`curl -s http://localhost:8080/graduation/rest/dishes/100008  --user user:password`

#### create Dish
`curl -s -X POST -d '{"date":"2020-08-21","name":"Created lunch","price":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/dishes?restaurantId=100005 --user admin:password`

#### update Dish 100008
`curl -s -X PUT -d '{"date":"2020-08-11","name":"Updated lunch","price":666}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/dishes/100008?restaurantId=100005 --user admin:password`

#### delete Dish 100008
`curl -s -X DELETE http://localhost:8080/graduation/rest/dishes/100008 --user admin:password`

#### get Restaurant 100007
`curl -s http://localhost:8080/graduation/rest/restaurants/100007 --user user:password`

#### create Restaurant 
`curl -s -X POST -d '{"name":"Puzzles"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/restaurants --user admin:password`

#### update Restaurant 100007
`curl -s -X PUT -d '{"name":"Updated restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/restaurants/100007 --user admin:password`

#### delete Restaurant 100007
`curl -s -X DELETE http://localhost:8080/graduation/rest/restaurants/100007 --user admin:password`

#### get Vote 100023
`curl -s http://localhost:8080/graduation/rest/votes/100023 --user admin:password`

#### create Vote 
`curl -s -X PUT -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/votes?restaurantId=100006 --user user:password`

#### delete Vote 100023
`curl -s -X DELETE http://localhost:8080/graduation/rest/votes/100023 --user admin:password`

------------------------------

**Installation**
    
    https://github.com/leonaugust/graduation.git
    
    mvn clean package
    mvn cargo:run
    
------------------------------

**Authorization**

User(name: user, login: user, password: password)

Admin(name: admin, login: admin, password: password)

------------------------------

**Documentation**

Header: 

Content-Type:application/json;charset=UTF-8

------------------------------
URL: /rest/admin/users

Notes: Actions are available only for admins

Get User
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/admin/users/:id

* Method: GET

* URL Params: 

    `id=[integer]`

* Data Params: None

* Success Response:

        Code: 200
        Content: {
                   "id": 100000,
                   "name": "user",
                   "login": "user",
                   "password": "password",
                   "roles": [
                     "USER"
                   ]
                 }
                 
* Error Response:

        Code: 404 Not Found, 401 Unauthorized, 403 Forbidden
        
-------

Create User
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/admin/users

* Method: POST

* URL Params: None

* Data Params: 

        {"name":"New User","login":"test-login","password":"test-password"}

* Success Response:

        Code: 201 Created
        Content: {
                   "id": 100027,
                   "name": "New User",
                   "login": "test-login",
                   "password": "test-password"
                 }
                 
* Error Response:

        Code: 400 Bad Request, 401 Unauthorized, 403 Forbidden
        
--------------

Update User
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/admin/users/:id

* Method: PUT

* URL Params:

       id=[integer]

* Data Params: 

        {"name":"Updated User","login":"updated-login","password":"updated-password"}

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 409 Conflict, 401 Unauthorized, 403 Forbidden 
        
--------------

Delete User
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/admin/users/:id

* Method: DELETE

* URL Params:

       id=[integer]

* Data Params: None

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 404 Not Found, 401 Unauthorized, 403 Forbidden 
        
--------------  

URL: /rest/profile

Notes: The user can only apply actions to himself

Get User
=====

* Authentication:

        login: user
        password: password

* URL: /rest/profile

* Method: GET

* URL Params: None

* Data Params: None

* Success Response:

        Code: 200
        Content: {
                   "id": 100000,
                   "name": "user",
                   "login": "user",
                   "password": "password",
                   "roles": [
                     "USER"
                   ]
                 }
        
-------

Register
=====

* Authentication: None

* URL: /rest/profile/register

* Method: POST

* URL Params: None

* Data Params: 

        {"name":"New User","login":"test-login","password":"test-password"}

* Success Response:

        Code: 201 Created
        Content: {
                   "id": 100027,
                   "name": "New User",
                   "login": "test-login",
                   "password": "test-password",
                   "roles": [
                     "USER"
                   ]
                 }
                 
* Error Response:

        Code: 409 Conflict
        
--------------

Update User
=====

* Authentication:

        login: user
        password: password

* URL: /rest/profile

* Method: PUT

* URL Params: None

* Data Params: 

        {"name":"Updated User","login":"updated-login","password":"updated-password"}

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 401 Unauthorized, 409 Conflict
        
--------------

Delete User
=====

* Authentication:

        login: user
        password: password

* URL: /rest/profile

* Method: DELETE

* URL Params: None

* Data Params: None

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 401 Unauthorized
        
--------------        

URL: /rest/dishes

Get Dish
=====

* Authentication:

        login: user
        password: password

* URL: /rest/dishes/:id

* Method: GET

* URL Params: 

    id=[integer]

* Data Params: None

* Success Response:

        Code: 200
        Content: {
                   "id": 100008,
                   "name": "Ratatouille",
                   "price": 250,
                   "date": "2020-08-14"
                 }
                 
* Error Response:

        Code: 404 Not Found
        
-------

Create Dish
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/dishes

* Method: POST

* URL Params: 

    restaurantId=[integer]

* Data Params:
        {"date":"2020-08-21","name":"Created lunch","price":300}

* Success Response:

        Code: 201 Created
        Content: {
                   "id": 100027,
                   "name": "Created lunch",
                   "price": 300,
                   "date": "2020-08-21"
                 }
                 
* Error Response:

        Code: 403 Forbidden, 401 Unauthorized, 409 Conflict
        
--------------

Update Dish
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/dishes/:id

* Method: PUT

* URL Params:

       id=[integer]
       restaurantId=[integer]

* Data Params: 

        {"date":"2020-08-11","name":"Updated lunch","price":666}

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 403 Forbidden, 401 Unauthorized, 409 Conflict
        
--------------

Delete Dish
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/dishes/:id

* Method: DELETE

* URL Params:

       id=[integer]

* Data Params: None

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 404 Not Found, 403 Forbidden, 401 Unauthorized
        
--------------  

URL: /rest/restaurants

Get Restaurant
=====

* Authentication:

        login: user
        password: password

* URL: /rest/restaurants/:id

* Method: GET

* URL Params: 

    id=[integer]

* Data Params: None

* Success Response:

        Code: 200
        Content: {
                   "id": 100007,
                   "name": "The Krusty Krab ",
                   "menu": null
                 }
                 
* Error Response:

        Code: 404 Not Found 
        
-------

Create Restaurant
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/restaurants

* Method: POST

* URL Params: None

* Data Params:
        {"name":"Puzzles"}

* Success Response:

        Code: 201 Created
        Content: {
                   "id": 100027,
                   "name": "Puzzles"
                 }
                 
* Error Response:

        Code: 409 Conflict, 401 Unauthorized, 403 Forbidden
        
--------------

Update Restaurant
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/restaurants/:id

* Method: PUT

* URL Params:

       id=[integer]

* Data Params: 

        {"name":"Updated restaurant"}

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 403 Forbidden, 401 Unauthorized, 409 Conflict
        
--------------

Delete Restaurant
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/restaurants/:id

* Method: DELETE

* URL Params:

       id=[integer]

* Data Params: None

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 404 Not Found, 403 Forbidden, 401 Unauthorized
        
--------------

URL: /rest/votes

Get Vote
=====

* Authentication:

        login: user
        password: password

* URL: /rest/votes/:id

* Method: GET

* URL Params: 

    id=[integer]

* Data Params: None

* Success Response:

        Code: 200
        Content: {
                   "id": 100023,
                   "user": null,
                   "date": "2020-08-14",
                   "restaurant": null
                 }
                 
* Error Response:

        Code: 404 Not Found, 401 Unauthorized, 403 Forbidden
        
-------

Create Vote
=====

* Authentication:

        login: user
        password: password

* URL: /rest/votes

* Method: PUT

* URL Params: 

    restaurantId=[integer]

* Data Params: None

* Success Response:

        Code: 201 Created
        Content: {
                   "id": 100027,
                   "user": null,
                   "date": "2020-09-09",
                   "restaurant": null
                 }
                 
* Error Response:

        Code: 400 Bad Request, 401 Unauthorized, 423 Locked
        
--------------

Delete Vote
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/votes/:id

* Method: DELETE

* URL Params:

       id=[integer]

* Data Params: None

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 404 Not Found, 403 Forbidden, 401 Unauthorized

      


        





 
