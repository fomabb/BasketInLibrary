CREATE TABLE IF NOT EXISTS carts
(
    id         BIGSERIAL PRIMARY KEY,
    dateCreate TIMESTAMP,
    allPrice   DECIMAL
);