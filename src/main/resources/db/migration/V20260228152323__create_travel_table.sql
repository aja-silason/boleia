    create table travels (
        id VARCHAR(100) not null,
        created_at timestamp(6),
        destiny varchar(255) not null,
        origin varchar(255) not null,
        price varchar(255) not null,
        seats varchar(255) not null,
        start_time timestamp(6) not null,
        status varchar(255) not null,
        updated_at timestamp(6),
        vehicle_id VARCHAR(100) not null,
        driver_id varchar(255) not null,
        primary key (id)
    );

    create table travels_passangers (
        travel_id varchar(255) not null,
        passanger_id varchar(255) not null
    );


    alter table if exists travels 
       add constraint FKlspsvxlrwfk1u8mufpnhr4x3y 
       foreign key (vehicle_id) 
       references vehicles;

    alter table if exists travels 
       add constraint FKe6pm03eicduvcxlcx9e5rfe8y 
       foreign key (driver_id) 
       references drivers;

    alter table if exists travels_passangers 
       add constraint FKqj4u2xjhlj1xpaeigjor5p38i 
       foreign key (passanger_id) 
       references users;

    alter table if exists travels_passangers 
       add constraint FKh8u4k1026veubtbqc4x37qj6x 
       foreign key (travel_id) 
       references travels;