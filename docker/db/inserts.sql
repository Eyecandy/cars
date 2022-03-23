insert into user_role (role_id, role_type)
values
(1,'buyer') ,(2,'seller') ,(3,'admin')
RETURNING role_id, role_type;


insert into car_brand (car_brand_id, car_brand_name)
values
(1,'Toyota'), (2,'Honda'), (3,'BMW'), (4,'Mercedes-Benz'), (5,'Nissan'), (6,'Volswagen'),
(7, 'Alfa Romeo'), (8, 'Ford'), (9, 'Chevrolet'), (10, 'Hyundai'),
(11, 'Kia'), (12,'Mazda'), (13,'Mitsubishi'),(14,'Subaru'), (15,'Audi')
RETURNING car_brand_id, car_brand_name


insert into county (county_id, county_name)
values
(1,'Agder'),
(2,'Innlandet'),
(3,'Møre og Romsdal'),
(4,'Oslo'),
(5,'Nordland'),
(6,'Rogaland'),
(7,'Troms og Finnmark'),
(8,'Trøndelag'),
(9,'Vestfold og Telemark'),
(10, 'Vestland'),
(11, 'Viken')
RETURNING county_id, county_name











