CREATE TABLE IF NOT EXISTS products
(
    id        BIGSERIAL PRIMARY KEY,
    title     VARCHAR(255),
    author    VARCHAR(255),
    genre     VARCHAR(255),
    price     DECIMAL,
    delivery  INTEGER,
    status    VARCHAR(50),
    publisher VARCHAR(255),
    count     INTEGER
);