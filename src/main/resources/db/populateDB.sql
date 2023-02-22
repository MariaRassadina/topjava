DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2023-02-19 08:00:00', 'завтрак1 User', '600', 100000),
       ('2023-02-19 13:00:00', 'обед1 User', '800', 100000),
       ('2023-02-19 18:00:00', 'ужин1 User', '500', 100000),
       ('2023-02-18 08:00:00', 'завтрак2 User', '800', 100000),
       ('2023-02-19 08:00:00', 'завтрак Admin', '600', 100001),
       ('2023-02-19 13:30:00', 'обед Admin', '800', 100001),
       ('2023-02-19 19:00:00', 'ужин Admin', '400', 100001);