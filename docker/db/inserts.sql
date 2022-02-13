insert into user_role (role_id, role_type)
values
(1,'buyer') ,(2,'seller') ,(3,'admin')
RETURNING role_id, role_type;
