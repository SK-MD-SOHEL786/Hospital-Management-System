create database hospital;
use hospital;

create table patients(
	id int auto_increment primary key,
    name varchar(255) not null,
    age int not null,
    gender varchar(10) not null);
    
create table doctors (
	id int auto_increment primary key,
    name varchar (255) not null,
    specialization varchar(255) not null); 
    
create table appoinments (
	id int auto_increment primary key,
    p_id int not null,
    d_id int not null,
    appoinment_date date not null,
    foreign key (p_id) references patients(id),
    foreign key (d_id) references doctors(id) 
    );
    
insert into doctors(name,specialization ) values ("Sadique","Physician");
insert into doctors(name,specialization ) values ("Aziz","Surgeon");
select * from appoinments ;
select * from patients;
select * from doctors;

