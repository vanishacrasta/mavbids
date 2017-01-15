-----------------------RESTAURANT MANAGEMENT SYSTEM---------------------------
------------------------------SQL Queries-------------------------------------

--------------------1.The most ordered Item of the month----------------------
 select * from(select i.ItemID,i.ItemName,count(*) as value
                     from F15_10_Orders o,F15_10_Item i
                     where o.ItemID=i.ItemID
                     group by(i.ItemID,i.ItemName)
                      order by value desc) 
 where rownum=1;
--expected ouput :------
--ITEMID ITEMNAME             VALUE
------ --------------- ----------
--   101 Tomato Soup             10



--------------------2.The least ordered Item of the month----------------------
select * from(select i.ItemID,i.ItemName,count(*) as value
                     from F15_10_Orders o,F15_10_Item i
                     where o.ItemID=i.ItemID
                     group by(i.ItemID,i.ItemName)
                      order by value )
where rownum=1;
--expected ouput :------
--ITEMID ITEMNAME             VALUE
------ --------------- ----------
--   210 Chicken Tikka M          4
--              asala



--------------------3.The most crowded day of the month------------------------
select * from(select OrderDate,count(*) as value
              from F15_10_OrderDate
              group by(OrderDate)
              order by value desc)
where rownum=1;
--expected ouput :------
--ORDERDATE            VALUE
---------------- ----------
--01-NOV-15              129



------------------4.The most frequent customer of the month----------------------
select * from (select c.FirstName,c.LastName,count(*) as value
              from F15_10_Customer c,F15_10_OrderDate o
              where c.CID=o.CID
              group by(c.FirstName,c.LastName)
              order by value desc)
where rownum=1;
--expected ouput :------
--FIRSTNAME       LASTNAME             VALUE
--------------- --------------- ----------
--Abhay           Kulkarni               145




------------------5.The most profitable day of the month----------------------
select * from (select o.OrderDate,sum(i.ItemCost) as totalearned
              from F15_10_OrderDate o,F15_10_Item i
              where o.ItemID=i.ItemID
	      group by (OrderDate)
              order by totalearned desc)
where rownum=1;
--expected ouput :------
--ORDERDATE       TOTALEARNED
--------------- -----------
--01-NOV-15               865



------------------6.The total expenditure of the month------------------------
select sum (value) as expenditure
  from ((select sum(SupCost)  as value
        from F15_10_Supplier)
        union
        (select sum(Salary) 
         from F15_10_Employee));
--expected ouput :------
--EXPENDITURE
-------------
--        543



------------------7.The total amount earned for a month------------------------
select sum(i.ItemCost*o.Quantity) as totalamount
from F15_10_Orders o,F15_10_Item i
where o.ItemID=i.ItemID;
--expected ouput :------
--TOTALAMOUNT
-------------
--       1648


------------------8.The profit earned for the month-----------------------------
select totalamount - expenditure as profit
from (select sum(value) as expenditure
      from((select sum(SupCost)  as value
                from F15_10_Supplier)
                union
              (select sum(Salary) 
               from F15_10_Employee))),(select sum(i.ItemCost*o.Quantity) as totalamount
                                 from F15_10_Orders o,F15_10_Item i
                                 where o.ItemID=i.ItemID);
--expected ouput :------
 --  PROFIT
-----------
 --    1105



--------------9.The Employee who applied for leave maximum number of times------------
select *
from (select e.FName,e.LName,count(*) as value
     from F15_10_Employee e,F15_10_Leave l
     where e.EmpID=l.EmpID
     group by(e.FName,e.LName)
     order by value desc)
where rownum=1;
--expected ouput :------
--FNAME           LNAME                VALUE
----------------- --------------- ----------
--Rajesh          Kumar                    3


--------------10.The Employees on leave for more than 10 days------------------
select e.FName,e.LName
from F15_10_Employee e,F15_10_Leave l
where e.EmpID=l.EmpID and ((l.ToDate-l.FromDate)>10)
order by e.FName asc;
--expected ouput :------
--FNAME           LNAME
---------------- ---------------
--Neha            Kateel
--Sita            Ram



--------------11.The Supplier supplying maximum quantity of ingredients-------------
 select *
 from (select su.SupID,s.SupName,sum(su.qty) as totalsupply
       from F15_10_Supplies su,F15_10_Supplier s
       where su.SupID=s.SupID
       group by(su.SupID,s.SupName)
       order by totalsupply desc)
where rownum=1;
--expected ouput :------
---    SUPID SUPNAME         TOTALSUPPLY
------ --------------- -----------
---     30 Bombay Bazaar           125
            

                         




