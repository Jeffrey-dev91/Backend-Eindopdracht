


insert into roles (rolename) values ('ADMIN');
insert into roles(rolename)values('USER');
insert into roles(rolename)values('EMPLOYEE');

insert into users(username, email, password)values ('jeffrey','jeffrey@hotmail.com', '1234');
insert into users(username, email, password)values ('ben','ben@hotmail.com','abc');
insert into users(username, email, password)values ('niek','niek@hotmail.com','12345');


insert into users_roles(username, rolename)values ('jeffrey','ADMIN');
insert into users_roles(username, rolename)values ('ben', 'USER');
insert into users_roles(username, rolename)values ('niek','EMPLOYEE');

insert into genres(name) values('Fantasy');

insert into books(title,author,isbn,total_copies,available_copies)
values('Harry Potter', 'J.K Rowling', '123456', 10, 10);