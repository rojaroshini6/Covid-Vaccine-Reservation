#Follow the steps to run backend service restAPI for covid-vaccine-reservation project. 

Pre-requisite: 
1) Install mysql server locally (Follow this guide - https://flaviocopes.com/mysql-how-to-install/)
2) Create a database/schema in mysql with the name "covid_vaccine_reservation" (Use Sequal Pro or TablePlus for sql queries)
3) Start mysql "mysql.server start"
4) Ensure java version is java8

How to run: 
1) Change mysql credentials in application.properties (spring.datasource.username & spring.datasource.password)
2) Run "mvn clean install -U" on the project root folder (i.e, covid-vaccine-reservation)
3) Run CovidVaccineReservation class from IDE (There are prefilled data in CovidVaccineReservation class which will populate data base)
4) Import the json file in postman (CovidVaccineReservation.postman_collection.json) to run and test all the rest API. 

Note:
1) Reservation time in DB is stored in UTC timezone
2) Each vaccination slot takes 15 minutes. So please choose time in the increment of 15 as per the centre working hours 
(Eg: 2021-11-19 10:00, 2021-11-19 10:15, 2021-11-19 10:30, 2021-11-19 10:45, 2021-11-19 11:00 etc)
3) Backend server runs on port 8081. if required change the port in application.properties (server.port) and also change port in postman collection. 
