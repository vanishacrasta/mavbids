import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.*;
public class Restaurant {
static Connection connection = null;
static Statement stmt=null;
public int validateemployee(String username,String password)
{//Employee login validation
int flag=0;
try{
String des="Supervisor";
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Employee");
while(rs.next())
{
if(rs.getString("FName").equals(username))
{

if(rs.getString("EmpID").equals(password))
{
flag=2;
if(rs.getString("Designation").equals(des))
flag=1;
}
}
}
stmt.close();

}
catch(SQLException e){}
return flag;
}
public int validatecustomer(String username,String password)
{//Customer login validation
int f=0;
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Customer");
while(rs.next())
{
if(rs.getString("FirstName").equals(username))
{

if(rs.getString("CID").equals(password))
{
f=1;
}
}
}
stmt.close();

}
catch(SQLException e){}
return f;
}

public static void DBconnection() {
//connnecting to Oracle
try {
Class.forName("oracle.jdbc.driver.OracleDriver");
    }
catch (ClassNotFoundException e) {
System.out.println("\nDriver not found!!");	
e.printStackTrace();
return;
                                   }
//System.out.println("Oracle JDBC Driver Registered!");
try {
 //netID and oracle password
connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:cse1", "vxc2903", "uta2015");
    }
catch (SQLException e) {
System.out.println("Connection Failed! Check output console");
e.printStackTrace();
return;
                       }
if (connection != null) {
//System.out.println("Connection Successfull!!");
                        }
else {
System.out.println("Failed to make connection!");
     }
} 
public static void main(String args[]) throws IOException
{
String input8=" ";
int c10=0;
String c6=" ";
int c7=0;
String c4=" ";
int c5=0;
int c=0;
int c3=0;
String input="";
Restaurant r = new Restaurant();
InputStreamReader is = new InputStreamReader(System.in);
BufferedReader br=new BufferedReader(is);
System.out.println("\n***********RESTAURANT MANAGEMENT SYSTEM**********");
System.out.println("\n***********VANISHA CRASTA , MANAS RAM BAPATLA**********");
System.out.println("\n***********TEAM 10 CSE5330 FALL 2015**********");
System.out.println("\n PLEASE SELECT ONE OF THE FOLLOWING OPTIONS : ");
System.out.println("\n\n1.EMPLOYEE LOGIN");
System.out.println("\n\n2.CUSTOMER LOGIN");
System.out.println("\n\n3.NEW CUSTOMER REGISTRATION");
System.out.println("\n\n4.EXIT");
System.out.println("\n\nPLEASE ENTER YOUR CHOICE : ");
input=br.readLine();
c=Integer.parseInt(input);
try{
switch(c){
case 1:{
        String username1,password1;
        int a=-1;
        String input1=" ";
        int c1=0; 
        BufferedReader br1=new BufferedReader(new
        InputStreamReader(System.in));
        System.out.println("\nENTER THE USERNAME ");
        username1=br1.readLine();
        System.out.println("\nENTER THE PASSWORD ");
        password1=br1.readLine();
        r.DBconnection();
        a=r.validateemployee(username1,password1);
        if(a==1)
        {//functionalities for the supervisor
        System.out.println("\n~~~~~~~WELCOME "+username1+"~~~~~~~"); 
do{
String c2=" ";
 c3=0;
System.out.println("\nFind for this month : ");
System.out.println("\n1.Most ordered dish");
System.out.println("\n2.Least ordered dish");
System.out.println("\n3.Most crowded day");
System.out.println("\n4.Most frequent customer");
System.out.println("\n5.Most profitable day");
System.out.println("\n6.Total expenditure");
System.out.println("\n7.Total income");
System.out.println("\n8.Profit");
System.out.println("\n9.Employee who have applied for leave");
System.out.println("\n10.Employee on leave for more than 10 days");
System.out.println("\n11.Supplier supplying maximmum number of ingredients");
System.out.println("\n12.All customer details ");
System.out.println("\n13.All employee details ");
System.out.println("\n14.Add new employee");
System.out.println("\n15.Delete employee");
System.out.println("\n16.Update Employee Salary ");
System.out.println("\n17.Exit ");
System.out.println("\n\nEnter your choice : ");
input1=br1.readLine();
c1=Integer.parseInt(input1);
switch(c1){
case 1:{
r.DBconnection();
System.out.println("\n\nThe most ordered dish of this month is :");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select ItemName,number_of_times_ordered from (select i.ItemID,i.ItemName,count(*) as number_of_times_ordered from F15_10_Orders o,F15_10_Item i where o.ItemID=i.ItemID group by(i.ItemID,i.ItemName)order by number_of_times_ordered desc) where rownum=1");
 while(rs.next())          {System.out.println("\n"+rs.getString("ItemName")+" "+"number of times ordered : "+rs.getString("number_of_times_ordered"));
        }
        } 
         
               catch(SQLException e)
               {
               }
               stmt.close();
        }
System.out.println("Enter 1 to continue");
BufferedReader br5=new BufferedReader(new
InputStreamReader(System.in));
c2=br5.readLine();
c3=Integer.parseInt(c2);
  
               break;
case 2:{
r.DBconnection();
System.out.println("\n\nThe least ordered dish of this month is :");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select ItemName,number_of_times_ordered from (select i.ItemID,i.ItemName,count(*) as number_of_times_ordered from F15_10_Orders o,F15_10_Item i where o.ItemID=i.ItemID group by(i.ItemID,i.ItemName)order by number_of_times_ordered) where rownum=1");
while(rs.next())          {System.out.println("\n"+rs.getString("ItemName")+" "+"number of times ordered : " +rs.getString("number_of_times_ordered"));
        }
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br6=new BufferedReader(new
InputStreamReader(System.in));
c2=br6.readLine();
c3=Integer.parseInt(c2);
break;
case 3:{
r.DBconnection();
System.out.println("\n\nThe most crowded day of the month is :");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select OrderDate from (select OrderDate,count(*) as value from F15_10_OrderDate group by(OrderDate) order by value desc) where rownum=1");
while(rs.next())
{
System.out.println("\n"+rs.getString("OrderDate"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br7=new BufferedReader(new
InputStreamReader(System.in));
c2=br7.readLine();
c3=Integer.parseInt(c2);
break;
case 4:{
r.DBconnection();
System.out.println("\n\nThe most frequent customer of the month is : ");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select FirstName,LastName,number_of_times_visited from (select c.FirstName,c.LastName,count(*) as number_of_times_visited from F15_10_Customer c,F15_10_OrderDate o where c.CID=o.CID group by(c.FirstName,c.LastName) order by number_of_times_visited desc) where rownum=1");
while(rs.next())
{
System.out.println("\n"+rs.getString("FirstName")+" "+rs.getString("LastName"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br8=new BufferedReader(new
InputStreamReader(System.in));
c2=br8.readLine();
c3=Integer.parseInt(c2);
break;
case 5:{
r.DBconnection();
System.out.println("\n\nThe most profitable day of the month is :");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select OrderDate,totalearned from (select o.OrderDate,sum(i.ItemCost) as totalearned from F15_10_OrderDate o,F15_10_Item i where o.ItemID=i.ItemID group by (OrderDate) order by totalearned desc) where rownum=1");
while(rs.next())
{
System.out.println("\n"+rs.getString("OrderDate")+" "+"The total amount earned  is : $"+rs.getString("totalearned"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br9=new BufferedReader(new
InputStreamReader(System.in));
c2=br9.readLine();
c3=Integer.parseInt(c2);
break;
case 6:{
r.DBconnection();
System.out.println("\n\nThe total expenditure of the month is :");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select sum(value) as expenditure from ((select sum(SupCost) as value from F15_10_Supplier) union (select sum(Salary) from F15_10_Employee))");
while(rs.next())
{
System.out.println("\n$"+rs.getString("expenditure"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br10=new BufferedReader(new
InputStreamReader(System.in));
c2=br10.readLine();
c3=Integer.parseInt(c2);
break;
case 7:{
r.DBconnection();
System.out.println("\nThe total income for this month is : ");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select sum(i.ItemCost*o.Quantity) as totalamount from F15_10_Orders o,F15_10_Item i where o.ItemID=i.ItemID");
while(rs.next())
{
System.out.println("\n$"+rs.getString("totalamount"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br11=new BufferedReader(new
InputStreamReader(System.in));
c2=br11.readLine();
c3=Integer.parseInt(c2);
break;
case 8:{
r.DBconnection();
System.out.println("\n\nThe profit of the month is : ");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select totalamount-expenditure as profit from (select sum(value) as expenditure from ((select sum(SupCost) as value from F15_10_Supplier) union (select sum(Salary) from F15_10_Employee))),(select sum(i.ItemCost*o.Quantity) as totalamount from F15_10_Orders o,F15_10_Item i where o.ItemID=i.ItemID)");
while(rs.next())
{
System.out.println("\n$"+rs.getString("profit"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br12=new BufferedReader(new
InputStreamReader(System.in));
c2=br12.readLine();
c3=Integer.parseInt(c2);
break;
case 9:{
r.DBconnection();
System.out.println("\n\nThe Employees who have applied for leave");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select e.FName,e.LName,l.FromDate,l.ToDate from F15_10_Employee e,F15_10_Leave l where e.EmpID=l.EmpID order by FName");
while(rs.next())
{
System.out.println("\n"+rs.getString("FName")+" "+rs.getString("LName")+" "+rs.getString("FromDate")+" "+rs.getString("ToDate"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br13=new BufferedReader(new
InputStreamReader(System.in));
c2=br13.readLine();
c3=Integer.parseInt(c2);
break;
case 10:{
r.DBconnection();
System.out.println("\n\nThe Employee on leave for more than 10 days is:");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select DISTINCT e.FName,e.LName from F15_10_Employee e,F15_10_Leave l where e.EmpID=l.EmpID and ((l.ToDate-l.FromDate)>10) order by e.FName");
while(rs.next())
{
System.out.println("\n"+rs.getString("FName")+" "+rs.getString("LName"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br14=new BufferedReader(new
InputStreamReader(System.in));
c2=br14.readLine();
c3=Integer.parseInt(c2);
break;
case 11:{
r.DBconnection();
System.out.println("\n\nThe supplier supplying maximum quantity of ingredients is :");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from (select su.SupID,s.SupName,sum(su.qty) as totalsupply from F15_10_Supplies su,F15_10_Supplier s where su.SupID=s.SupID group by(su.SupID,s.SupName) order by totalsupply desc) where rownum=1");
while(rs.next())
{
System.out.println("\n"+rs.getString("SupName")+" " +"total number of quatities supplied is:"+rs.getString("totalsupply"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br15=new BufferedReader(new
InputStreamReader(System.in));
c2=br15.readLine();
c3=Integer.parseInt(c2);
break;
case 12:{
r.DBconnection();
System.out.println("\n\nThe current customers are : ");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Customer");
while(rs.next())
{
System.out.println("\n"+rs.getString("CID")+" "+rs.getString("FirstName")+" "+rs.getString("LastName")+" "+rs.getString("Address")+" "+rs.getString("Pnumber"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br16=new BufferedReader(new
InputStreamReader(System.in));
c2=br16.readLine();
c3=Integer.parseInt(c2);
break;
case 13:{

r.DBconnection();
System.out.println("\nThe current employees are : ");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Employee");
System.out.println("\nEmployee ID FName LName Phonenumber Salary Designation");
while(rs.next()){
System.out.println("\n"+rs.getString("EmpID")+"         "+rs.getString("FName")+"   "+rs.getString("LName")+"   "+rs.getString("Phonenumber")+"  "+rs.getString("Salary")+"  "+rs.getString("Designation"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br17=new BufferedReader(new
InputStreamReader(System.in));
c2=br17.readLine();
c3=Integer.parseInt(c2);
break;
case 14:{
int flag1=0;
String EID=" ";
String Esal=" ";
String Ephone=" ";
int id=0;
String name1=" ";
String name2=" ";
int phone=0;
int sal=0;
String desig=" ";
BufferedReader br2=new BufferedReader(new
InputStreamReader(System.in));
r.DBconnection();
System.out.println("\nEnter the details of the Employee");
System.out.println("\nEmployee ID : ");
EID=br2.readLine();
id=Integer.parseInt(EID);
System.out.println("\nEmployee First Name : ");
name1=br2.readLine();
System.out.println("\nEmployee Last Name : ");
name2=br2.readLine();
System.out.println("\nEmployee phone number : ");
Ephone=br2.readLine();
phone=Integer.parseInt(Ephone);
System.out.println("\nEmployee salary :");
Esal=br2.readLine();
sal=Integer.parseInt(Esal);
System.out.println("\nEmployee designation : ");
desig=br2.readLine();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Employee");
while(rs.next()){
if(rs.getString("EmpID").equals(EID)){
System.out.println("\nError!!..Employee ID already exists");
flag1=1;
}
}
if(flag1==0){
System.out.println("\ninserting ");
r.DBconnection();
stmt=connection.createStatement();
ResultSet rs1=stmt.executeQuery("insert into F15_10_Employee (EmpID,FName,LName,Phonenumber,Salary,Designation) values('"+EID+"','"+name1+"','"+name2+"','"+Ephone+"','"+Esal+"','"+desig+"')");
System.out.println("\nEmployee details added");
}

}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br18=new BufferedReader(new
InputStreamReader(System.in));
c2=br18.readLine();
c3=Integer.parseInt(c2);
break;
case 15:{
int flag2=0;
String EmID=" ";
BufferedReader br3=new BufferedReader(new
InputStreamReader(System.in));
r.DBconnection();
System.out.println("\n\nEnter the Employee ID of the Employee whose details have to deleted");
EmID=br3.readLine();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Employee");
while(rs.next()){
if(rs.getString("EmpID").equals(EmID))
flag2=1;
}
if(flag2==0)
System.out.println("\nThe employee ID does not exists");
if(flag2==1){
stmt=connection.createStatement();
ResultSet rs1=stmt.executeQuery("delete from F15_10_Employee where EmpID='"+EmID+"'");
System.out.println("\nEmployee details deleted");
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br19=new BufferedReader(new
InputStreamReader(System.in));
c2=br19.readLine();
c3=Integer.parseInt(c2);

break;
case 16:{
int flag3=0;
r.DBconnection();
System.out.println("\nEnter the employee ID of the employee");
String id1=" ";
String s=" ";
BufferedReader br4=new BufferedReader(new
InputStreamReader(System.in));
id1=br4.readLine();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Employee");
while(rs.next()){
if(rs.getString("EmpID").equals(id1))
flag3=1;
}
if(flag3==0)
System.out.println("\nthe employee does not exists");
if(flag3==1)
{
stmt=connection.createStatement();
System.out.println("\nEnter the new salary");
s=br4.readLine();
ResultSet rs1=stmt.executeQuery("update F15_10_Employee set Salary='"+s+"' where EmpID='"+id1+"'");
System.out.println("\nSalary has been updated");
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br21=new BufferedReader(new
InputStreamReader(System.in));
c2=br21.readLine();
c3=Integer.parseInt(c2);

break;
case 17:break;
default:System.out.println("\nWrong choice..Try again!!");
      System.out.println("Enter 1 to continue");
BufferedReader br22=new BufferedReader(new
InputStreamReader(System.in));
c2=br22.readLine();
c3=Integer.parseInt(c2);

break;
}
}while(c3==1);
}
if(a==2)
{
do{
c7=0;
//functionalities for employee other than supervisor
System.out.println("\n~~~~~~~~~~~WELCOME "+username1+"~~~~~~~~~~");
System.out.println("\nSelect an option");
System.out.println("\n1.Item Menu");
System.out.println("\n2.Item Ingredient details");
System.out.println("\n3.Customer details");
System.out.println("\n4.Leave application");
System.out.println("\n5.Exit");
System.out.println("\nEnter you choice :");
BufferedReader br23=new BufferedReader(new
InputStreamReader(System.in));
c4=br23.readLine();
c5=Integer.parseInt(c4);
switch(c5)
{
case 1:{
System.out.println("\n\nItem Menu");
r.DBconnection();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Item");
System.out.println("\nItemID   ItemName   ItemType   ItemCategory   ItemCost");
while(rs.next()){
System.out.println("\n"+rs.getString("ItemID")+" "+rs.getString("ItemName")+" "+rs.getString("ItemType")+" "+rs.getString("ItemCat")+" "+rs.getString("ItemCost"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("\nEnter 1 to continue");
BufferedReader br25=new BufferedReader(new
InputStreamReader(System.in));
c6=br25.readLine();
c7=Integer.parseInt(c6);
break;
case 2:{
System.out.println("\n\nThe ingredients used by different items are :");
r.DBconnection();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select i.ItemName,ing.IngName,c.Quantity from F15_10_Item i,F15_10_ConsistsOf c,F15_10_Ingredient ing where i.ItemID=c.ItemID and c.IngID=ing.IngID order by ItemName");
while(rs.next()){
System.out.println("\n"+rs.getString("ItemName")+" "+rs.getString("IngName")+" "+rs.getString("Quantity"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("\nEnter 1 to continue");
BufferedReader br26=new BufferedReader(new
InputStreamReader(System.in));
c6=br26.readLine();
c7=Integer.parseInt(c6);

break;
case 3:{
System.out.println("The current customers are: ");
r.DBconnection();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Customer");
while(rs.next())
{
System.out.println("\n"+rs.getString("CID")+" "+rs.getString("FirstName")+" "+rs.getString("LastName")+" "+rs.getString("Address")+" "+rs.getString("Pnumber"));
}
}
catch(SQLException e){}
stmt.close();
}
System.out.println("\nEnter 1 to continue");
BufferedReader br27=new BufferedReader(new
InputStreamReader(System.in));
c6=br27.readLine();
c7=Integer.parseInt(c6);

break;
case 4:{
String input2="  ";
String input3=" ";
System.out.println("\n\n*****LEAVE APPLICATION******");
r.DBconnection();
BufferedReader br24=new BufferedReader(new
InputStreamReader(System.in));
System.out.println("\nEnter the Start Date in the format 'dd-month-yy'");
input2=br24.readLine();
System.out.println("\nEnter the Last Date in the format 'dd-month-yy'");
input3=br24.readLine();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("insert into F15_10_Leave (EmpID,FromDate,ToDate) values('"+password1+"','"+input2+"','"+input3+"')");
System.out.println("\nYour leave application has been accepted");
}
catch(SQLException e){}
stmt.close();
}
System.out.println("\nEnter 1 to continue");
BufferedReader br28=new BufferedReader(new
InputStreamReader(System.in));
c6=br28.readLine();
c7=Integer.parseInt(c6);
break;
case 5:break;
default:System.out.println("\nWrong choice...Try again!");
System.out.println("\nEnter 1 to continue");
BufferedReader br29=new BufferedReader(new
InputStreamReader(System.in));
c6=br29.readLine();
c7=Integer.parseInt(c6);

break;
}
}while(c7==1);
}
else if(a==-1)
{
System.out.println("\n\nEMPLOYEE NOT FOUND!! try again");
}
}
break;
case 2:{
int b=-1;
String username2=" ";
String password2=" ";
BufferedReader br30=new BufferedReader(new
InputStreamReader(System.in));
System.out.println("\nEnter the username");
username2=br30.readLine();
System.out.println("\nEnter the password");
password2=br30.readLine();
r.DBconnection();
b=r.validatecustomer(username2,password2);
if(b==1)
{
String input4=" ";
int c8=0;
BufferedReader br31=new BufferedReader(new
InputStreamReader(System.in));
//menu for customer
System.out.println("\n~~~~~~~~~WELCOME "+username2+"~~~~~~~~~");
try{
do{
c10=0;
System.out.println("\nPlease choose one of the following");
System.out.println("\n1.Item Menu");
System.out.println("\n2.Search by Item name");
System.out.println("\n3.Order");
System.out.println("\n4.exit");
System.out.println("\n\nEnter your choice : ");
input4=br31.readLine();
c8=Integer.parseInt(input4);

switch(c8)
{
case 1:{
r.DBconnection();
System.out.println("\n\n***********************************");
System.out.println("\n  *          ITEM MENU              *");
System.out.println("\n  ***********************************");
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Item order by ItemType");
System.out.println("ITEMNAME  ITEMCAT  ITEMTYPE   ITEMCOST");
while(rs.next()){
System.out.println("\n"+" "+rs.getString("ItemName")+" "+rs.getString("ItemCat")+" "+rs.getString("ItemType")+" "+rs.getString("ItemCost"));
}
System.out.println("\n\n***********************************");
}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br35=new BufferedReader(new
InputStreamReader(System.in));
input8=br35.readLine();
c10=Integer.parseInt(input8);
break;
case 2:{
String Item=" ";
r.DBconnection();
BufferedReader br32=new BufferedReader(new
InputStreamReader(System.in));
System.out.println("\n\nEnter the Item Name");
Item=br32.readLine();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Item");
while(rs.next())
{
if(rs.getString("ItemName").equals(Item))
{
System.out.println("\nItemName : "+" "+rs.getString("ItemName")+"\nItem Category : "+" "+rs.getString("ItemCat")+"\nItem Type : "+" "+rs.getString("ItemType")+"\nItem Cost : $"+" "+rs.getString("ItemCost"));
}
}
}
catch(SQLException e){}
}
System.out.println("Enter 1 to continue");
BufferedReader br36=new BufferedReader(new
InputStreamReader(System.in));
input8=br36.readLine();
c10=Integer.parseInt(input8);
break;
case 3:{
String input7=" ";
int c9=0;
String ItemOrder=" ";
String ItemQty=" ";
int sum=0;
int cost=0;
int iqty=0;

BufferedReader br33=new BufferedReader(new
InputStreamReader(System.in));
System.out.println("\nPlace your order here : ");
try{
do{
System.out.println("\nEnter the ItemName");
ItemOrder=br33.readLine();
System.out.println("\nEnter the Quantity"); 
ItemQty=br33.readLine();
iqty=Integer.parseInt(ItemQty);
r.DBconnection();
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select ItemCost,ItemID from F15_10_Item where ItemName='"+ItemOrder+"'");
//Every ordered item needs to be added to the orders table but we //have'nt done it since our description does not include online //ordering
while(rs.next()){
cost=Integer.parseInt(rs.getString("ItemCost"));
sum=sum+(cost*iqty);
stmt=connection.createStatement();
}
stmt=connection.createStatement();
System.out.println("\n\nEnter 1 to order another item");
BufferedReader br34=new BufferedReader(new
InputStreamReader(System.in));
input7=br34.readLine();
c9=Integer.parseInt(input7);
}while(c9==1);
System.out.println("\n\nYOUR BILL : $"+sum);

}
catch(SQLException e){}
stmt.close();
}
System.out.println("Enter 1 to continue");
BufferedReader br37=new BufferedReader(new
InputStreamReader(System.in));
input8=br37.readLine();
c10=Integer.parseInt(input8);
break;
case 4:break;
default:System.out.println("\nWrong Choice...Try again");
        System.out.println("Enter 1 to continue");
BufferedReader br38=new BufferedReader(new
InputStreamReader(System.in));
input8=br38.readLine();
c10=Integer.parseInt(input8);
break;
}
}while(c10==1);
}
catch(Exception e){}

}
else
{
System.out.println("\nInvalid username/password");
}
}
break;
case 3:{
//customer registration
int f1=0;
String cid=" ";
String fname=" ";
String lname=" ";
String add=" ";
String pno=" ";
BufferedReader br40=new BufferedReader(new
InputStreamReader(System.in));
System.out.println("\n\n********CUSTOMER REGISTRATION*********");
System.out.println("\n\nPlease enter the following details");
System.out.println("\ncustomer ID : ");
cid=br40.readLine();
System.out.println("\ncustomer First Name :");
fname=br40.readLine();
System.out.println("\ncustomer Last Name :");
lname=br40.readLine();
System.out.println("\ncustomer Address : ");
add=br40.readLine();
System.out.println("\ncustomer contact : ");
pno=br40.readLine();
r.DBconnection();
try{
stmt=connection.createStatement();
ResultSet rs=stmt.executeQuery("select * from F15_10_Customer");
while(rs.next()){
if(rs.getString("CID").equals(cid))
{
f1=1;
System.out.println("\nEmployee ID already exists...");
}
}
if(f1==0){
ResultSet rs1=stmt.executeQuery("insert into F15_10_Customer(CID,FirstName,LastName,Address,Pnumber) values ('"+cid+"','"+fname+"','"+lname+"','"+add+"','"+pno+"')");
System.out.println("\n\nREGISTRATION SUCCESSFULL!!");
}
}
catch(SQLException e){}
stmt.close();
}
break;
case 4:break;
default:System.out.println("\nWrong choice..Try again");
break;
}

}
catch(Exception e){}
}
}

