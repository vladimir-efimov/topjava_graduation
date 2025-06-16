INSERT INTO USERS (name, email, password, blocked)
VALUES ('Admin', 'admin@restaurants.ru', 'admin', false),
       ('SimpleUser', 'user@restaurants.ru', 'user1', false),
       ('TestUser1', 'user1@restaurants.ru', '12345', false),
       ('TestUser2', 'user2@restaurants.ru', '12345', false),
       ('TestUser3', 'user3@restaurants.ru',  '12345', false);

INSERT INTO USER_ROLE (role, user_id)
VALUES ('ADMIN', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5);

INSERT INTO RESTAURANT (name, address)
VALUES ('restaurant1', 'address1'),
       ('fastfood2', 'address2_1'),
       ('fastfood2', 'address2_2'),
       ('cafe3', 'address3');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('dish1', 300.0, 1),
       ('dish2', 200.0, 1),
       ('dish3', 200.0, 1),
       ('dish4', 200.0, 2),
       ('dish5', 300.0, 2),
       ('dish6', 300.0, 3),
       ('dish7', 300.0, 3);

INSERT INTO MENU (serve_date, restaurant_id)
VALUES ('2024-01-12', 1),
       ('2024-01-13', 1),
       ('2024-01-13', 2);

INSERT INTO MENU_DISH (menu_id, dish_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 3),
       (3, 4);

INSERT INTO VOTE (user_id, restaurant_id, date)
VALUES (2, 1, '2024-01-13'),
       (3, 3, '2024-01-13'),
       (4, 3, '2024-01-13');
