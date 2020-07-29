CREATE TABLE PUBLIC.TIPO_LOG_LEVEL (
    id smallint NOT NULL,
    descricao character varying(20) NOT NULL,
    CONSTRAINT tipo_log_level_pkey PRIMARY KEY (id)
);

insert into PUBLIC.TIPO_LOG_LEVEL values(1, 'error');

insert into PUBLIC.TIPO_LOG_LEVEL values(2, 'warning');

insert into PUBLIC.TIPO_LOG_LEVEL values(3, 'info');