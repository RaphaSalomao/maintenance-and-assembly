CREATE TABLE maintenance_request (
	id 					UUID 	PRIMARY KEY DEFAULT 	uuid_generate_v4(),
  	user_id 			UUID 	NOT NULL  	REFERENCES 	t_user (id),
  	maintenance_info 	JSONB 	NOT NULL
);
