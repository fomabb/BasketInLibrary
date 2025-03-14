CREATE TABLE IF NOT EXISTS users_roles
(
  user_id BIGINT REFERENCES users(id),
  role_id BIGINT REFERENCES roles(id)
);