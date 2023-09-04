# skill_queue_accelerate_backend
this is the backend code for skill queue accelerate project.

Developing Backend using Java 17 and Spring 3.1.1 (or newer version).

Tools Required : 
IDE -  any of your favorite IDE  (IntelliJ Idea or Eclipse) 
For API test -  Postman 
SCM tool -  Git and GitHub
Database - MySQL or PostgreSQL


Step 1: Download and install all of the above tools from Software Center.
                        
Step 2: I will share my repository link by today evening just fork it and you are ready to build.

Note: 
	• make a GitHub account if you don’t have on   https://infygithub.ad.infosys.com/ 
	• make a repository in your GitHub account 
  . Push and Pull your code from Git to GitHub repository as per your need 
  
  
  
  
  
  
  
  
  
  
  
  **Database Table : insert below lines into Mysql or Postgresql to create employee table
  
  
  create table employee (
        employee_id int,
	employee_name varchar(50),
	email_id varchar(50),
	skills varchar[],
	primary_skill varchar,
	certifications varchar[],
	description_of_Skills varchar,
	registration_time timestamp,
	constraint emp_id_pk primary key (employee_id)
)
