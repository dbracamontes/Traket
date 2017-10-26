/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Abraham Rayas<abraham.rayas@hotmail.com>
 * Created: 22/09/2017
 */

CREATE DATABASE Traket;
\c traket;
CREATE SEQUENCE empresa_rid_seq;
CREATE TABLE empresa (
    rid bigint PRIMARY KEY NOT NULL DEFAULT nextval('empresa_rid_seq'), 
    nombre varchar NOT NULL
);
CREATE SEQUENCE empleado_rid_seq;
CREATE TABLE empleado (
    rid bigint PRIMARY KEY NOT NULL DEFAULT nextval('empleado_rid_seq'), 
    belongs bigint REFERENCES empresa(rid) NOT NULL,
    nombre varchar NOT NULL,
    apellido_paterno varchar NOT NULL,
    apellido_materno varchar NOT NULL,
    puesto varchar,
    area varchar
);
CREATE SEQUENCE usuario_rid_seq;
CREATE TABLE usuario (
    rid bigint PRIMARY KEY NOT NULL DEFAULT nextval('usuario_rid_seq'), 
    belongs bigint REFERENCES empresa(rid) NOT NULL,
    nombre varchar NOT NULL,
    apellido_paterno varchar NOT NULL,
    apellido_materno varchar NOT NULL,
    email varchar NOT NULL,
    password varchar NOT NULL,
    telefono varchar,
    ext varchar
);
CREATE SEQUENCE ticket_rid_seq;
CREATE TABLE ticket (
    rid bigint PRIMARY KEY NOT NULL DEFAULT nextval('ticket_rid_seq'), 
    belongs bigint REFERENCES empresa(rid) NOT NULL,
    rid_empleado bigint REFERENCES empleado(rid) NOT NULL,
    rid_usuario bigint REFERENCES usuario(rid),
    titulo varchar NOT NULL,
    descripcion varchar NOT NULL,
    prioridad integer NOT NULL,
    tiempo_estimado integer,
    area varchar NOT NULL,
    status integer NOT NULL,
    fecha_estimada timestamp,
    fecha_creacion timestamp,
    fecha_modificacion timestamp
);
CREATE SEQUENCE ticket_comentarios_rid_seq;
CREATE TABLE ticket_comentarios (
    rid bigint PRIMARY KEY NOT NULL DEFAULT nextval('ticket_comentarios_rid_seq'), 
    rid_ticket bigint REFERENCES ticket(rid) NOT NULL,
    rid_empleado bigint REFERENCES empleado(rid),
    rid_usuario bigint REFERENCES usuario(rid),
    comentario varchar NOT NULL,
    fecha_creacion timestamp
);


alter table usuario drop column password;

CREATE SEQUENCE ticket_credenciales_usuario_rid_seq;
CREATE TABLE credenciales_usuario (
    rid bigint PRIMARY KEY NOT NULL DEFAULT nextval('ticket_credenciales_usuario_rid_seq'), 
    rid_usuario bigint REFERENCES usuario(rid) NOT NULL,
    password bytea NOT NULL,
    salt bytea NOT NULL,
    fecha_modificacion timestamp,
    fecha_expiracion timestamp
);