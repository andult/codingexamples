-- homework 2.sql
-- use courses-small.sql to create tables.
-- Unless specified otherwise, the result should be ordered by the first column of the result.

-- 1. show the name and salary of all instructors sorted by name.
select name, salary
from instructor
order by name;

-- 2. show all columns for instructors in the 'Comp. Sci.' department in order by name
select *
from instructor
where dept_name = 'Comp. Sci.'
order by name;

-- 3. show name, salary, department for instructors with salaries less than $50,000.
select name, salary, dept_name
from instructor
where salary < 50000;

-- 4. show the student name, major department and total credits for 
--    students with at least 90 credits.  The list should be in order by total credits.
select name, dept_name, tot_cred
from student
where tot_cred >= 90
order by tot_cred;

-- 5. show the student ID and name for students who are majoring in  
--    'Elec. Eng.' or 'Comp. Sci.'  and have at least 90 credits 
select ID, name
from student
where dept_name = 'Elec. Eng.' or dept_name = 'Comp. Sci.'
and tot_cred >= 90;

-- 6. insert a new Student with an ID 12399, Fred Brooks, majoring in Comp. Sci., total credits 0.
insert into student values (12399, 'Fred Brooks', 'Comp. Sci.', 0);

-- 7. increase the total credits by 8 for student ID 19991
update student
set tot_cred = tot_cred+8
where ID = 19991;

-- 8. change the tot_cred for student ID 12399 to 100.
update student
set tot_cred = 100
where ID = 12399;

-- 9. show all columns of the student table in order by name
select * from student
order by name;

-- 10.  Give all faculty a 4% increase in salary.
update instructor
set salary = salary + (salary*.04);

-- 11.  Give all faculty in the Physics department a $3,500 salary increase.
update instructor
set salary = salary + 3500
where dept_name = 'Physics';

-- 12.  show the  ID, name and salary for all instructors in decreasing order by salary
select ID, name, salary
from instructor
order by salary DESC;

-- 13.  try to delete the course with course_id 'PHY-101'.  
delete from course
where course_id = 'PHY-101';

-- 14.  Why does the delete fail?
-- It fails because you cannot delete or update a parent row due to foreign key constraints.

-- 15.  Delete the course 'CS-315'
delete from course
where course_id = 'CS-315';

-- 16.  Show a list of all course_id's.
select course_id
from course;

-- 17.  Show all the student majors without duplicates.
--      Label the dept_name column as 'major' 
select distinct dept_name as major
from student;

-- 18.  create a table "company" with columns id, name and ceo. 
-- Column "id" is the primary key.
-- insert the following data 
--    id   name          ceo
--    ACF  Acme Finance  Mike Dempsey
--    TCA  Tara Capital  Ava Newton
--    ALB  Albritton     Lena Dollar
create table company
	(ID		varchar(3),
	 name	varchar(20),
	 ceo	varchar(20),
	 primary key (ID)
	);
insert into company values ('ACF', 'Acme Finance', 'Mike Dempsey');
insert into company values ('TCA', 'Tara Capital', 'Ava Newton');
insert into company values ('ALB', 'Albritton', 'Lena Dollar');

-- create a table "security" with columns id, name, type.
-- Column "id" is the primary key.
-- insert the following data
--    id    name                type
--    AE    Abhi Engineering    Stock
--    BH    Blues Health        Stock
--    CM    County Municipality Bond
--    DU    Downtown Utlity     Bond
--    EM    Emmitt Machines     Stock
create table security
	(ID		varchar(2),
	 name	varchar(20),
	 type	varchar(7),
	 primary key (ID)
	);
insert into security values ('AE', 'Abhi Engineering', 'Stock');
insert into security values ('BH', 'Blues Health', 'Stock');
insert into security values ('CM', 'County Municipality', 'Bond');
insert into security values ('DU', 'Downtown Utility', 'Bond');
insert into security values ('EM', 'Emmitt Machines', 'Stock');
select * from security;

-- create a table "funds"   
-- Make "fundid" the primary key.  
-- Make "companyid" a foreign key.
-- insert the following data
-- CompanyID  InceptionDate   FundID Name
--    ACF      2005-01-01     BG     Big Growth
--    ACF      2006-01-01     SG     Steady Growth
--    TCA      2005-01-01     LF     Tiger Fund
--    TCA      2006-01-01     OF     Owl Fund
--    ALB      2005-01-01     JU     Jupiter
--    ALB      2006-01-01     SA     Saturn
create table funds 
	(CompanyID	varchar(3),
     InceptionDate date,
     FundID		varchar(2),
     Name		varchar(20),
     primary key (FundID),
     foreign key (CompanyID) references company(ID)
	);
insert into funds values ('ACF', '2005-01-01', 'BG', 'Big Growth');
insert into funds values ('ACF', '2006-01-01', 'SG', 'Steady Growth');
insert into funds values ('TCA', '2005-01-01', 'LF', 'Tiger Fund');
insert into funds values ('TCA', '2006-01-01', 'OF', 'Owl Fund');
insert into funds values ('ALB', '2005-01-01', 'JU', 'Jupiter');
insert into funds values ('ALB', '2006-01-01', 'SA', 'Saturn');

-- create a table holdings
-- Make fundid, securityid the primary key.
-- Make fundid a foreign key.
-- Make securityid a foreign key.
-- insert the following data  
--    fundID   securityID   quantity
--     BG       AE           500
--     BG       EM           300
--     SG       AE           300
--     SG       DU           300
--     LF       EM          1000
--     LF       BH          1000
--     OF       CM          1000
--     OF       DU          1000
--     JU       EM          2000
--     JU       DU          1000
--     SA       EM          1000
--     SA       DU          2000
create table holdings 
	(FundID		varchar(2),
     SecurityID varchar(2),
     Quantity	int,
     primary key (FundID, securityID),
     foreign key (FundID) references funds(FundID),
     foreign key (securityID) references security(ID)    
	);
insert into holdings values ('BG', 'AE', '500');
insert into holdings values ('BG', 'EM', '300');
insert into holdings values ('SG', 'AE', '300');
insert into holdings values ('SG', 'DU', '300');
insert into holdings values ('LF', 'EM', '1000');
insert into holdings values ('LF', 'BH', '1000');
insert into holdings values ('OF', 'CM', '1000');
insert into holdings values ('OF', 'DU', '1000');
insert into holdings values ('JU', 'EM', '2000');
insert into holdings values ('JU', 'DU', '1000');
insert into holdings values ('SA', 'EM', '1000');
insert into holdings values ('SA', 'DU', '2000');

-- 19.  alter the security table to add a column price with datatype numeric(7,2)
alter table security
add price numeric(7,2);

-- 20.  drop the tables. The foreign key constraints require that you drop the 
--   tables in a particular order.
drop table holdings, funds, security, company;