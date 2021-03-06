CREATE SEQUENCE PUBLIC.EVENTO_LOG_SEQ
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE PUBLIC.EVENTO_LOG (
    evento_log_id BIGINT NOT NULL,
    level_id SMALLINT NOT NULL,
    descricao character varying(1000) NOT NULL,
    log text NOT NULL,
    data_geracao timestamp without time zone NOT NULL,
    quantidade INTEGER NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT now(),
    usuario_id INTEGER NOT NULL,
    CONSTRAINT evento_pk PRIMARY KEY (evento_log_id),
    CONSTRAINT evento_tipologlevel_fK FOREIGN KEY (level_id)
        REFERENCES PUBLIC.TIPO_LOG_LEVEL (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT user_fk FOREIGN KEY (usuario_id)
            REFERENCES PUBLIC.USUARIO (usuario_id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
);