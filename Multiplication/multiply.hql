drop table Mmatrix;
drop table Nmatrix;
drop table temptable;
create table Mmatrix(r int,c int,v double) row format delimited fields terminated by ',' stored as textfile;
create table Nmatrix(r int,c int,v double) row format delimited fields terminated by ',' stored as textfile;
load data local inpath '${hiveconf:M}' overwrite into table Mmatrix;
load data local inpath '${hiveconf:N}' overwrite into table Nmatrix;
create table temptable(r int,c int,product double);
INSERT INTO table temptable 
SELECT  mat1.r,mat2.c,SUM(mat1.v*mat2.v) as product
FROM Mmatrix as mat1 JOIN Nmatrix as mat2
ON mat1.c=mat2.r
GROUP BY mat1.r,mat2.c;
SELECT count(*),AVG(product)
FROM temptable;
