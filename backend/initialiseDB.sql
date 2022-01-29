use bastimdb;
-- PERMISSIONS -- 
insert into PERMISSIONS (name) values ("WRITE_USER"); -- 1 --
insert into PERMISSIONS (name) values ("UPDATE_USER"); -- 2 --
insert into PERMISSIONS (name) values ("READ_USER"); -- 3 -- 
insert into PERMISSIONS (name) values ("DELETE_USER"); -- 4 --

insert into PERMISSIONS (name) values ("UPDATE_USER_SELF"); -- 5 --
insert into PERMISSIONS (name) values ("READ_USER_SELF"); -- 6 --
insert into PERMISSIONS (name) values ("DELETE_USER_SELF"); -- 7 --

insert into PERMISSIONS (name) values ("WRITE_PRODUCT"); -- 8 --
insert into PERMISSIONS (name) values ("UPDATE_PRODUCT"); -- 9 --
insert into PERMISSIONS (name) values ("READ_PRODUCT"); -- 10 --
insert into PERMISSIONS (name) values ("DELETE_PRODUCT"); -- 11 --

insert into PERMISSIONS (name) values ("WRITE_CATEGORY"); -- 12 --
insert into PERMISSIONS (name) values ("UPDATE_CATEGORY"); -- 13 --
insert into PERMISSIONS (name) values ("READ_CATEGORY"); -- 14 --
insert into PERMISSIONS (name) values ("DELETE_CATEGORY"); -- 15 --

insert into PERMISSIONS (name) values ("WRITE_ORDER"); -- 16 --
insert into PERMISSIONS (name) values ("UPDATE_ORDER"); -- 17 --
insert into PERMISSIONS (name) values ("READ_ORDER"); -- 18 --
insert into PERMISSIONS (name) values ("DELETE_ORDER"); -- 19 --

insert into PERMISSIONS (name) values ("WRITE_ORDER_SELF"); -- 20 --
insert into PERMISSIONS (name) values ("UPDATE_ORDER_SELF"); -- 21 --
insert into PERMISSIONS (name) values ("READ_ORDER_SELF"); -- 22 --
insert into PERMISSIONS (name) values ("DELETE_ORDER_SELF"); -- 23 --

insert into PERMISSIONS (name) values ("WRITE_ROLE"); -- 24 --
insert into PERMISSIONS (name) values ("UPDATE_ROLE"); -- 25 --
insert into PERMISSIONS (name) values ("READ_ROLE"); -- 26 --
insert into PERMISSIONS (name) values ("DELETE_ROLE"); -- 27 --

insert into PERMISSIONS (name) values ("WRITE_PERMISSION"); -- 28 --
insert into PERMISSIONS (name) values ("UPDATE_PERMISSION"); -- 29 --
insert into PERMISSIONS (name) values ("READ_PERMISSION"); -- 30 --
insert into PERMISSIONS (name) values ("DELETE_PERMISSION"); -- 31 --

insert into PERMISSIONS (name) values ("WRITE_ROLE_PERMISSION"); -- 32 --
insert into PERMISSIONS (name) values ("READ_ROLE_PERMISSION"); -- 33 --
insert into PERMISSIONS (name) values ("DELETE_ROLE_PERMISSION");  -- 34 --

insert into PERMISSIONS (name) values ("WRITE_USER_ROLE");  -- 35 --
insert into PERMISSIONS (name) values ("READ_USER_ROLE");  -- 36 --
insert into PERMISSIONS (name) values ("DELETE_USER_ROLE");  -- 37 --



-- ROLES --
insert into ROLES (role_name) values ("ADMIN");
insert into ROLES (role_name) values ("MODERATOR");
insert into ROLES (role_name) values ("CUSTOMER");

-- ROLE PERMISSSIONS --

-- ADMIN PERMISSIONS --
insert into ROLES_PERMISSIONS values (1,1);
insert into ROLES_PERMISSIONS values (1,2);
insert into ROLES_PERMISSIONS values (1,3);
insert into ROLES_PERMISSIONS values (1,4);
										
insert into ROLES_PERMISSIONS values (1,8);
insert into ROLES_PERMISSIONS values (1,9);
insert into ROLES_PERMISSIONS values (1,10);
insert into ROLES_PERMISSIONS values (1,11);
										  
insert into ROLES_PERMISSIONS values (1,12);
insert into ROLES_PERMISSIONS values (1,13);
insert into ROLES_PERMISSIONS values (1,14);
insert into ROLES_PERMISSIONS values (1,15);
										  
insert into ROLES_PERMISSIONS values (1,16);
insert into ROLES_PERMISSIONS values (1,17);
insert into ROLES_PERMISSIONS values (1,18);
insert into ROLES_PERMISSIONS values (1,19);
										  
insert into ROLES_PERMISSIONS values (1,24);
insert into ROLES_PERMISSIONS values (1,25);
insert into ROLES_PERMISSIONS values (1,26);
insert into ROLES_PERMISSIONS values (1,27);
										  
insert into ROLES_PERMISSIONS values (1,28);
insert into ROLES_PERMISSIONS values (1,29);
insert into ROLES_PERMISSIONS values (1,30);
insert into ROLES_PERMISSIONS values (1,31);
										  
insert into ROLES_PERMISSIONS values (1,32);
insert into ROLES_PERMISSIONS values (1,33);
insert into ROLES_PERMISSIONS values (1,34);
										  
insert into ROLES_PERMISSIONS values (1,35);
insert into ROLES_PERMISSIONS values (1,36);
insert into ROLES_PERMISSIONS values (1,37);
										
-- MODERATOR PERMISSIONS --             
insert into ROLES_PERMISSIONS values (2,5);
insert into ROLES_PERMISSIONS values (2,6);
										
insert into ROLES_PERMISSIONS values (2,8);
insert into ROLES_PERMISSIONS values (2,9);
insert into ROLES_PERMISSIONS values (2,10);
insert into ROLES_PERMISSIONS values (2,11);
										
insert into ROLES_PERMISSIONS values (2,12);
insert into ROLES_PERMISSIONS values (2,13);
insert into ROLES_PERMISSIONS values (2,14);
insert into ROLES_PERMISSIONS values (2,15);
										
-- CUSTOMER PERMISSIONS --              
insert into ROLES_PERMISSIONS values (3,5);
insert into ROLES_PERMISSIONS values (3,6);
										
insert into ROLES_PERMISSIONS values (3,10);
										
insert into ROLES_PERMISSIONS values (3,14);

insert into ROLES_PERMISSIONS values (3,20);
										
insert into ROLES_PERMISSIONS values (3,22);




-- USERS --
insert into users (address, birth_date, city, country, email, first_name, last_name, password, points, postal_address) VALUES ("Dluga 10", "2000-10-19",  "Krakow", "Polska", "kowalski@gmail.com","Janusz", "Kowalski", "$2a$10$8tEHH/2cCq72qLW.dJgjeepRQi9XW4BbEJgItzn71uQmwXaY./Qtu",  1000, "32-002"); -- password: admin123--
insert into users (address, birth_date, city, country, email, first_name, last_name, password, points, postal_address) VALUES ("Malborska 12", "2000-12-14",  "Warszawa", "Polska", "zibon@gmail.com","Piotr", "Zibon", "$2a$10$GZXCZw2haZ/lwkGz.vpqvOJvcc/S89u6yv53tdUXEXioPfCqVJOcu",  0, "30-001"); -- password: 1234--
insert into users (address, birth_date, city, country, email, first_name, last_name, password, points, postal_address) VALUES ("Wroclawska 126a", "1998-03-16",  "Poznan", "Polska", "wolinski@gmail.com","Jakub", "Wolinski", "$2a$10$4xgelx/smcosrWbtehdlDuZrF43vzJXWfFLbAgsGrtkTodbsnuyn.",  0, "35-051"); -- password: wolin0101--
insert into users (address, birth_date, city, country, email, first_name, last_name, password, points, postal_address) VALUES ("Szkolna 152c", "1993-04-16",  "Krakow", "Polska", "frankowski10@gmail.com","Bartosz", "Frankowski", "$2a$10$0kGMozHuckcqucOnsEDOJuYPvPDWVdvSWXpnD2zcqtBT4d7XNIxA.",  0, "35-051"); -- password: fajnehaslo--


-- USER ROELS --
INSERT INTO users_roles values(1, 1);
INSERT INTO users_roles values(2, 2);
INSERT INTO users_roles values(3, 2);
INSERT INTO users_roles values(3, 3);
INSERT INTO users_roles values(4, 3);

-- CATEGORIES --
INSERT INTO categories values(1, "Wszystko", null);

INSERT INTO categories values(2, "Narzedzia i artykuly", 1);
INSERT INTO categories values(3, "Budowa", 1);
INSERT INTO categories values(4, "Wykonczenie", 1);
INSERT INTO categories values(5, "Ogrod", 1);

-- 2 --
INSERT INTO categories values(6, "Mocowania i laczenia", 2);
INSERT INTO categories values(7, "Narzedzia reczne", 2);
INSERT INTO categories values(8, "Elektronarzedzia", 2);

-- 3 --
INSERT INTO categories values(9, "Drzwi", 3);
INSERT INTO categories values(10, "Materialy", 3);

-- 4 --
INSERT INTO categories values(11, "Plytki", 4);

-- 5 --
INSERT INTO categories values(12, "Architektura ogrodowa", 5);



-- 6 --
INSERT INTO categories values(13, "Srubki", 6);
INSERT INTO categories values(14, "Kleje", 6);
INSERT INTO categories values(15, "Gwozdzie", 6);

-- 7 --
INSERT INTO categories values(16, "Srubokrety", 7);
INSERT INTO categories values(17, "Klucze", 7);

-- 8 --
INSERT INTO categories values(18, "Wkretarki i wiertarki", 8);

-- 10 --
INSERT INTO categories values(19, "Drewniane", 10);
INSERT INTO categories values(20, "Gipsowe", 10);
INSERT INTO categories values(21, "Stalowe", 10);


-- 11 --
INSERT INTO categories values(22, "Prawoskretne", 13);
INSERT INTO categories values(23, "Lewoskretne", 13);

-- 16 --
INSERT INTO categories values(24, "Krzyzowe", 16);
INSERT INTO categories values(25, "Izolowane", 16);
INSERT INTO categories values(26, "Plaskie", 16);

-- 21 --
INSERT INTO categories values(27, "Plyty", 21);
INSERT INTO categories values(28, "Wsporniki", 21);

-- 27 --
INSERT INTO categories values(29, "Cienkie", 27);
INSERT INTO categories values(30, "Grube", 27);

-- 30 --
INSERT INTO categories values(31, "Falowane", 30);
INSERT INTO categories values(32, "Proste", 30);
INSERT INTO categories values(33, "Dziurawe", 30);


-- PRODUCTS --


-- śrubki --
INSERT INTO products values(1, 13, "Super unikatowa śrubka obustronnieskrętna!", "RPA","Śrubka cud obustronnie skretna paczka 10", 13.13, 1, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 1);

INSERT INTO products values(2, 22, "Śrubka ts114 firmy DDT to przedmiot, na który zasługujesz!", "DDT","Śrubka ts114 paczka 200", 89.99, 98, "{ \"Produkt\": {\"Producent\": \"DDT\"}", 3);
INSERT INTO products values(3, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka pc11 paczka 100", 29.99, 0, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 12);
INSERT INTO products values(4, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka cd99 paczka 100", 99.99, 420, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 11);
INSERT INTO products values(5, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka e99lo paczka 100", 19.99, 20, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 5);
INSERT INTO products values(6, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka kas aanh paczka 100", 45.99, 210, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 122);
INSERT INTO products values(7, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka 98ikd paczka 100", 77.99, 10, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 112);
INSERT INTO products values(8, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka idk50 paczka 100", 13.99, 0, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 8);
INSERT INTO products values(9, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka dl60 paczka 100", 11.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(10, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka gch67 paczka 100", 18.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(11, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka zuo dla kasjera paczka 100", 11111.11, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(12, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka losowanazwa1 paczka 100", 16.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(13, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka mocnego wkrętu paczka 100", 1.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(14, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka świetnego krzyżyka paczka 100", 99.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(15, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka ze złota paczka 100", 999999.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(16, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka z diamentu paczka 100", 999999999.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(17, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka z antymaterii paczka 100", 999999999999999999999999999999.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(18, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka wspaniałości paczka 100", 777.77, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(19, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka z niepełną paczka 93", 41.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(20, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka oli25 paczka 100", 33.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(21, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka as30 paczka 100", 21.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(22, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka d64 paczka 100", 19.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(23, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka agh60 paczka 100", 9.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);
INSERT INTO products values(24, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka aventador paczka 100", 19.99, 90, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 56);

INSERT INTO products values(25, 22, "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka rx76 paczka 100", 59.99, 68, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 12);

INSERT INTO products values(26, 23, "Super lewoskretna wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka lewoskretna rx76 paczka 100", 59.99, 68, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 12);
INSERT INTO products values(27, 23, "Śrubka lewoskretna  ts114 firmy DDT to przedmiot, na który zasługujesz!", "DDT","Śrubka lewoskretna ts114 paczka 200", 89.99, 98, "{ \"Produkt\": {\"Producent\": \"DDT\"}", 3);
INSERT INTO products values(28, 23, "Super lewoskretna wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka lewoskretna pc11 paczka 100", 29.99, 0, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 12);
INSERT INTO products values(29, 23, "Super lewoskretna  wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka lewoskretna cd99 paczka 100", 99.99, 420, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 11);
INSERT INTO products values(30, 23, "Super lewoskretna wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka lewoskretna e99lo paczka 100", 19.99, 20, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 5);
INSERT INTO products values(31, 23, "Super lewoskretna wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!", "RPA","Śrubka lewoskretna kas aanh paczka 100", 45.99, 210, "{ \"Produkt\": {\"Producent\": \"RPA\"}", 122);



-- płyty --
INSERT INTO products values(32, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "Płytki Falowane sp z o.o.", "Płytka falowana 1mx1m stal nierdzewna", 89.99, 11, "", 121);
INSERT INTO products values(33, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "Płytki Falowane sp z o.o.", "Płytka falowana 2mx2m stal nierdzewna", 119.99,0, "", 11);
INSERT INTO products values(34, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 0);
INSERT INTO products values(35, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(36, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 2mx2m stal", 89.99, 651, "", 111);
INSERT INTO products values(37, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka 'kosmos'- 10mx10m stal", 1119.99, 1, "", 111);
INSERT INTO products values(38, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1, "", 0);
INSERT INTO products values(39, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(40, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1521, "", 0);
INSERT INTO products values(41, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(42, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 2mx1m stal", 19.99, 1921, "", 4);
INSERT INTO products values(43, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(44, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx3m stal", 19.99, 21, "", 343);
INSERT INTO products values(45, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(46, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka 6hr - 4mx1m stal", 19.99, 21, "", 34);
INSERT INTO products values(47, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(48, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 0, "", 5);
INSERT INTO products values(49, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(50, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka t45 - 1mx12m stal", 19.99, 0, "", 4);
INSERT INTO products values(51, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(52, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 3mx1m stal", 19.99, 1221, "", 34);
INSERT INTO products values(53, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(54, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka qrx - 1mx3m stal", 19.99, 41, "", 4);
INSERT INTO products values(55, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(56, 31, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka r4 - 1mx4m stal", 19.99, 0, "", 32);
INSERT INTO products values(57, 31, "Jedyna taka płytka falowana. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
 
INSERT INTO products values(58, 32, "Jedyna taka płytka prosta. Nie znajdziesz lepszej więc kup ją", "płytex sp z o.o.", "Płytka 'płytex'- 10mx10m stal", 1119.99, 1, "", 111);
INSERT INTO products values(59, 32, "Jedyna taka płytka prosta. Nie znajdziesz lepszej więc kup ją", "Płytki Falowane sp z o.o.", "Płytka prosta 1mx1m stal nierdzewna", 89.99, 11, "", 121);
INSERT INTO products values(60, 32, "Jedyna taka płytka prosta. Nie znajdziesz lepszej więc kup ją", "Płytki Falowane sp z o.o.", "Płytka prosta 2mx2m stal nierdzewna", 119.99,0, "", 11);
INSERT INTO products values(61, 32, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 0);
INSERT INTO products values(62, 32, "Jedyna taka płytka prosta. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(63, 32, "Jedyna taka płytka prosta. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 2mx2m stal", 89.99, 651, "", 111);
INSERT INTO products values(64, 32, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "Płytka 'kosmos'- 10mx10m stal", 1119.99, 1, "", 111);
 
INSERT INTO products values(65, 33, "Jedyna taka płytka dziurawa. Nie znajdziesz lepszej więc kup ją", "płytex sp z o.o.", "Płytka 'płytex'- 10mx10m stal", 1119.99, 1, "", 111);
INSERT INTO products values(66, 33, "Jedyna taka płytka dziurawa. Nie znajdziesz lepszej więc kup ją", "Płytki dziurawa sp z o.o.", "Płytka dziurawa 1mx1m stal nierdzewna", 89.99, 11, "", 121);
INSERT INTO products values(67, 33, "Jedyna taka płytka dziurawa. Nie znajdziesz lepszej więc kup ją", "Płytki dziurawa sp z o.o.", "Płytka dziurawa 2mx2m stal nierdzewna", 119.99,0, "", 11);
INSERT INTO products values(68, 33, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 0);
INSERT INTO products values(69, 33, "Jedyna taka płytka dziurawa. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 1mx1m stal", 19.99, 1221, "", 111);
INSERT INTO products values(70, 33, "Jedyna taka płytka dziurawa. Nie znajdziesz lepszej więc kup ją", "PolskiePłyty", "Płytka płycix - 2mx2m stal", 89.99, 651, "", 111);
INSERT INTO products values(71, 33, "Stalowa płytka wyprodukowana z welką dbałością o szczegóły na każdym etapie produkcji", "Płytka 'kosmos'- 10mx10m stal", 1119.99, 1, "", 111);

