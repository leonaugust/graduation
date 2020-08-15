DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, login, password)
VALUES ('User', 'user', 'password'),
       ('Admin', 'admin', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants (name, address, website, user_id)
VALUES ('La Ratatouille', 'Disney universe', 'www.la-ratatouille.com', 100001),
       ('Pizza Planet', 'Disney universe', 'www.pizza-planet.com', 100001),
       ('The Krusty Krab ', '831 Bottom Feeder Lane', 'www.krusty-krab.com', 100001);

INSERT INTO meals (name, price, restaurant_id)
VALUES ('Ratatouille', 25, 100002),
       ('Buffalo Wings', 30, 100002),
       ('Charbroiled Oysters', 27, 100002),
       ('Meringue Pie', 10, 100002),
       ('Omelet', 9, 100002),

       ('Pepperoni Pizza', 10.99, 100003),
       ('New York-Style Pizza', 9.54, 100003),
       ('Greek-Style Pizza', 8, 100003),
       ('Sicilian Pizza', 12, 100003),
       ('Tomato Pie', 9, 100003),

       ('Cheeseburger', 6.55, 100004),
       ('The Original Burger', 5, 100004),
       ('Luger Burger', 7.29, 100004),
       ('Bacon Cheeseburger', 7, 100004),
       ('Bob''s Burger Barn', 6.23, 100004);

INSERT INTO votes(user_id, date_time, restaurant_id)
VALUES (100000, '2020-08-14 16:37:00', 100002),
       (100001, '2020-08-14 17:20:00', 100003);
