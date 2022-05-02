-- Dropping tables if they exist


DROP VIEW IF EXISTS displayTutor CASCADE;
DROP VIEW IF EXISTS displayStudent CASCADE;
DROP TABLE IF EXISTS Booking CASCADE;
DROP TABLE IF EXISTS Session CASCADE;
DROP TABLE IF EXISTS Location CASCADE;
DROP TABLE IF EXISTS Offering CASCADE;
DROP TABLE IF EXISTS Course CASCADE;
DROP TABLE IF EXISTS Admin CASCADE;
DROP TABLE IF EXISTS Student CASCADE;
DROP TABLE IF EXISTS Availability CASCADE;
DROP TABLE IF EXISTS Tutor CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Account CASCADE;

-----------------------------------------------------------------------------------------------------------------
-- Creating tables

CREATE TABLE Account(
	user_id smallint Primary Key,
	user_password varchar(20)
);

CREATE TABLE Users(
	user_id smallint PRIMARY KEY,
	user_fname varchar(15) not NULL,
	user_lname varchar(15) not NULL,
	user_phonenum char(10),
	user_dob date not NULL,
	user_type char(1) not NULL,
	CONSTRAINT CkUser_type CHECK (user_type in ('A', 'S', 'T')),
	CONSTRAINT accountfk FOREIGN KEY (user_id) REFERENCES Account(user_id) 
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Admin(
	PRIMARY KEY(user_id),
	admin_role char(1) not NULL,
	admin_hiring_date date not NULL,
	CONSTRAINT Ckadmin_role CHECK (admin_role in ('S', 'M', 'O'))
)INHERITS(users);

CREATE TABLE Student(
	PRIMARY KEY(user_id),
	attended_hours decimal(5,2) NOT NULL DEFAULT 0,
	stud_balance decimal(6,2) NOT NULL Default 500,
	reg_date date NOT NULL,
	academic_level varchar(5) NOT NULL,
	CONSTRAINT Ckacademic_level CHECK (academic_level in ('TC', '1BAC', '2BAC'))
)INHERITS(users);


CREATE TABLE Tutor(
	PRIMARY KEY(user_id),
	tutor_balance decimal(6,2) not NULL DEFAULT 0,
	tutor_level VARCHAR(10) not null,
	tutor_work_hours decimal(5,2) NOT NULL DEFAULT 0,
	CONSTRAINT Cklevel CHECK (tutor_level in ('TC', '1BAC', '2BAC'))
)INHERITS(users);

CREATE TABLE Availability(
	av_code int PRIMARY KEY,
	av_date date not NULL,
	av_start_time time NOT NULL, 
	av_end_time time NOT NULL,
	tutor_id smallint NOT NULL,
	av_status VARCHAR(9) not null DEFAULT 'Unbooked',
	CONSTRAINT CKstatus CHECK (av_status IN ('Open', 'Closed', 'Unbooked', 'Done')),
	CONSTRAINT timeConstraint CHECK (av_start_time < av_end_time),
	CONSTRAINT available_at FOREIGN KEY (tutor_id) REFERENCES Tutor(user_id)
                      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Course(
	course_code VARCHAR(8) PRIMARY KEY,
	course_title VARCHAR(30) not null,
	course_level VARCHAR(10) not null,
	course_price_hr int not null,
	CONSTRAINT Cklevel CHECK (course_level in ('TC', '1BAC', '2BAC'))
);

CREATE TABLE Offering(
	tutor_ID smallint,
	course_code VARCHAR(8),
	PRIMARY KEY(tutor_ID, course_code),
	CONSTRAINT offers FOREIGN KEY (tutor_ID) REFERENCES Tutor(User_ID)
        		ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT offered_by FOREIGN KEY (course_code) REFERENCES Course(course_code)
        		ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Location(
	room_num smallint PRIMARY KEY,
	room_seats smallint not NULL DEFAULT 14
);

CREATE TABLE Session(
	session_code VARCHAR(8) PRIMARY KEY,
	session_date date not null,
	session_start_Time time NOT NULL, 
	session_end_Time time NOT NULL,
	session_seats_av smallint not null DEFAULT 14,
	session_status VARCHAR(9) not null DEFAULT 'Open',
	course_code VARCHAR(8),
	tutor_ID smallint,
	room_num int DEFAULT 3,
	CONSTRAINT CKstatus CHECK (session_status IN ('Open', 'Closed', 'Canceled', 'Done')),
   	CONSTRAINT timeConstraint CHECK (session_start_time < session_end_time),
	CONSTRAINT corresponds FOREIGN KEY (course_code, tutor_ID) REFERENCES OFFERING(course_code, tutor_ID)
        		ON UPDATE CASCADE ON DELETE CASCADE,
     	CONSTRAINT located_in FOREIGN KEY (room_num) REFERENCES Location(room_num)
       		 	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Booking(
	student_id smallint,
	session_code varchar(8),
	PRIMARY KEY(session_code, student_id),
	rating smallint DEFAULT 5,
	CONSTRAINT booked_by FOREIGN KEY (student_id) REFERENCES Student(user_id)
                      	ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT creates FOREIGN KEY (session_code) REFERENCES Session(session_code)
    			ON UPDATE CASCADE ON DELETE CASCADE,
   	CONSTRAINT Ckacademic_level CHECK (rating BETWEEN 0 AND 5)
);

------------------------------------------------------------------------------------------

-- Inserting into tables

INSERT INTO Account(user_id, user_password) VALUES
  (100, 'saaddr'),
  (101, 'yassirbe'),
  (102, 'khaoulaai'),
  (103, 'nadabo'),
  (104, 'younesja'),
  (105, 'ikramai'),
  (106, 'hamzaai'),
  (1, 'rafahbe'),
  (10, 'lailamo'),
  (11, 'mouadel'),
  (20, 'anassde'),
  (50, 'hassanro'),
  (51, 'wahibakh'),
  (52, 'naimala'),
  (53, 'omarbo'),
  (54, 'fouadzi'),
  (55, 'abdelmajidrb'),
  (56, 'ibrahimih'),
  (57, 'ahmadbo'),
  (58, 'bouchrali'),
  (59, 'saidana');

INSERT INTO Student(user_id, user_fname, user_lname, user_phonenum, user_dob, user_type, reg_date, academic_level) VALUES
  (100, 'Saad', 'Driouech', '0662097271', '2000-09-15', 'S', '2021-09-01', 'TC'),
  (101, 'Yassir', 'Bendabdellah', '0654789622', '2000-06-14', 'S', '2021-09-01', '1BAC'),
  (102, 'Khaoula', 'Ait Soussi', '0614859473', '2000-12-08', 'S', '2021-09-01', '2BAC'),
  (103, 'Nada', 'Bounajma', '0638749122', '2001-01-22', 'S', '2021-09-01', '1BAC'),
  (104, 'Younes', 'Jamal', '0754399985', '2000-10-01', 'S', '2021-09-01', 'TC'),
  (105, 'Ikram', 'Ainan', '0627313835', '2006-10-01', 'S', '2021-09-01', 'TC'),
  (106, 'Hamza', 'Aisour', '0738273462', '2006-10-01', 'S', '2021-09-01', 'TC');

INSERT INTO Admin (user_id, user_fname, user_lname, user_phonenum, user_dob, user_type, admin_role, admin_hiring_date) VALUES
  (1, 'Rafah', 'Bennani', '0512846931', '1982-03-28', 'A', 'O', '2018-09-01'),
  (10, 'Laila', 'Moustaghit', '0546952132', '1990-06-12', 'A', 'M', '2018-09-15'),
  (20, 'Mouad', 'Elazizi', '0548623597', '1988-04-30', 'A', 'S', '2018-09-15'),
  (21, 'Anass', 'Dehbi', '0678549622', '1991-10-24', 'A', 'S', '2019-04-01');

INSERT INTO Tutor(user_id, user_fname, user_lname, user_phonenum, user_dob, user_type, tutor_level) VALUES
  (50, 'Hassan', 'Rouias', '0654896755', '1975-02-14', 'T', '1BAC'),
  (51, 'Wahiba', 'Khassim', '0696342100', '1983-03-03', 'T', 'TC'),
  (52, 'Naima', 'Laalami', '0675896314', '1978-08-15', 'T', '1BAC'),
  (53, 'Omar', 'Bouchta', '0666128684', '1976-06-06', 'T', '2BAC'),
  (54, 'Fouad', 'Ziani', '0658669744', '1979-07-12', 'T', 'TC'),
  (55, 'Abdelmajid', 'Rbib', '0685479123','1976-08-12', 'T', '1BAC'),
  (56, 'Ibrahim', 'Ih', '0672934853', '1969-07-12', 'T', '1BAC'),
  (57, 'Ahmad', 'Bouguern', '0689253627', '1980-02-01', 'T', '2BAC'),
  (58, 'Bouchra', 'Liman', '0678392748', '1962-03-12', 'T', 'TC'),
  (59, 'Saida', 'Nasir', '0678362819', '1964-08-14', 'T', '2BAC');

INSERT INTO Availability(av_code, av_date, av_start_time, av_end_time, tutor_id, av_status) VALUES
  (1, '2021-12-29', '18:00:00', '19:00:00', 50, 'Open'),
  (2, '2021-12-27', '19:00:00', '20:00:00', 50, 'Open'),
  (3, '2021-12-29', '19:00:00', '20:00:00', 51, 'Unbooked'),
  (4, '2021-12-28', '17:00:00', '18:00:00', 51, 'Open'),
  (5, '2021-12-20', '19:00:00', '20:00:00', 52, 'Done'),
  (6, '2021-12-27', '17:30:00', '18:30:00', 53, 'Closed'),
  (7, '2021-12-26', '20:30:00', '21:30:00', 53, 'Open'),
  (8, '2021-12-31', '18:30:00', '19:30:00', 54, 'Unbooked'),
  (9, '2021-12-30', '18:30:00', '19:30:00', 50, 'Unbooked'),
  (10, '2021-12-27', '19:00:00', '20:00:00', 59, 'Open'),
  (11, '2021-12-28', '18:00:00', '19:00:00', 57, 'Unbooked'),
  (12, '2021-12-22', '20:00:00', '21:00:00', 56, 'Done'),
  (13, '2021-12-29', '21:00:00', '22:00:00', 57, 'Open'),
  (14, '2022-01-02', '22:00:00', '23:00:00', 58, 'Closed'),
  (15, '2021-12-21', '19:00:00', '20:00:00', 55, 'Done');
  

INSERT INTO Course (course_code, course_title, course_level, course_price_hr) VALUES 
  ('MTH1', 'Math 1 BAC', '1BAC', 250.00),
  ('MTH2', 'Math 2 BAC', '2BAC', 300.00),
  ('PHY1', 'Physique 1 BAC', '1BAC', 200.00),
  ('PHY2', 'Physique 2 BAC', '2BAC', 280.00),
  ('MTH0', 'Math tronc commun', 'TC', 150.00),
  ('PHY0', 'Physique TC', 'TC', 120.00),
  ('SVT0', 'Science de Vie et Terre TC', 'TC', 100.00),
  ('SVT1', 'Science de Vie et Terre 1 BAC', '1BAC', 180.00),
  ('SVT2', 'Science de Vie et Terre 2 BAC', '2BAC', 220.00);

INSERT INTO Offering(tutor_id, course_code) VALUES
  (50, 'MTH1'),
  (51, 'MTH0'),
  (52, 'PHY1'),
  (53, 'SVT2'),
  (54, 'SVT0'),
  (55, 'MTH1'),
  (56, 'SVT1'), 
  (57, 'MTH2'),
  (58, 'PHY0'),
  (59, 'PHY2');

INSERT INTO Location(room_num, room_seats) VALUES
  (1, 15),
  (2, 15),
  (3, 20),
  (4, 15),
  (5, 4);

INSERT INTO Session(session_code, session_date, session_start_time, session_end_time, session_seats_av, session_status, course_code, tutor_id, room_num) VALUES
  ('ses1', '2021-12-29', '18:00:00', '19:00:00', 10 , 'Open', 'MTH1', 50, 1),
  ('ses2', '2021-12-27', '19:00:00', '20:00:00', 13, 'Open', 'MTH1', 50, 1),
  ('ses3', '2021-12-28', '17:00:00', '18:00:00', 13, 'Open', 'MTH0', 51, 1),
  ('ses4', '2021-12-20', '19:00:00', '20:00:00', 12, 'Done', 'PHY1', 52, 2),
  ('ses5', '2021-12-27', '17:30:00', '18:30:00', 0, 'Closed', 'SVT2', 53, 1),
  ('ses6', '2021-12-26', '20:30:00', '21:30:00', 14, 'Open', 'SVT2', 53, 1),
  ('ses7', '2021-12-27', '19:00:00', '20:00:00', 12, 'Open', 'PHY2', 59, 3),
  ('ses8', '2021-12-22', '20:00:00', '21:00:00', 11, 'Done', 'SVT1', 56, 1),
  ('ses9', '2021-12-29', '21:00:00', '22:00:00', 13, 'Open', 'MTH2', 57, 1),
  ('ses10', '2022-01-02', '22:00:00', '23:00:00', 0, 'Closed', 'PHY0' , 58, 3),
  ('ses11', '2021-12-21', '19:00:00', '20:00:00', 10, 'Done', 'MTH1', 55, 1);
  
  
INSERT INTO Booking(student_id, session_code) VALUES
  (100, 'ses1'),
  (101, 'ses1'),
  (102, 'ses1'),
  (103, 'ses1'),
  (105, 'ses1'),
  (100, 'ses2'),
  (101, 'ses2'),
  (104, 'ses3'),
  (106, 'ses3'), 
  (100, 'ses4'),
  (101, 'ses4'),
  (102, 'ses4'),
  (103, 'ses5'),
  (105, 'ses6'),
  (102, 'ses7'),
  (103, 'ses7'),
  (104, 'ses7'),
  (106, 'ses8'),
  (102, 'ses8'),
  (103, 'ses8'),
  (105, 'ses8'),
  (100, 'ses9'),
  (101, 'ses9'),
  (104, 'ses10'),
  (104, 'ses11'),
  (106, 'ses11'),
  (102, 'ses11'),
  (103, 'ses11'),
  (105, 'ses11');
  --------------------------------------------------------------------------------------------------------
  -- Functionalities and Triggers
  
  CREATE or replace FUNCTION inheritanceCheck() RETURNS TRIGGER
AS $$
    DECLARE
    is_exist boolean;
BEGIN
    SELECT true INTO is_exist 
    FROM Admin  AS P FULL OUTER JOIN Tutor AS M ON P.User_ID = M.User_ID FULL OUTER JOIN Student AS A ON A.User_ID = M.User_ID
    WHERE P.User_ID = NEW.User_ID OR M.User_ID = NEW.User_ID  OR A.User_ID = NEW.User_ID ;

    IF is_exist = true  THEN
        RAISE EXCEPTION 'This user already exist.';
    END IF;
    RETURN NEW;

END;
$$
LANGUAGE plpgsql;


CREATE TRIGGER InheritanceStudentTr BEFORE INSERT ON student
    FOR EACH ROW
    EXECUTE FUNCTION inheritanceCheck();
CREATE TRIGGER InheritanceProfessorTr BEFORE INSERT ON Tutor
    FOR EACH ROW
    EXECUTE FUNCTION inheritanceCheck();
CREATE TRIGGER InheritanceAdminTr BEFORE INSERT ON Admin
    FOR EACH ROW
    EXECUTE FUNCTION inheritanceCheck();
	
	
---------------------------------------------------
---- Add hour to the tutor
create or replace function addHourTutor () returns trigger
as $$
begin	 
	update tutor
	set tutor_work_hours = tutor_work_hours + 1 
	where user_ID = new.tutor_id;
	return new;
	
end;
$$
language plpgsql; 

DROP TRIGGER IF EXISTS addhourTR ON session CASCADE;
create trigger addHourTR after insert on session
for each row
execute function addHourTutor();
---------------------------------------------------------------------------------
----- Add hour to the student 

create or replace function addHourStudent() returns trigger
as $$  
begin		
	update student
	set attended_hours = attended_hours + 1
	where user_ID = new.student_ID;
	return new;
end;
$$
language plpgsql;

DROP TRIGGER IF EXISTS addhourST ON booking CASCADE;
create trigger addHourTr after insert on booking
for each row
execute function addHourStudent();

---------------------------------------------------------------------------------------------
-- Views
------ Displaying Tutor for Students OR tutor for tutor

create view displayTutor as 
select user_fname, user_lname, tutor_level from tutor;

------ displaying student for tutor or studnt for student

create view displayStudent as
select user_fname, user_lname, reg_date, academic_level from student;

------ 

-----------------------------------------------------------------------------------------------------------------------------------------------
------ Procedure to create Student
CREATE OR REPLACE Procedure createStudent (id smallint, password_F varchar(20),fn varchar(15),ln varchar(15),phonenumber varchar(10),dob date,academic_lvl varchar(5))
	LANGUAGE plpgsql
	  AS $$
    DECLARE 
	cur_date date;
	utype char(1);
    BEGIN
			select CURRENT_DATE INTO cur_date;
			utype='S';
            insert Into account(user_id, user_password) 
            Values(id,password_F);
            insert into student(user_id,user_fname,user_lname,user_phonenum,user_dob,user_type,reg_date,academic_level)Values
            (id,fn,ln,phoneNumber,dob,utype,cur_date,academic_lvl);
END;
$$;
-----------------------------------------------------------------------------------------------------------------------------------------------
------ Procedure to create Admin
CREATE OR REPLACE Procedure createAdmin (id smallint, upassword varchar(20), fn varchar(15),ln varchar(15),phonenumber varchar(10),dob date,adrole char(1))
	LANGUAGE plpgsql
	  AS $$
    DECLARE 
    cur_date date;
	utype char(1);
    BEGIN
		    select CURRENT_DATE INTO cur_date;
			utype='A';
            insert Into account(user_id, user_password) 
            Values(id,upassword);
            insert into Admin(user_id,user_fname,user_lname,user_phonenum,user_dob,user_type,admin_role,admin_hiring_date)Values
            (id,fn,ln,phonenumber,dob,utype,adrole,cur_date);
END;
$$;
-----------------------------------------------------------------------------------------------------------------------------------------------
------ Procedure to create Tutor
CREATE OR REPLACE Procedure createTutor (id smallint, upassword varchar(20), fn varchar(15),ln varchar(15),phonenumber varchar(10),dob date,lvl varchar(10))
	LANGUAGE plpgsql
	  AS $$
    DECLARE 
		utype char(1);
		cur_date date;
    BEGIN
            select CURRENT_DATE INTO cur_date;
			utype='T';
            insert Into account(user_id, user_password) 
            Values(id,upassword);
            insert into Tutor(user_id,user_fname,user_lname,user_phonenum,user_dob,user_type,tutor_level)Values
            (id,fn,ln,phonenumber,dob,utype,lvl);
END;
$$;


-----------------------------------------------------------------------------------------------------------------------------------------------
------ Indexes
Create index studName on student (user_fname, user_lname);
Create index tutorName on tutor (user_fname, user_lname);
create index tutorLvl on tutor (tutor_level);
Create index sessionDate on session (session_date);
Create index courseTitle on course (course_title);