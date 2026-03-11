


insert into roles (rolename) values ('ADMIN');
insert into roles(rolename)values('EMPLOYEE');
insert into roles(rolename)values('USER');




INSERT INTO users (username, email, password) values
 ('jeffrey','jeffrey@hotmail.com','$2a$12$Y/AeHoz.At8DyMgkYwMxj.yV0lWtkIblg4uPb5D.A987VrBHYlRUS'),
 ('jasper','jasper@hotmail.com','$2a$12$Lu8cvyKVqu10ggrUKrvpneAzq59anRfxCRZidd.UoIK7.eAgrb.iO'),
('kevin','kevin@hotmail.com', '$2a$12$Il2y3Nri9GO07CK0665sGOmKyNjzLdchpNSSyscxAQTHXReQTISQ6');



insert into users_roles(user_id, rolename)values
    (1,'ADMIN'),
    (2,'EMPLOYEE'),
    (3,'USER');

