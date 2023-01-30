-- homework 4.sql
-- The tables used in this exercise come from 'courses-small.sql';
-- Unless specified otherwise, the result should be ordered by the first column of the result.

-- 1.  For the spring 2009 semester, show the department name
--     and number of students enrolled in courses from each department
--     Label the number of students as "count". Order the result by 
--     department name.
select dept_name, count(name) as count
from student join takes using(id) -- join course using(course_id)
where takes.semester = 'spring' and year = '2009'
group by dept_name
order by dept_name;

select dept_name, count(distinct name) as count
from student join takes using(id) -- join course using(course_id)
where takes.semester = 'spring' and year = '2009'
group by dept_name
order by dept_name;
-- 2.  List all instructors (ID, dept_name, name) with the number of courses taught with the 
--     label "num_courses".   If an instructor did not teach, they are listed with a value of 0.
--     Order by result by ID.  A correct result will include the 3 instructors with 0 courses. 
select instructor.id, dept_name, name, count(course_id) as num_courses
from instructor left join teaches on instructor.id = teaches.id
group by instructor.name
order by instructor.id;

-- 3.  List the student majors (student.dept_name) and the number of students in each major
--     with the label "count"
select student.dept_name, count(name) as count
from student
group by dept_name;

-- 4.  Same as #3 but only list majors with more than 2 students.
select dept_name, count(name) as count
from student
group by dept_name
having count > 2;

-- 5.  List all departments and the number of students (use label "count") majoring in that department.
--     If a department has no student majors, show 0.
select department.dept_name, count(student.name) as count
from department left join student on department.dept_name=student.dept_name
group by dept_name;


-- 6.  List all departments and the number of students majoring in that department (use label "count")
--      and have more than 90 total credits.
--      A correct answer will show that History, Music and Physics departments have 0 students 
select department.dept_name, count(student.name) as count
from department left join student on department.dept_name = student.dept_name and tot_cred > 90
group by department.dept_name;

-- 7. For each department, show the total number of credits students  have earned in courses
--    from that department (use label "total").  
--    If a student's grade if F or null, they do not earn credit.
select course.dept_name, sum(credits) as total
from takes join course on takes.course_id=course.course_id
and takes.grade is not null and takes.grade != 'F'
group by course.dept_name;

-- 8.  Show the department name and the number of upper division courses [300-499] in that deparment. 
--      Use label "count" for the number of courses.
--      A correct answer will show there are 4 departments with 0 upper division courses.
select department.dept_name, count(course_id) as count
from department left join course on department.dept_name=course.dept_name and course_id like '%3%' or course_id like '4%'
group by dept_name;

select department.dept_name, count(instructor.id) as count
from department left join instructor on department.dept_name=instructor.dept_name and salary > 75000
group by dept_name;

select d.dept_name, count(*) as count
from department d left join instructor n on d.dept_name=n.dept_name
group by d.dept_name
order by d.dept_name;


-- 9.  show the instructor id and name and the course title of courses taught. 
--     if an instructor taught a course multiple times, don't list duplicates.
--     If the instructor has not taught any courses, show a null value for course title. 
--     Use a LEFT JOIN operator.
select distinct instructor.id, instructor.name, course.title
from instructor left join teaches on instructor.id=teaches.id left join course using(course_id);

-- 10. List the course_id and title for courses that are offered both in Fall 2009 and in Spring 2010.
 select distinct course.course_id, course.title
 from course join teaches using(course_id)
 where course_id in (select course_id from teaches where semester='Fall' and year=2009 )
 and course_id in (select course_id from teaches where semester='Spring' and year=2010);


-- 11.  List students (ID, name) with more than 90 credits or have taken more than 2 courses. 
--      Use UNION operator.
select id, name
from student
where tot_cred > 90
union
select student.id, name
from student join takes on student.id=takes.id
group by name
having count(course_id) > 2;

-- 12. Create a table transcript with columns 
--    StudentID, StudentName, year, semester, course_id,  title, grade.
--    Using a insert with select, copy transcript information for student '12345'
create table transcript(
	StudentID	varchar(5),
    StudentName	varchar(20),
    year		decimal(4),
    semester	varchar(7),
    course_id	varchar(8),
    title		varchar(50),
    grade		varchar(3)
);
insert into transcript select student.id, name, year, semester, takes.course_id, course.title, grade
	from student join takes using(id) join course using(course_id)
    where student.id=12345;

--  13. Display the transcript table.
--     The courses should be listed in chronological sequence, 2009 Spring, 2009 Summer, 2009 Fall, 2010 Spring, ...
--     hint:  use an order by with a case operator to get the correct sequence  
--     order by year, case semester when 'Spring' then 1 when 'Summer' then 2 when 'Fall' then 3 end
select *
from transcript
order by year,
	case 
		when semester = 'Spring' then 1
        when semester = 'Summer' then 2
        when semester = 'Fall' then 3
        end;

-- 14.  drop the transcript TABLE  
drop table transcript;

select department.dept_name, count(name) as count
from department left join student on department.dept_name=student.dept_name
group by department.dept_name;