DROP TABLE IF EXISTS public."Vertex";
CREATE TABLE public."Vertex"
(
   "Id" serial, 
   "IntVal" integer, 
   "doubleVal" double precision, 
   "stringVal" character varying(512)
);

DROP TABLE IF EXISTS public."Edges";
CREATE TABLE public."Edges"
(
   src integer, 
   target integer
);
