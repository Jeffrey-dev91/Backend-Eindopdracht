


insert into roles (rolename) values ('ROLE_ADMIN');
insert into roles(rolename)values('ROLE_USER');
insert into roles(rolename)values('ROLE_EMPLOYEE');


INSERT INTO users (username, email, password) values
 ('admin',' admin@hotmail.com','$2a$12$7JY6VuymRjdxiMQf7dxEGe9xH99iFWWjkY6XewlfqxaI9/8iUo496'),
('user','user@hotmail.com', '$2a$12$jBe4S4EQFbFummeBVl97meeVHM7VgsgnT.KOcZ3bAEV4tNcLK5FGO'),
('employee','employee@hotmail.com','$2a$12$FK.ZdT27HTPxllTWDuBo5efggfA3OH9t61xeZRyE67nf.togM9CWS');

insert into users_roles(user_id, rolename)values
    (1,'ROLE_ADMIN'),
    (2,'ROLE_USER'),
    (3,'ROLE_EMPLOYEE');

insert into books (id, title,author, isbn, totalcopies,availablecopies) values
            (1, 'Spring in Action','JWK', 12345,5,10);


insert into loans(id, book_id, user_id, loandate, duedate, returned) values
        (1,1,1,'2026-02-19','2026-03-01',false);