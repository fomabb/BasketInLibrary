DROP FUNCTION IF EXISTS make_tsvector_book_product(title varchar, genre varchar, author varchar);

create or replace function make_tsvector_book_product(title varchar, genre varchar, author varchar)
    returns tsvector as
$$
begin
    return (setweight(to_tsvector('english', title), 'A') ||
            setweight(to_tsvector('english', genre), 'B') ||
            setweight(to_tsvector('english', author), 'C'));
end
$$ language 'plpgsql' immutable;

CREATE INDEX IF NOT EXISTS idx_fts_articles ON products
    USING gin (make_tsvector_book_product(title, genre, author));

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS book_product_trgm_idx ON products USING gin (title gin_trgm_ops);