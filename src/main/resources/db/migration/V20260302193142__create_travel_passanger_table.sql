    create table travel_passanger (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        status varchar(255),
        passenger_id varchar(255),
        travel_id varchar(255),
        primary key (id)
    );


    alter table if exists travel_passanger 
       add constraint FK71mntn86iu2ifvq49jacnnfh8 
       foreign key (passenger_id) 
       references users;

    alter table if exists travel_passanger 
       add constraint FKdjm2f26j2ctlrr3nqc5fv5xot 
       foreign key (travel_id) 
       references travels;