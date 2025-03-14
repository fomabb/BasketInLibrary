CREATE TABLE IF NOT EXISTS products_carts
(
    id             BIGSERIAL PRIMARY KEY,
    quantity       INTEGER,
    price_quantity DECIMAL,
    product_id     BIGINT REFERENCES products (id),
    cart_id        BIGINT REFERENCES carts (id)
);