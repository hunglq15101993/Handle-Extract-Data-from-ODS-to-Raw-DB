CREATE TABLE lead_management.process_save_application (
	id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	application_id varchar(100) NULL,
	kafka_message text NULL,
	ods_request_payload text NULL,
	status varchar(20) DEFAULT 'RUNNING'::character varying NOT NULL,
	current_step varchar(50) NULL,
	retry_count int4 DEFAULT 0 NULL,
	error_message text NULL,
	enable_retry varchar(2) DEFAULT '0'::character varying NOT NULL,
	update_payload varchar(100) NULL,
	created_at timestamp DEFAULT current_timestamp NULL,
	updated_at timestamp NULL,
	event_request_payload text NULL,
	model_request_payload text NULL,
	"version" numeric NULL,
	CONSTRAINT process_save_application_pkey PRIMARY KEY (id)
);


CREATE TABLE lead_management.process_save_application_migration (
	id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	application_id varchar(4000) NULL,
	kafka_message text NULL,
	ods_request_payload text NULL,
	status varchar(4000) DEFAULT 'RUNNING'::character varying NOT NULL,
	current_step varchar(4000) NULL,
	retry_count int4 DEFAULT 0 NULL,
	error_message text NULL,
	enable_retry varchar(4000) DEFAULT '0'::character varying NOT NULL,
	update_payload varchar(4000) NULL,
	"version" numeric NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	event_request_payload text NULL,
	model_request_payload text NULL,
	CONSTRAINT process_save_application_migration_pkey PRIMARY KEY (id)
);