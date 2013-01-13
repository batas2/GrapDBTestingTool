DROP TABLE IF EXISTS public."Edges";
DROP TABLE IF EXISTS public."Vertex";

CREATE TABLE public."Vertex"
(
   "Id" serial, 
   "IntVal" integer, 
   "doubleVal" double precision, 
   "stringVal" character varying(512),
CONSTRAINT pk_vertex PRIMARY KEY ("Id")
);


CREATE TABLE "Edges"
(
  src integer,
  target integer,
  CONSTRAINT fk_src FOREIGN KEY (src)
      REFERENCES "Vertex" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_target FOREIGN KEY (target)
      REFERENCES "Vertex" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

