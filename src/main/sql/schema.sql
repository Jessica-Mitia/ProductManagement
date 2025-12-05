CREATE TABLE product (
    id serial primary key,
    name varchar(100) not null,
    price numeric,
    creation_datetime timestamp default now()
);

CREATE TABLE product_category (
    id serial primary key,
    name varchar(100) not null,
    product_id int references product(id)
);