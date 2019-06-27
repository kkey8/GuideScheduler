drop database if exists climbworksSchedulerTestDB;

create database climbworksSchedulerTestDB;

use climbworksSchedulerTestDB;

create table location(
	locationId int primary key auto_increment,
    city varchar(50) not null,
    `state` varchar(48) not null
);

create table employee(
	employeeId int primary key auto_increment,
    locationId int not null,
    firstName varchar(25) not null,
    lastName varchar(25) not null,
    username varchar(25) not null,
    `password` varchar(255) not null,
    active int not null,
    foreign key (locationId) references location(locationId)
);

create table `role`(
	roleId int primary key auto_increment,
    rolename varchar(20) not null
);

create table employeerole(
	employeeroleid int primary key auto_increment,
	roleId int not null,
    employeeId int not null,
    foreign key (roleId) references `role`(roleId),
    foreign key (employeeid) references employee(employeeid)
);

create table job(
	jobId int primary key auto_increment,
    title varchar(8) not null
);

create table employeeJob(
	employeeId int not null,
    jobId int not null,
	constraint pk_EmployeeJob
		primary key (employeeId, jobId),
	constraint fk_EmployeeJob_EmployeeId
		foreign key (employeeId) references Employee(employeeId),
	constraint fk_EmployeeJob_JobId 
		foreign key (jobId) references Job(jobId)
);

create table course(
	courseId int primary key auto_increment,
    locationId int not null,
    `name` varchar(50),
    numLines int not null,
    numReceivers int not null,
    managerFirstName varchar(25) null,
    managerLastName varchar(25) null,
    foreign key (locationId) references Location(locationId)
);

create table daySchedule(
	dayScheduleId int primary key auto_increment,
    courseId int not null,
    theDate date not null,
    foreign key (courseId) references Course(courseId)
);

create table tourTime(
	tourTimeId int primary key auto_increment,
    theTime time not null
);

create table tour(
	dayScheduleId int not null,
    tourId int primary key auto_increment,
    tourTimeId int not null,
    senderId int not null,
    receiverOneId int not null,
    receiverTwoId int null,
	foreign key (dayScheduleId) references DaySchedule(dayScheduleId),
    foreign key (tourTimeId) references TourTime(tourTimeId),
    foreign key (senderId) references Employee(employeeId),
    foreign key (receiverOneId) references Employee(employeeId),
    foreign key (receiverTwoId) references Employee(employeeId)
);

create table Rating(
	ratingId int primary key auto_increment,
    raterId int not null,
    ratedEmployeeId int not null,
    rate int null,
    foreign key (raterId) references Employee(employeeId),
    foreign key (ratedEmployeeId) references Employee(employeeId)
);

create table OffDay(
	offDayId int primary key auto_increment,
    employeeId int not null,
    offDate date not null,
    isResolved bool not null,
    foreign key (employeeId) references Employee(employeeId)
);

insert into job (title)
	values ('Sender'),
			('Receiver'),
            ('Smoothie'),
            ('Office'),
            ('Rescue'),
            ('Training')
;

insert into location(city, state)
	values ('Gatlinburg', 'Tennessee'),
			('Oahu', 'Hawaii')
;

insert into `role` (rolename)
	values ('ADMIN'),
			('USER');
            


insert into course(locationId, `name`, numLines, numReceivers, managerFirstName, managerLastName)
	values (1, 'Treetop', 9, 1, 'Phil', 'Rhodes'),
			(1, 'Mountaintop', 5, 2, 'Brian', 'Turley'),
            (2, 'Oceanfront', 9, 2, 'Adam', 'Thompson')
;

insert into tourTime(theTime)
	values ('7:00'),
			('7:30'),
            ('8:00'),
			('8:30'),
            ('9:00'),
			('9:30'),
            ('10:00'),
            ('10:30'),
            ('11:00'),
            ('11:30'),
            ('12:00'),
            ('12:30'),
            ('13:00'),
            ('13:30'),
            ('14:00'),
            ('14:30'),
            ('15:00'),
            ('15:30'),
            ('16:00'),
            ('16:30'),
            ('17:00'),
            ('17:30'),
            ('18:00'),
            ('18:30'),
            ('19:00'),
            ('19:30'),
            ('6:45'),
            ('7:15'),
            ('7:45'),
            ('8:15'),
            ('8:45'),
            ('9:15'),
            ('9:45'),
            ('10:15'),
            ('10:45'),
            ('11:15'),
            ('11:45'),
            ('12:15'),
            ('12:45'),
            ('13:15'),
            ('13:45'),
            ('14:15'),
            ('14:45'),
            ('15:15'),
            ('15:45'),
            ('16:15'),
            ('16:45'),
            ('17:15'),
            ('17:45'),
            ('18:15'),
            ('18:45'),
            ('19:15'),
            ('7:20'),
            ('7:40'),
            ('8:20'),
            ('8:40'),
            ('9:20'),
            ('9:40'),
            ('10:20'),
            ('10:40'),
            ('11:20'),
            ('11:40'),
            ('12:20'),
            ('12:40'),
            ('13:20'),
            ('13:40'),
            ('14:20'),
            ('14:40'),
            ('15:20'),
            ('15:40'),
            ('16:20'),
            ('16:40'),
            ('17:20'),
            ('17:40')
;

insert into employee (locationid, firstname, lastname, username, `password`, active)
	values (1, 'Brian', 'Turley', 'emp1', 'emp1', 1),
			(1, 'Jarrad', 'Read', 'emp2', 'emp2', 1),
            (1, 'Corbin', 'March', 'emp3', 'emp3', 1),
			(1, 'Shark', 'Marcellino', 'emp4', 'emp4', 1),
			(1, 'Oliver', 'Lee', 'emp5', 'emp5', 1),
			(1, 'Chin', 'Ollor', 'emp6', 'emp6', 1),
			(1, 'Jack', 'Gilbert', 'emp7', 'emp7', 1),
			(1, 'Moe', 'Bamba', 'emp8', 'emp8', 1),
			(1, 'Max', 'Taylor', 'emp9', 'emp9', 1),
			(1, 'Asap', 'Rocky', 'emp10', 'emp10', 1),
			(1, 'Tommy', 'Cooper', 'emp11', 'emp11', 1),
			(1, 'Ryan', 'Finnerty', 'emp12', 'emp12', 1),
			(1, 'RJ', 'Bell', 'emp13', 'emp13', 1),
			(1, 'Will', 'Godwin', 'emp14', 'emp14', 1),
			(1, 'Chris', 'Taylor', 'emp15', 'emp15', 1),
			(1, 'Steele', 'Fancher', 'emp16', 'emp16', 1),
			(1, 'Brook', 'Schroeder', 'emp17', 'emp17', 1),
			(1, 'Caitlin', 'McGinnis', 'emp18', 'emp18', 1),
			(1, 'Amanda', 'Malmin', 'emp19', 'emp19', 1),
			(1, 'Erin', 'Verbeck', 'emp20', 'emp20', 1),
			(1, 'Xong', 'Yang', 'emp21', 'emp21', 1),
			(1, 'Jennifer', 'Wasik', 'emp22', 'emp22', 1),
			(1, 'Courtney', 'Crowe', 'emp23', 'emp23', 1),
			(1, 'Erin', 'Kenny', 'emp24', 'emp24', 1),
			(1, 'Shelby', 'Greaves', 'emp25', 'emp25', 1),
			(1, 'Savannah', 'Alday', 'emp26', 'emp26', 1),
			(1, 'Blair', 'Moss', 'emp27', 'emp27', 1),
			(1, 'Lexie', 'Tietgens', 'emp28', 'emp28', 1),
			(1, 'Lauren', 'Hood', 'emp29', 'emp29', 1),
			(1, 'Maddie', 'Hays', 'emp30', 'emp30', 1)
;

insert into employeerole (employeeid, roleid)
	values (1, 1),
			(2, 1),
            (3, 2),
            (4, 2),
            (5, 2),
            (6, 2),
            (7, 2),
            (8, 2),
            (9, 2),
            (10, 2),
            (11, 2),
            (12, 2),
            (13, 2),
            (14, 2),
            (15, 2),
            (16, 2),
            (17, 2),
            (18, 2),
            (19, 2),
            (20, 2),
            (21, 2),
            (22, 2),
            (23, 2),
            (24, 2),
            (25, 2),
            (26, 2),
            (27, 2),
            (28, 2),
            (29, 2),
            (30, 2)
;

insert into employeejob (employeeid, jobid)
		values 
				(1, 2),
				(2, 2),
				(3, 2),
				(4, 2),
				(5, 2),
				(6, 2),
				(7, 2),
				(8, 2),
				(9, 2),
				(10, 2),
				(11, 2),
				(12, 2),
				(13, 2),
				(14, 2),
				(15, 2),
                (16, 1),
                (17, 1),
                (18, 1),
                (19, 1),
                (20, 1),
                (21, 1),
                (22, 1),
                (23, 1),
                (24, 1),
                (25, 1),
                (26, 1),
                (27, 1),
                (28, 1),
                (29, 1),
                (30, 1)
;
			















