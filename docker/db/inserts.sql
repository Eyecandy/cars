-- create roles
insert into user_role (role_id, role_type)
values
(1,'buyer') ,(2,'seller') ,(3,'admin')
RETURNING role_id, role_type;

-- create car brands
insert into car_brand (car_brand_id, car_brand_name)
values
(1,'Toyota'), (2,'Honda'), (3,'BMW'), (4,'Mercedes-Benz'), (5,'Nissan'), (6,'Volswagen'),
(7, 'Alfa Romeo'), (8, 'Ford'), (9, 'Chevrolet'), (10, 'Hyundai'),
(11, 'Kia'), (12,'Mazda'), (13,'Mitsubishi'),(14,'Subaru'), (15,'Audi')
RETURNING car_brand_id, car_brand_name;

-- create counties
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
RETURNING county_id, county_name;
-- create config method
insert into config_method (config_method_id, name)
values (1, 'Link'), (2, 'PDF')
RETURNING config_method_id, name;

-- create retailers
insert into retailer (retailer_id, name)
values
(1, 'cardealer1'), (2, 'cardealer2'), (3, 'cardealer3'),
(4, 'cardealer4'), (5, 'cardealer5'),(6, 'cardealer6'),
(7,'cardealer7'), (8,'cardealer8'), (9,'cardealer9'),
(10,'cardealer10'), (11,'cardealer11'), (12,'cardealer12'),
(13,'cardealer13'), (14,'cardealer14')

RETURNING org_number, name;

-- insert one car dealer for each brand
insert into retailer_car_brands (retailer_id, car_brand_id)
values
(1,1), (2,2), (3,3), (4,4), (5,5),
(6,6), (7,7), (8,8), (9,9), (10,10),
(11,11), (12,12), (13,13), (14,14)

returning retailer_id, car_brand_id;
-- insert seller users
insert into user_account


