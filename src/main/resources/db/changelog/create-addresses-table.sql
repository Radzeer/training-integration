create table addresses (
                    id bigint auto_increment,
                    zip varchar(255),
                    city varchar(255),
                    line1 varchar(255),
                    employee_id bigint,
                    primary key (id)
                );
alter table addresses add constraint foreign_key_employee_1 foreign key (employee_id) references employees (id);