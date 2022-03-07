insert into user_role (role_id, role_type)
values
(1,'buyer') ,(2,'seller') ,(3,'admin')
RETURNING role_id, role_type;


insert into car_brand (car_brand_id, car_brand_name)
values
(1,'Toyota'), (2,'Honda'), (3,'BMW'), (4,'Mercedes'), (5,'Nissan'), (6,'Volswagen')
RETURNING car_brand_id, car_brand_name
