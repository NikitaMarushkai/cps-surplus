CREATE TABLE catalogue_item(
    id int primary key,
    part_number varchar(512) not null,
    description varchar(512),
    price numeric(19, 2) not null,
    qty int not null,
    weight numeric(19, 2) not null,
    dimensions varchar(512),
    note varchar(2048),
    location varchar(2) not null
);

CREATE SEQUENCE seq_catalogue_item OWNED BY catalogue_item.id;

CREATE INDEX catalogue_part_number_idx ON catalogue_item(part_number);
CREATE INDEX catalogue_description_idx ON catalogue_item(description);