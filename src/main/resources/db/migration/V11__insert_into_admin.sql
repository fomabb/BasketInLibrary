INSERT INTO carts (id, datecreate, allprice)
VALUES (1, now(), null);

INSERT INTO orders(id, datetime)
values (1, now());

INSERT INTO users (id, username, email, password, cart_id, order_id)
VALUES (1,
        'admin',
        'kirabb123@gmail.com',
        '$2y$10$w/4L6l/9C98taW0D8hnbAuP3olgWLzCl/9jxj9HjhPldsyc/zQ1v2',
        1,
        1);