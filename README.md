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

#### get Meals 100005
`curl http://localhost:8080/graduation/rest/meals?restaurantId=100005  --user user:password`

#### create Meal
`curl -s -X POST -d '{"date":"2020-08-21","name":"Created lunch","price":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/meals?restaurantId=100005 --user admin:password`

#### register User
`curl -s -i -X POST -d '{"name":"New User","login":"test-login","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/profile/register`

#### get Restaurant 100007
`curl -s http://localhost:8080/graduation/rest/restaurants/100007 --user admin:password`

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
                   ],
                   "restaurants": null
                 }
                 
* Error Response:

        Code: 500 Internal Server Error
        
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

        Code: 400 Bad Request
        
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

        Code: 400 Bad Request
        
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

        Code: 500 Internal Server Error
        
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
                   ],
                   "restaurants": null
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
                   "id": 100029,
                   "name": "New User",
                   "login": "test-login",
                   "password": "test-password",
                   "roles": [
                     "USER"
                   ]
                 }
                 
* Error Response:

        Code: 400 Bad Request
        
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

        Code: 401 Unauthorized
        
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

URL: /rest/meals

Notes: Only restaurant owners can modify meals

Get Meal
=====

* Authentication:

        login: user
        password: password

* URL: /rest/meals/:id

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

        Code: 500 Internal Server Error
        
-------

Create Meal
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/meals

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

        Code: 403 Forbidden or 401 Unauthorized
        
--------------

Update Meal
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/meals/:id

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

        Code: 403 Forbidden
        
--------------

Delete Meal
=====

* Authentication:

        login: admin
        password: password

* URL: /rest/meals/:id

* Method: DELETE

* URL Params:

       id=[integer]

* Data Params: None

* Success Response:

        Code: 204 
        Content: None
                 
* Error Response:

        Code: 500 Internal Server Error or 403 Forbidden
        
--------------  

URL: /rest/restaurants

Notes: Only restaurant owners can modify their restaurants

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
                   "id": 100005,
                   "name": "Gusteau's",
                   "menu": null
                 }
                 
* Error Response:

        Code: 500 Internal Server Error
        
-------

Create Restaurant
=====

* Authentication:

        login: user
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

        Code: 500 Internal Server Error or 401 Unauthorized or 400 Bad Request
        
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

        Code: 403 Forbidden or 400 Bad Request
        
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

        Code: 500 Internal Server Error or 403 Forbidden
        
--------------

URL: /rest/votes

Notes: Even admins aren't allowed to delete votes of others

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

        Code: 500 Internal Server Error
        
-------

Delete Vote
=====

* Authentication:

        login: user
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

        Code: 500 Internal Server Error or 403 Forbidden

      


        





 
