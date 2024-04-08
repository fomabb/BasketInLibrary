CREATE INDEX IF NOT EXISTS idx_fts_articles ON book
    USING gin (make_tsvector(title, genre, author));

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS book_trgm_idx ON book USING gin (title gin_trgm_ops);


-- insert into role(name)
-- values ('ROLE_ADMIN');
--
-- insert into role(name)
-- values ('ROLE_USER');


