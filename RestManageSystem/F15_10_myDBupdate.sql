------------------------RESTAURANT MANAGEMENT SYSTEM------------------------
----------------------------Update the tables-------------------------------


---------------------------1.Update table Employee----------------------------
update F15_10_Employee
set salary=4000
where FName='Rajesh' and LName='Kumar';


-----------------------------2.Update table Item----------------------------
update F15_10_Item
set ItemCost=7
where ItemName='Butter Nan';

-----------------------3.Increase the item cost for most ordered dish----------
update F15_10_Item
set ItemCost=15
where ItemID IN (select ItemID 
                from(select i.ItemID,i.ItemName,count(*) as value
                    from F15_10_Orders o,F15_10_Item i
                    where o.ItemID=i.ItemID
                    group by(i.ItemID,i.ItemName)
                    order by value desc) 
                    where rownum=1) ;

-----------------------4.Delete least ordered dish-----------------------------
delete from F15_10_Item
where ItemID IN (select ItemID 
                from(select i.ItemID,i.ItemName,count(*) as value
                    from F15_10_Orders o,F15_10_Item i
                    where o.ItemID=i.ItemID
                    group by(i.ItemID,i.ItemName)
                    order by value) 
                    where rownum=1) ;

-----------------------5.Increase quantity for most ordered dish----------
update F15_10_ConsistsOf
set Quantity=40
where ItemID IN (select ItemID 
                from(select i.ItemID,i.ItemName,count(*) as value
                    from F15_10_Orders o,F15_10_Item i
                    where o.ItemID=i.ItemID
                    group by(i.ItemID,i.ItemName)
                    order by value desc) 
                    where rownum=1) ;

-----------------------6.Decrease quantity for least ordered dish----------
update F15_10_ConsistsOf
set Quantity=1
where ItemID IN (select ItemID 
                from(select i.ItemID,i.ItemName,count(*) as value
                    from F15_10_Orders o,F15_10_Item i
                    where o.ItemID=i.ItemID
                    group by(i.ItemID,i.ItemName)
                    order by value) 
                    where rownum=1) ;

------------------7.Delete Employee who appliedfor leave maximum number of times----------
delete from F15_10_Employee
where EmpID IN(select EmpID
from (select e.EmpID,e.FName,e.LName,count(*) as value
     from F15_10_Employee e,F15_10_Leave l
     where e.EmpID=l.EmpID
     group by(e.EmpID,e.FName,e.LName)
     order by value desc)
where rownum=1);

------------------8.Decrease the salary of The Employee on leave for more than 10 days----------
update F15_10_Employee
set salary=400
where EmpID IN(select EmpID
               from(select e.FName,e.LName,e.EmpID
                    from F15_10_Employee e,F15_10_Leave l
                    where e.EmpID=l.EmpID and ((l.ToDate-l.FromDate)>10)
                    order by e.FName asc)); 

------------------9.Decrease Supplier cost for Supplier supplying minimum quantity of ingredients----------
update F15_10_Supplier
set SupCost=200
where SupName IN (select SupName
                  from (select su.SupID,s.SupName,sum(su.qty) as totalsupply
                        from F15_10_Supplies su,F15_10_Supplier s
                        where su.SupID=s.SupID
                        group by(su.SupID,s.SupName)
                        order by totalsupply desc)
                  where rownum=1);


