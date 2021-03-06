DELETE
FROM dish;
DELETE
FROM vote;
DELETE
FROM restaurant;
DELETE
FROM user_role;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, login, password)
VALUES ('user', 'user', '{noop}password'),
       ('admin', 'admin', '{noop}password'),
       ('barney', 'stinson', '{noop}password'),
       ('lily', 'aldrin', '{noop}password'),
       ('ted', 'mosby', '{noop}password');


INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('ADMIN', 100002),
       ('USER', 100003),
       ('USER', 100004);

INSERT INTO restaurant (name)
VALUES ('Gusteau''s'),
       ('Pizza Planet'),
       ('The Krusty Krab ');

INSERT INTO dish (name, price, date, restaurant_id)
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

INSERT INTO vote (user_id, date, restaurant_id)
VALUES (100000, '2020-08-14', 100005),
       (100001, '2020-08-14', 100005),
       (100002, '2020-08-14', 100006),
       (100003, '2020-08-14', 100005);
