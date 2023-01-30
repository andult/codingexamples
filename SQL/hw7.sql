-------------------------------------------------------------------------------
-- Homework 7
-- CST 363, Intro to Databases
-- The problems are based on the normalized CA candidate, contributor and contribution 
-- tables in file 'campaign-normal.sql';


-- 1. List the contributions to each candidate from contributors that have city of 'SALINAS'
-- Show candidate name, and total contributed amount (labeled as "total")
-- Order the output by total contributed amount largest to smallest.
-- The first row should be Rubio, Marco  44050.00
select candidate.name, sum(amount) as total 
from candidate join contribution using(cand_id) join contributor using(contbr_id)
where city = 'Salinas'
group by candidate.name
order by total desc;

-- 2. For each of the local zip codes, 
--    return a result with labels zip, cand_name, count 
--    zip is the first 5 digits of the zipcode, and count is the number of contributions from that zip code
--    Order by zip code (small to large) and number of contributions (large to small).
--    NOTE: In this and the following questions, 'local' means 
--    the zip codes that begin with 93933, 93901, 93905, 93955, or 93906.
--    Your result should be 
-- 93901 Clinton, Hillary Rodham 30
-- 93901 Sanders, Bernard 28
-- 93901 Rubio, Marco 25
-- 93901 Carson, Benjamin S. 15
-- 93901 Cruz, Rafael Edward 'Ted' 7
-- 93901 Fiorina, Carly 2
-- 93901 Kasich, John R. 2
-- 93905 Sanders, Bernard 7
-- 93905 Cruz, Rafael Edward 'Ted' 2
-- 93905 Clinton, Hillary Rodham 1
-- 93905 Huckabee, Mike 1
-- 93906 Sanders, Bernard 28
-- 93906 Rubio, Marco 12
-- 93906 Clinton, Hillary Rodham 9
-- 93906 Cruz, Rafael Edward 'Ted' 6
-- 93906 Carson, Benjamin S. 2
-- 93906 Paul, Rand 2
-- 93906 Walker, Scott 2
-- 93933 Sanders, Bernard 48
-- 93933 Cruz, Rafael Edward 'Ted' 27
-- 93933 Clinton, Hillary Rodham 10
-- 93933 Carson, Benjamin S. 3
-- 93955 Sanders, Bernard 56
-- 93955 Clinton, Hillary Rodham 12
-- 93955 Paul, Rand 6
-- 93955 Carson, Benjamin S. 5
-- 93955 Cruz, Rafael Edward 'Ted' 4
select left(zip,5) as zip, candidate.name, count(contb_id) as count
from candidate join contribution using(cand_id) join contributor using(contbr_id)
where left(zip,5) = '93933' or left(zip,5) = '93901' or left(zip,5) = '93905' or left(zip,5) = '93955' or left(zip,5) = '93906'
group by candidate.name, left(zip,5)
order by zip, count desc, candidate.name;

-- 3. Who are the top 10 local contributors?  
-- return the contbr_name and label the total amount as "total"
-- sort by total in descending order, then name ascending.
-- use "limit 10" to get only the top 10 rows.
-- The result should be 
-- TAYLOR, KATHRYN HAGLER MRS. 8100.00
-- DREVER, ROBYN L. MRS. 5400.00
-- TAYLOR, KYLE MR. 5400.00
-- DREVER, MARK J. MR. 4800.00
-- HEVIA, CLAUDIA 2700.00
-- LEWIS, ANDRE 2700.00
-- PATEL, BHAVIN 2700.00
-- TAYLOR, STEVEN BRUCE MR. 2700.00
-- LESIKAR, DAVID 900.00
-- SCHAEFER, KARL 797.35
select contributor.name, sum(amount) as label
from contributor join contribution using(contbr_id)
where left(zip,5) = '93933' or left(zip,5) = '93901' or left(zip,5) = '93905' or left(zip,5) = '93955' or left(zip,5) = '93906'
group by name
order by label desc, name
limit 10;

-- 4. Show date and amount for all contributions from 'BATTS, ERIC'.
-- Order by amount largest to smaller and then by date.
-- The first row should be 30-JAN-16 125.00.  
-- The last row 30-JUN-15 2.00
select date, amount
from contributor join contribution using(contbr_id)
where name = 'BATTS, ERIC'
order by amount desc, date;

-- 5. On average, how many contributions did each contributor make?
-- Divide the number of contributions by the number of contributors.
-- Give a single number, rounded to one digit and labeled "average"
-- The correct answer is 2.9
with t1 as (select count(name) as name from contributor),
	t2 as (select count(*) as contbr from contribution)
select round(contbr/name, 1) as average
from t1 join t2course;
