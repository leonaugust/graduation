[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2fe90b89d1f642909e83e3bbf258e615)](https://www.codacy.com/manual/leonaugust/graduation?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=leonaugust/graduation&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/leonaugust/graduation.svg?branch=master)](https://travis-ci.org/leonaugust/graduation)


Restaurant voting system
===============================
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.
___
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.
___
