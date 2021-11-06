CREATE TABLE t_user (
	id          UUID            PRIMARY KEY default uuid_generate_v4(),
  	email       VARCHAR(255)    UNIQUE      NOT NULL,
  	password    VARCHAR(255)    NOT NULL,
  	name        VARCHAR(255)    NOT NULL,
  	phone       VARCHAR(20)     UNIQUE      NOT NULL,
  	role        VARCHAR(50)     NOT NULL
);
