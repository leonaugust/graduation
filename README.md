[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2fe90b89d1f642909e83e3bbf258e615)](https://www.codacy.com/manual/leonaugust/graduation?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=leonaugust/graduation&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/leonaugust/graduation.svg?branch=master)](https://travis-ci.org/leonaugust/graduation)


Restaurant voting system
===============================

cURL commands:

#### get Meals by restaurant id 100005
`curl http://localhost:8080/graduation/rest/meals?restaurantId=100005  --user user:password`

#### create Meal
`curl -s -X POST -d '{"date":"2020-08-21","name":"Created lunch","price":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/meals?restaurantId=100005 --user admin:password`

#### register User
`curl -s -i -X POST -d '{"name":"New User","login":"test-login","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/profile/register`

#### get Restaurant 100007
`curl -s http://localhost:8080/graduation/rest/restaurants/100007 --user admin:password`