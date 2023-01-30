-- homework 3.sql
-- use courses-small.sql to create tables.
-- Unless specified otherwise, the result should be ordered by the first column of the result.

-- 1.  List the course id and title for courses that have 
--     "System" or "Computer" in their title.
select course_id, title
from course
where title like '%System%' or title like '%Computer%';

-- 2.  List the id and name of instructors whose name
--     start with the letter "S".  The list should in name order.
select ID, name
from instructor
where name like 'S%'
order by name;

-- 3.  Return a list of all courses with the department name and 
--     the 3 digit number of the course labeled as "course_number".
--     Use the string function RIGHT(course_id, 3) to extract the 
--     3 digit course number from course_id column.
--     The list should be in order by department name and course number. 
--     An example would be 
--     DEPT_NAME   COURSE_NUMBER
--     Biology     101 
select dept_name, RIGHT(course_id, 3) as course_number
from course
order by dept_name, course_number;

-- 4.  Use the BETWEEN predicate to show the student ID and name 
--     of students who have total credits in the range [50-90]
select id, name
from student
where tot_cred between 50 and 90;

-- 5. How many upper division courses are there?  An upper
--    division course is a course with the 3 digit number in the range [300-499].
--    Label the ouput column "count"
select count(right(course_id, 3)) as count
from course
where course_id like '%3%' or course_id like '4%';

-- 6. How many unique buildings are used to teach classes?  
--    Use the BUILDING column in the SECTION table.
--    Label the ouput column "count"
select count(distinct building) as count
from section;

-- 7.  show the instructor id, name and the title of courses the instructor taught. 
--     If an instructor taught a course multiple times, don't list duplicates.
--     Sort the result by title.
select distinct id, name, course.title 
from instructor join course using(dept_name)
order by course.title;

-- 8.  for each instructor show their ID, name, monthly salary (salary divided by 12 rounded to integer) 
--     labeled as "monthly" and salary divided by department budget expressed as percent rounded to an integer
--     labeled as "percent".
--     order by percent largest to smallest.
select id, name, round(salary/12,0) as monthly, round(salary/budget*100) as percent
from instructor natural join department;

-- 9.  List Comp. Sci. courses taught in Spring 2009.
--     Return columns dept_name, course_id, sec_id, name (of instructor), building, room_number, day_time
--     (column day_time is in the time_slot_f table)
--     JOIN tables section, course, teaches, instructor and time_slot_f 
--     Order the result by course_id. 
select dept_name, course_id, section.sec_id, name, building, room_number, day_time
from section join course using(course_id)
join teaches using(course_id)
join instructor using(dept_name)
join time_slot_f using(time_slot_id)
order by course_id;

-- 10. Which students have a null value for grade. 
--     Label the result with studentID, student (student name), course_id, instructor (instructor name) 
select student.id as studentID, student.name as student, course_id, instructor.name as instructor, grade
from student join course using(dept_name)
join takes using(course_id)
join instructor using(dept_name)
where grade is null;

-- 11. Count the number of null grades in the takes table.  Label the output "count"
select count(*) as count
from takes
where grade is null;

-- 12. What is the average of student grades? 
--     Join tables takes and grade_points to convert letter grade to number.
--     Round to 2 decimal points.  Your answer should be a single number labeled as "average".  
--     Result should be 3.11
select round(avg(points),2) as average
from takes natural join grade_points;
     
-- 13. What is the weighted average of student grades? 
--    Use course credits as the weighting factor.
--    Your result should be a single number.  Round to 2 decimal points.
--    If done correctly, the answer will be 3.09  Label the output as "w_average"
select round(sum(points*credits)/sum(credits),2) as w_average
from course natural join takes natural join grade_points;

-- 14. Find the min, max and average total credits of students.
--     Label the output columns "min_credits", 'max_credits", "avg_credits".
--     Use the ROUND function to return the average as an integer value.
select min(tot_cred) as min_credits, max(tot_cred) as max_credits, round(avg(tot_cred),0) as avg_credits
from student;
                           