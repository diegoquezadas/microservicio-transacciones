-- Database: banco

-- DROP DATABASE IF EXISTS banco;

CREATE DATABASE banco
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	

--creacion tabla clientes

CREATE TABLE public.clientes
(
    id serial NOT NULL,
    cedula text,
    nombre text,
    genero text,
    edad integer,
    direccion text,
    telefono text,
    contrasenia text,
    estado boolean,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE IF EXISTS public.clientes
    OWNER to postgres;

--creacion tabla cuentas

CREATE TABLE public.cuentas
(
    id serial NOT NULL,
    cliente integer,
    numero_cuenta text,
    saldo_inicial integer,
    tipo_cuenta text,
    estado boolean,
    CONSTRAINT pk_cuenta PRIMARY KEY (id),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente)
        REFERENCES public.clientes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

ALTER TABLE IF EXISTS public.cuentas
    OWNER to postgres;

--creacion tabla movimientos

CREATE TABLE public.movimientos
(
    id serial NOT NULL,
    cuenta integer,
    fecha date,
    tipo_movimiento text,
    valor integer,
    saldo integer,
    CONSTRAINT pk_movimientos PRIMARY KEY (id),
    CONSTRAINT fk_cuenta FOREIGN KEY (cuenta)
        REFERENCES public.cuentas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

ALTER TABLE IF EXISTS public.movimientos
    OWNER to postgres;
