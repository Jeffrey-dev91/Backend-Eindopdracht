
INSERT INTO roles (rolename) VALUES ('ADMIN');
INSERT INTO roles (rolename) VALUES ('EMPLOYEE');
INSERT INTO roles (rolename) VALUES ('USER');


INSERT INTO users (id, username, email, password) VALUES
(1, 'jeffrey', 'jeffrey@hotmail.com', '$2a$12$Y/AeHoz.At8DyMgkYwMxj.yV0lWtkIblg4uPb5D.A987VrBHYlRUS'),
(2, 'jasper', 'jasper@hotmail.com', '$2a$12$Lu8cvyKVqu10ggrUKrvpneAzq59anRfxCRZidd.UoIK7.eAgrb.iO'),
(3, 'kevin', 'kevin@hotmail.com', '$2a$12$Il2y3Nri9GO07CK0665sGOmKyNjzLdchpNSSyscxAQTHXReQTISQ6');


INSERT INTO users_roles (user_id, rolename) VALUES
(1, 'ADMIN'),
(2, 'EMPLOYEE'),
(3, 'USER');


INSERT INTO genres (id, name) VALUES
(1, 'Thriller'),
(2, 'Fantasy'),
(3, 'Literatuur'),
(4, 'Science Fiction');


INSERT INTO books (id, author, isbn, title, totalcopies, availablecopies, genre_id) VALUES

(1, 'Herman Koch', '9789021408132', 'Het Diner', 5, 5, 1),
(2, 'J.K. Rowling', '9780545010221', 'Harry Potter en de Relieken van de Dood', 10, 10, 2),
(3, 'Gerard Reve', '9789024576791', 'De Avonden', 3, 3, 3),
(4, 'Frank Herbert', '9780441172719', 'Dune', 7, 7, 4),
(5, 'Karin Slaughter', '9789400511873', 'Gespleten', 4, 0, 1);
