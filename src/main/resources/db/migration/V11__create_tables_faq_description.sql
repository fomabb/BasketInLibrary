CREATE TABLE IF NOT EXISTS description
(
    id          BIGINT PRIMARY KEY,
    title       VARCHAR(255),
    category    VARCHAR(255),
    description TEXT
);

CREATE TABLE IF NOT EXISTS faq
(
    id                   BIGSERIAL PRIMARY KEY,
    question             VARCHAR(255),
    date_question_create TIMESTAMP(6),
    answer               VARCHAR(255),
    date_answer_create   TIMESTAMP(6),
    description_id BIGINT REFERENCES description(id)
);