-----------------Restaurant management System-----------------------
--------------------This file creates tables---------------------

-----------------------create table ITEM-------------------------
set linesize 200;
set pagesize 200;
CREATE TABLE F15_10_Item
(
ItemID int NOT NULL,
ItemName varchar(50) NOT NULL,
ItemType varchar(50),
ItemCat varchar(50),
ItemCost int NOT NULL,
primary key (ItemID),
unique(ItemName)
);

-----------------------create table CUSTOMER-------------------------
CREATE TABLE F15_10_Customer
(
CID int NOT NULL,
FirstName varchar(50) NOT NULL,
LastName varchar(50),
Address varchar(50),
Pnumber int,
primary key (CID) 
);

-----------------------create table EMPLOYEE-------------------------
CREATE TABLE F15_10_Employee 
(
EmpID int NOT NULL,
FName varchar(50),
LName varchar(50),
Phonenumber int,
Salary int,
Designation varchar(50),
primary key (EmpID)
);

-----------------------create table SUPPLIER-------------------------
CREATE TABLE F15_10_Supplier
(
SupID int NOT NULL,
SupName varchar(50) NOT NULL,
SupContact int,
SupCost int NOT NULL,
primary key (SupID)
);

-----------------------create table ORDERS-------------------------
CREATE TABLE F15_10_Orders   
(
ItemID int,
CID int,
Quantity int NOT NULL, 
primary key(ItemID,CID),
FOREIGN KEY(ItemID) REFERENCES F15_10_Item(ItemID) on delete cascade,
FOREIGN key(CID) references F15_10_Customer(CID) on delete cascade
);

----------------------create table INGREDIENT-------------------------
CREATE TABLE F15_10_Ingredient      
(
IngID int NOT NULL,
IngName varchar(50) NOT NULL,
primary key(IngID),
unique(IngName)
);

-----------------------create table CONSISTSOF-------------------------
CREATE TABLE F15_10_ConsistsOf      
(
IngID int,
ItemID int,
Quantity int NOT NULL,
primary key(IngID,ItemID),
FOREIGN KEY(IngID) REFERENCES F15_10_Ingredient(IngID) on delete cascade,
FOREIGN KEY(ItemID) REFERENCES F15_10_Item(ItemID) on delete cascade
);

-----------------------create table SUPPLIES-------------------------
CREATE TABLE F15_10_Supplies     
(
IngID int,
SupID int,
Qty int,
primary key(IngID,SupID),
FOREIGN KEY(IngID) REFERENCES F15_10_Ingredient(IngID) on delete cascade,
FOREIGN key(SupID) references F15_10_Supplier(SupID) on delete cascade
);

-----------------------create table ORDERDATE-------------------------
CREATE TABLE F15_10_OrderDate     
(
ItemID int,
CID int,
OrderDate date,
primary key(ItemID,CID,OrderDate),
FOREIGN KEY(ItemID) REFERENCES F15_10_Item(ItemID) on delete cascade,
FOREIGN key(CID) references F15_10_Customer(CID) on delete cascade
);

-----------------------create table LEAVE-------------------------
CREATE TABLE F15_10_Leave     
(
EmpID int,
FromDate date,
ToDate date,
primary key(EmpID,FromDate,ToDate),
FOREIGN KEY(EmpID) REFERENCES F15_10_Employee(EmpID) on delete cascade
);


-----------------------ALL TABLES CREATED-------------------------






