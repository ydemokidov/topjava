DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, calories, user_id, description) VALUES
('2001-09-28 01:00:00',200,100000,'Еда 1'),
('2001-09-28 01:00:00',250,100000,'Еда 11'),
('2001-09-28 01:00:00',220,100000,'Еда 12'),
('2001-09-28 01:00:00',290,100000,'Еда 13'),
('2001-09-28 01:00:00',210,100000,'Еда 14'),
('2001-09-29 01:00:00',250,100000,'Еда 15'),
('2001-09-29 01:00:00',280,100000,'Еда 16'),
('2001-09-29 01:00:00',200,100000,'Еда 17'),
('2001-09-29 01:00:00',290,100000,'Еда 18');