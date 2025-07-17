drop table if exists usr_users;
drop table if exists loc_locations;

create table loc_locations
(
    id               bigserial primary key unique,
    active           boolean,
    address          text,
    number           text,
    neighborhood     text,
    complement       text,
    zip_code         text,
    city             text,
    state            text,
    last_modify_date timestamp
);
create table usr_users
(
    id               bigserial primary key unique,
    active           boolean,
    name             text,
    login            text,
    password         text,
    last_modify_date timestamp,
    loc_location_id  bigint,
    constraint fk_location foreign key (loc_location_id) references loc_locations (id)
);

insert into loc_locations(active,
                          address,
                          number,
                          neighborhood,
                          zip_code,
                          city,
                          state,
                          last_modify_date)
values (true,
        'José Frigeri',
        '129',
        'Jardim Brasil',
        '13474160',
        'Americana',
        'São Paulo',
        current_timestamp);