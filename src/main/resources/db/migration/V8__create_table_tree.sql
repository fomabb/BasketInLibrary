CREATE TABLE IF NOT EXISTS tree
(
    id        BIGSERIAL PRIMARY KEY,
    category  VARCHAR(255),
    parent_id BIGINT
        CONSTRAINT fk_parent_id
            REFERENCES tree ON DELETE CASCADE
);

alter table products
    add column node_id bigint
        constraint fk_category_id references tree;