CREATE TABLE IF NOT EXISTS products_orders
(
    id                       BIGSERIAL PRIMARY KEY,
    creation_time            TIMESTAMP,
    delivery_report_date     TIMESTAMP(6) WITHOUT TIME ZONE,
    report_on_the_event_date TIMESTAMP,
    status_delivery_id       INTEGER,
    product_id               BIGINT REFERENCES products (id),
    order_id                 BIGINT REFERENCES orders (id)
);