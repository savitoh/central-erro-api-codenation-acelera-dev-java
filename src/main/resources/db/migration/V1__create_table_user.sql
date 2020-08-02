CREATE SEQUENCE PUBLIC.USER_SEQ
    INCREMENT 1
    START 2
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE PUBLIC.USER (
    user_id integer NOT NULL,
    nome character varying(50) NOT NULL,
    password character varying(60) NOT NULL,
    login character varying(12) NOT NULL UNIQUE,
    created_at timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);