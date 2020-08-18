DELETE
FROM meals;
DELETE
FROM votes;
DELETE
FROM restaurants;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, login, password)
VALUES ('user', 'user', 'password'),
       ('admin', 'admin', 'password'),
       ('barney', 'stinson', 'password'),
       ('lily', 'aldrin', 'password'),
       ('ted', 'mosby', 'password');


INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('ADMIN', 100002),
       ('USER', 100003),
       ('USER', 100004);

INSERT INTO restaurants (name, address, website, user_id)
VALUES ('La Ratatouille', 'Disney universe', 'www.la-ratatouille.com', 100001),
       ('Pizza Planet', 'Disney universe', 'www.pizza-planet.com', 100002),
       ('The Krusty Krab ', '831 Bottom Feeder Lane', 'www.krusty-krab.com', 100001);

INSERT INTO meals (name, price, date, restaurant_id)
VALUES ('Ratatouille', 250, '2020-08-14', 100005),
       ('Buffalo Wings', 300, '2020-08-14', 100005),
       ('Charbroiled Oysters', 270, '2020-08-14', 100005),
       ('Meringue Pie', 100, '2020-08-14', 100005),
       ('Omelet', 90, '2020-08-14', 100005),

       ('Pepperoni Pizza', 1099, '2020-08-14', 100006),
       ('New York-Style Pizza', 954, '2020-08-14', 100006),
       ('Greek-Style Pizza', 80, '2020-08-14', 100006),
       ('Sicilian Pizza', 120, '2020-08-14', 100006),
       ('Tomato Pie', 90, '2020-08-14', 100006),

       ('Cheeseburger', 655, '2020-08-14', 100007),
       ('The Original Burger', 50, '2020-08-14', 100007),
       ('Luger Burger', 729, '2020-08-14', 100007),
       ('Bacon Cheeseburger', 70, '2020-08-14', 100007),
       ('Bob''s Burger Barn', 623, '2020-08-14', 100007);

INSERT INTO votes(user_id, date, restaurant_id)
VALUES (100000, '2020-08-14', 100005),
       (100001, '2020-08-14', 100005),
       (100002, '2020-08-14', 100006),
       (100003, '2020-08-14', 100005);
