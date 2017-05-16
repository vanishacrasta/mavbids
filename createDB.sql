create table musician (
   musianid   varchar(10) primary key,
   name  varchar(32),
   genre    varchar(10),
   
);

create table performance (
  perfomanceId   varchar(13) primary key,
  month-year date
  musicianid	  varchar(10) references musician (musianid)
);
