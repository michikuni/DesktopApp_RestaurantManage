create database javaConnectTest;
use javaConnectTest;


create table admin(
	id int not null auto_increment primary key,
    name varchar(20) not null,
    username varchar(100) not null unique,
    password varchar(100) not null,
    role varchar(100) default "staff",
    phone varchar(20)
);
alter table admin
add constraint UNIQUE_username  unique (username);
insert into admin (name,username,password,role,phone)
values ("Vu Cong Hau","hauvu", "123123","admin","012341231");
insert into admin (name,username,password,role,phone)
values ("Na Na","nana", "123123","staff","051284091");
insert into admin (name,username,password,role,phone)
values ("Long","long", "123123","admin","031323421");
insert into admin (name,username,password,role,phone)
values ("Hung","hung", "123123","staff","0923953421");
create table dish(
	id int not null auto_increment primary key,
	name varchar(50) not null,
    price DECIMAL(10, 2) not null,
    category varchar(50),
    image mediumblob
);
INSERT INTO dish (name, price, category) VALUES ('Spaghetti Bolognese', 12.99, 'Italian');
INSERT INTO dish (name, price, category) VALUES ('Chicken Tikka Masala', 15.99, 'Indian');
INSERT INTO dish (name, price, category) VALUES ('Caesar Salad', 8.99, 'Salad');
INSERT INTO dish (name, price, category) VALUES ('Coffee', 2.99, 'Beverage');
INSERT INTO dish (name, price, category) VALUES ('Iced Tea', 1.99, 'Beverage');
INSERT INTO dish (name, price, category) VALUES ('Orange Juice', 3.49, 'Beverage');
select * from dish;
select * from admin;

create table bill(
	id int not null auto_increment primary key,
	time datetime,
    price DECIMAL(10, 2) not null
);
create table billDish(
	billId int not null,
    dishId int not null,
    quantity int not null,
    foreign key(billId) references bill(id),
    foreign key(dishId) references dish(id)
);
create table _table(
	id int not null auto_increment primary key,
	name varchar(50),
    status varchar(50)
);
-- Insert records into the dish table
INSERT INTO dish (name, price, category) VALUES
('Pasta', 12.99, 'Main Course'),
('Salad', 7.99, 'Appetizer'),
('Chocolate Cake', 9.99, 'Dessert');
INSERT INTO dish (name, price, category) VALUES
('Beef steak', 12.22, 'Main Course'),
('Poemecake', 9.99, 'Dessert'),
('Lemon tart', 5.24,'Dessert');
-- Insert records into the bill table
INSERT INTO bill (time, price) VALUES
('2023-12-15 12:30:00', 30.97),
('2023-12-16 13:45:00', 25.50);
select * from dish;
select * from admin;
select * from _table;
-- Get the auto-generated IDs of the inserted bills


-- Insert records into the billDish table for the first bill
INSERT INTO billDish (billId, dishId, quantity) VALUES
(5, 8, 2),  -- 2 servings of Pasta
(3, 9, 1),  -- 1 serving of Salad
(2, 10, 3);  -- 3 servings of Chocolate Cake;

-- Insert records into the billDish table for the second bill
INSERT INTO billDish (billId, dishId, quantity) VALUES
(7, 6, 6),  -- 2 servings of Salad
(7, 8, 8);  -- 1 serving of Chocolate Cake;
select * from bill where time between "2023-12-15" and "2023-12-16";
select * from bill, dish,billdish where bill.id = billdish.billid and dish.id = billdish.dishid ;

INSERT INTO bill (time, price) VALUES
('2023-1-5 12:30:00', 12.32),
('2023-2-7 13:45:00', 25.50),
('2023-12-5 12:30:00', 12.32),
('2023-12-7 13:45:00', 25.50);
select distinct category from dish;
INSERT INTO bill (time, price) VALUES
('2023-12-16 12:30:00', 20.12);
SELECT b.id AS bill_id, b.time, b.price AS bill_price, d.id AS dish_id, d.name, d.price AS dish_price, bd.quantity FROM bill b JOIN billDish bd ON b.id = bd.billId JOIN dish d ON bd.dishId = d.id
SELECT b.id AS bill_id, b.time, b.price AS bill_price, d.id AS dish_id, d.name, d.price AS dish_price, bd.quantity FROM bill b JOIN billDish bd ON b.id = bd.billId JOIN dish d ON bd.dishId = d.id where b.id = 7