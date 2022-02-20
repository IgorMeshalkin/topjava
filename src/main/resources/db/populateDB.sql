DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2021-12-20 20:20:22', 'Обед', 600),
       (100001, '2021-12-20 20:45:22', 'Завтрак', 680),
       (100002, '2021-12-20 20:13:22', 'Ужин', 640),
       (100000, '2021-12-22 20:20:22', 'Обед', 600),
       (100001, '2021-12-21 20:45:22', 'Завтрак', 680),
       (100002, '2021-12-23 20:13:22', 'Ужин', 640),
       (100000, '2021-12-21 20:20:22', 'Обед', 600),
       (100001, '2021-12-23 20:45:22', 'Завтрак', 680),
       (100002, '2021-12-26 20:13:22', 'Ужин', 640);

