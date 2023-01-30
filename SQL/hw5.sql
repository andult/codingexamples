-- homework 5.sql
-- The tables used in this exercise come from 'courses-small.sql';
-- Unless specified otherwise, the result should be ordered by the first column of the result.

-- 1.  Find the department(s)  with the most students. 
--     Return department name and the count of students labeled as "students" 
--     The answer is Biology has the most students with 13.
with t1 as (select dept_name, count(*) as student_count from student group by dept_name),
     t2 as (select max(student_count) as maxcount from t1)
select dept_name, student_count from t1 where student_count = (select maxcount from t2);

-- 2.  Find all students in the Comp. Sci. department 
--     with more than 90 credits (column "tot_cred" in student table).
--     Return the student ID and name.
--     Answer: there is one student  00128, Zhang
select id, name
from student
where dept_name='Comp. Sci.' and tot_cred > 90
order by id;

-- 3.  Find courses that have not been taken by any student Return the course_id.
--     Hint: use NOT EXISTS or NOT IN predicate. 
--     Answer: BIO-399 has not been taken by any students.
select course_id
from course
where course.course_id not in (select course_id from takes)
order by course_id;

-- 4.  Do #3 in another way that uses a join.
select course.course_id
from course left join takes on course.course_id=takes.course_id
where takes.course_id is null
order by course_id;

-- 5.  Find the courses taken by students from query #2.  
--     Return the course_id and title.  List each course only once. 
--     Use a subquery and your select statement from #2.
--     The answers will be CS-101 and CS-347
select distinct course_id, title
from course join takes using(course_id)
where takes.id in (select id
					from student
					where student.dept_name='Comp. Sci.' and tot_cred > 90)
order by course_id;

-- 6.  Find students who passed a 4 credit course (course.credits=4)
--     A passing grade is A+, A, A-, B+, B, B-, C+, C, C-.
--     Use a subquery. 
--     Return student ID and name ordered by student name.
--     The answer will have 8 students.
select distinct student.id, name
from student join takes using(id)
where grade != 'F' and grade is not null and course_id in
	(select course_id from course
		where credits = 4)
order by name;

-- 7.  Find the course(s) taken by the most students.  Return columns 
--     course_id, title,  enrollment (the count of students that have taken the course)
--     The answer is CS-101 with an enrollment of 7
with t1 as (select course_id, count(*) as cnt from takes group by course_id),
     t2 as (select max(cnt) as maxcnt from t1) 
 select t1.course_id, title, cnt as enrollment from t1 natural join  course where cnt = (select maxcnt from t2);    

-- 8.  create a view named "vcourse" showing columns course_id, title, credits, enrollment
--     If no students have taken the course, the enrollment should be 0.
create view vcourse as
	select course_id, title, credits, count(id) as enrollment
    from course left join takes using(course_id)
    group by course_id
    order by course_id;

-- 9. Use the view to display to the course(s) with highest enrollment.
--    Return course_id, title, enrollment 
--    Answer is same as #7.
with t1 as (select max(enrollment) as maxenroll from vcourse)
select course_id, title, enrollment 
from vcourse
where enrollment= (select maxenroll from t1);

-- 10. Update the tot_cred column in the student table by calculating the actual total credits 
--     based on a passing grade and the credits given for the course (course.credits column)
--     A passing grade is given in problem #4.
--     If the student has not taken any course, then the sum will be null.
--     Use the IFNULL function to set tot_cred to the value 0 instead of null.
--     complete the update statement.
update student 
set tot_cred =( select ifnull(sum(course.credits), 0)
				from takes natural join course 
                where takes.id = student.id and grade !='F' and grade is not null);

-- 11. Return id, name and tot_cred of all students ordered by id;
--     Number 10 is correct if Zhang has 7 total credits, Shankar has 14, Brandt has 3 and Snow has 0.
select id, name, tot_cred
from student
order by id;
     
-- 12.  List the instructor(s) (ID, name) who advise the most students
--      Answer:  Einstein, Katz and Kim  advise the most students.
with t1 as (select i_id, count(s_id) as cnt from advisor group by i_id),
	t2 as (select max(cnt) as maxstudents from t1)
select id, name
from instructor join t1 on instructor.id=t1.i_id
where cnt = (select maxstudents from t2)
order by id;

-- 13. List the course_id and title for courses that are offered both in Fall 2009 and in Spring 2010.
--     A correct answers shows that CS-101 is the only course offered both semesters.
select distinct course.course_id, course.title
from section join course using(course_id)
where semester='Fall' and year =2009 and course_id in 
		(select course_id
			from section
			where semester='Spring' and year=2010);

