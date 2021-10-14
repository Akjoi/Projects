CREATE TABLE IF NOT EXISTS users (
id integer PRIMARY KEY AUTOINCREMENT,
login text NOT NULL,
email text NOT NULL,
psw text NOT NULL,
time integer NOT NULL
);
CREATE TABLE IF NOT EXISTS necrologs(
id integer PRIMARY KEY AUTOINCREMENT,
author_id integer,
second_name text,
first_name text,
father_name text ,
bdate DATE,
ddate DATE,
necro text,
time integer NOT NULL,
);