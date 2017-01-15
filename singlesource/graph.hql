drop table graph;
drop table nodeinitial;
drop table intermediate;
drop table distanceij;
drop table intermediate2;
drop table intermediate3;
drop table result;

create table graph(source int,distance int,destination int) row format delimited fields terminated by ',' stored as textfile;
load data local inpath '${hiveconf:G}' overwrite into table graph;
create table nodeinitial(source int,distance int);
create table intermediate(source int,distance int,destination int,idistance int);
create table distanceij(source int,distance int,destination int,idistance int,jdistance int);
create table intermediate2(destination int,jdistance int);
create table intermediate3(destination int,jdistance int);
create table result(destination int,jdistance int);

SELECT *
FROM graph 
ORDER BY destination;

INSERT OVERWRITE TABLE nodeinitial
SELECT distinct source,9999
FROM graph
WHERE source!=0
UNION
SELECT distinct destination,9999
FROM graph
WHERE destination!=0
UNION
SELECT distinct source,0
FROM graph
WHERE source=0;

SELECT *
FROM nodeinitial;

INSERT INTO intermediate
SELECT g.source,g.distance,g.destination,i.distance
FROM graph as g JOIN nodeinitial as i
ON (i.source=g.source);

INSERT INTO distanceij
SELECT i.source,i.distance,i.destination,i.idistance,j.distance
FROM intermediate as i JOIN nodeinitial as j
ON (j.source=i.destination);

INSERT OVERWRITE TABLE intermediate2
SELECT destination,
CASE WHEN (jdistance>(idistance + distance)) THEN (idistance+distance)
ELSE jdistance
END as jdistance
FROM distanceij;

INSERT OVERWRITE TABLE intermediate3
SELECT destination,MIN(jdistance) as min
FROM intermediate2 
GROUP BY destination;

INSERT OVERWRITE TABLE result
SELECT source,CASE WHEN (n.distance<i.jdistance) THEN n.distance
WHEN i.jdistance is NULL THEN n.distance ELSE i.jdistance
END 
FROM nodeinitial as n LEFT OUTER JOIN intermediate3 as i ON (n.source = i.destination);

INSERT OVERWRITE TABLE nodeinitial
SELECT destination,jdistance
FROM result;

INSERT INTO intermediate
SELECT g.source,g.distance,g.destination,i.distance
FROM graph as g JOIN nodeinitial as i
ON (i.source=g.source);

INSERT INTO distanceij
SELECT i.source,i.distance,i.destination,i.idistance,j.distance
FROM intermediate as i JOIN nodeinitial as j
ON (j.source=i.destination);

INSERT OVERWRITE TABLE intermediate2
SELECT destination,
CASE WHEN (jdistance>(idistance + distance)) THEN (idistance+distance)
ELSE jdistance
END as jdistance
FROM distanceij;

INSERT OVERWRITE TABLE intermediate3
SELECT destination,MIN(jdistance) as min
FROM intermediate2 
GROUP BY destination;

INSERT OVERWRITE TABLE result
SELECT source,CASE WHEN (n.distance<i.jdistance) THEN n.distance
WHEN i.jdistance is NULL THEN n.distance ELSE i.jdistance
END 
FROM nodeinitial as n LEFT OUTER JOIN intermediate3 as i ON (n.source = i.destination);

INSERT OVERWRITE TABLE nodeinitial
SELECT destination,jdistance
FROM result;

INSERT INTO intermediate
SELECT g.source,g.distance,g.destination,i.distance
FROM graph as g JOIN nodeinitial as i
ON (i.source=g.source);

INSERT INTO distanceij
SELECT i.source,i.distance,i.destination,i.idistance,j.distance
FROM intermediate as i JOIN nodeinitial as j
ON (j.source=i.destination);

INSERT OVERWRITE TABLE intermediate2
SELECT destination,
CASE WHEN (jdistance>(idistance + distance)) THEN (idistance+distance)
ELSE jdistance
END as jdistance
FROM distanceij;

INSERT OVERWRITE TABLE intermediate3
SELECT destination,MIN(jdistance) as min
FROM intermediate2 
GROUP BY destination;

INSERT OVERWRITE TABLE result
SELECT source,CASE WHEN (n.distance<i.jdistance) THEN n.distance
WHEN i.jdistance is NULL THEN n.distance ELSE i.jdistance
END 
FROM nodeinitial as n LEFT OUTER JOIN intermediate3 as i ON (n.source = i.destination);

INSERT OVERWRITE TABLE nodeinitial
SELECT destination,jdistance
FROM result;

INSERT INTO intermediate
SELECT g.source,g.distance,g.destination,i.distance
FROM graph as g JOIN nodeinitial as i
ON (i.source=g.source);

INSERT INTO distanceij
SELECT i.source,i.distance,i.destination,i.idistance,j.distance
FROM intermediate as i JOIN nodeinitial as j
ON (j.source=i.destination);

INSERT OVERWRITE TABLE intermediate2
SELECT destination,
CASE WHEN (jdistance>(idistance + distance)) THEN (idistance+distance)
ELSE jdistance
END as jdistance
FROM distanceij;

INSERT OVERWRITE TABLE intermediate3
SELECT destination,MIN(jdistance) as min
FROM intermediate2 
GROUP BY destination;

INSERT OVERWRITE TABLE result
SELECT source,CASE WHEN (n.distance<i.jdistance) THEN n.distance
WHEN i.jdistance is NULL THEN n.distance ELSE i.jdistance
END 
FROM nodeinitial as n LEFT OUTER JOIN intermediate3 as i ON (n.source = i.destination);

INSERT OVERWRITE TABLE nodeinitial
SELECT destination,jdistance
FROM result;

SELECT * 
FROM result;


