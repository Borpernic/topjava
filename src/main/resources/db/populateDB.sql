DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1000),
  ('ROLE_ADMIN', 1001);

INSERT INTO meals (user_id, dateTime, description, calories) VALUES
  ( 100000,date '2001-09-28','Завтрак', 2000),
  ( 100000, date '2001-09-28','Обед', 2000),
  ( 100000, date '2001-09-28','ужин', 2000),
  ( 100001, date '2001-09-28','Завтрак', 2000),
  ( 100001,date '2001-09-28','Обед', 2000),
  ( 100001,date '2001-09-28','ужин', 2000);
