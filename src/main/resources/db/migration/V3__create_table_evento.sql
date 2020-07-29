CREATE SEQUENCE PUBLIC.EVENTO_SEQ
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE PUBLIC.EVENTO (
    evento_id BIGINT NOT NULL,
    level_id SMALLINT NOT NULL,
    descricao character varying(1000) NOT NULL,
    log text NOT NULL,
    data_geracao timestamp without time zone NOT NULL,
    quantidade INTEGER NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT evento_pk PRIMARY KEY (evento_id),
    CONSTRAINT evento_tipologlevel_id_fK FOREIGN KEY (level_id)
        REFERENCES PUBLIC.TIPO_LOG_LEVEL (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);