ALTER TABLE IF EXISTS travels 
    ALTER COLUMN price SET DATA TYPE numeric(10,2) USING price::numeric(10,2);

ALTER TABLE IF EXISTS travels 
    ALTER COLUMN seats SET DATA TYPE integer USING seats::integer;