DROP TABLE IF EXISTS public."Vertex";
CREATE TABLE public."Vertex"
(
   "Id" serial, 
   "IntVal" integer, 
   "doubleVal" double precision, 
   "stringVal" character varying(512), 
   CONSTRAINT pk_id PRIMARY KEY ("Id")
);

DROP TABLE IF EXISTS public."Edges";
CREATE TABLE public."Edges"
(
   id serial, 
   src integer, 
   target integer, 
   CONSTRAINT pk_edges PRIMARY KEY (id)
);
