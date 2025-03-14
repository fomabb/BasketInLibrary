CREATE TABLE IF NOT EXISTS carts
(
    id         BIGSERIAL PRIMARY KEY,
    all_price   NUMERIC(38, 2),
    date_create TIMESTAMP(6)
);