CREATE TABLE customer(
    id int primary key,
    name varchar(512),
    email varchar(512) not null unique,
    phone varchar(128),
    orders_count int default 0
);

CREATE TABLE orders(
    id int primary key,
    date_created timestamp default now() not null,
    part_id int not null,
    part_number varchar(512) not null,
    qty int not null,
    customer_id int not null,
    shipping_address text not null,
    comment varchar(2048),
    status varchar(256) default 'NEW',
    foreign key (part_id) REFERENCES catalogue_item(id),
    foreign key (customer_id) REFERENCES customer(id)
);

CREATE INDEX customer_email_idx ON customer(email);
