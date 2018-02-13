DROP SCHEMA IF EXISTS hibernate_examples CASCADE;
CREATE SCHEMA hibernate_examples;

DROP SEQUENCE IF EXISTS hibernate_examples.contact_id_seq CASCADE;
CREATE SEQUENCE hibernate_examples.contact_id_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.contact CASCADE;
CREATE TABLE hibernate_examples.contact (
  contact_id numeric(10,0) PRIMARY KEY DEFAULT nextval('hibernate_examples.contact_id_seq'),
  first varchar(255),
  last varchar(255),
  middle varchar(255),
  notes varchar(255),
  starred boolean NOT NULL,
  flag varchar(1) NOT NULL,
  website varchar(255)
);

ALTER TABLE hibernate_examples.contact ADD CONSTRAINT flag_types CHECK (flag = '1' OR flag = '0');

INSERT INTO hibernate_examples.contact (first, starred, flag) VALUES('hello', true, '1');
select * from hibernate_examples.contact;

DROP SEQUENCE IF EXISTS hibernate_examples.location_id_seq CASCADE;
CREATE SEQUENCE hibernate_examples.location_id_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.location CASCADE;
CREATE TABLE hibernate_examples.location (
  location_id numeric(10,0) PRIMARY KEY DEFAULT nextval('hibernate_examples.location_id_seq'),
  type varchar(255) NOT NULL,
  name VARCHAR(255) NOT NULL
);

INSERT INTO hibernate_examples.location (type, name) VALUES('DEALERSHIP', 'San Francisco Cars');
select * from hibernate_examples.location;

DROP FUNCTION IF EXISTS hibernate_examples.set_update_ts CASCADE;
CREATE OR REPLACE FUNCTION hibernate_examples.set_update_ts()
  RETURNS TRIGGER AS $$
BEGIN
  NEW.update_ts = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TABLE IF EXISTS hibernate_examples.donor CASCADE;
CREATE TABLE hibernate_examples.donor (
  donor_id uuid PRIMARY KEY,
  name VARCHAR(255),
  type_code VARCHAR(50) NOT NULL,
  test_create_ts timestamp NOT NULL,
  test_update_ts timestamp NOT NULL,
  another_test_create_ts timestamp NOT NULL,
  another_test_update_ts timestamp NOT NULL,
  create_ts timestamp NOT NULL DEFAULT NOW(),
  update_ts timestamp NOT NULL DEFAULT NOW()
);

DROP TRIGGER IF EXISTS update_ts_donor_tr ON hibernate_examples.donor CASCADE;
CREATE TRIGGER update_ts_donor_tr
  BEFORE UPDATE ON hibernate_examples.donor
  FOR EACH ROW EXECUTE PROCEDURE hibernate_examples.set_update_ts();


select * from hibernate_examples.donor;


DROP SEQUENCE IF EXISTS hibernate_examples.client_id_seq CASCADE;
CREATE SEQUENCE hibernate_examples.client_id_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.client CASCADE;
CREATE TABLE hibernate_examples.client (
  client_id numeric(10,0) PRIMARY KEY DEFAULT nextval('hibernate_examples.client_id_seq'),
  name VARCHAR(255),
  FOREIGN KEY (client_id) REFERENCES hibernate_examples.client (client_id)
);


DROP SEQUENCE IF EXISTS hibernate_examples.account_id_seq CASCADE;
CREATE SEQUENCE hibernate_examples.account_id_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.account CASCADE;
CREATE TABLE hibernate_examples.account (
  account_id numeric(10,0) PRIMARY KEY DEFAULT nextval('hibernate_examples.account_id_seq'),
  client_id numeric(10,0) NOT NULL,
  account_type VARCHAR(50) NOT NULL,
  amount numeric,
  rate numeric,
  active boolean,
  FOREIGN KEY (client_id) REFERENCES hibernate_examples.client (client_id)
);



DROP SEQUENCE IF EXISTS hibernate_examples.book_id_seq CASCADE;
CREATE SEQUENCE IF NOT EXISTS hibernate_examples.book_id_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.book CASCADE;
CREATE TABLE hibernate_examples.book (
  book_id numeric(10, 0) PRIMARY KEY DEFAULT nextval('hibernate_examples.book_id_seq'),
  author varchar(255),
  title varchar(255)
);

DROP SEQUENCE IF EXISTS hibernate_examples.reader_id_seq CASCADE;
CREATE SEQUENCE IF NOT EXISTS hibernate_examples.reader_id_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.reader CASCADE;
CREATE TABLE IF NOT EXISTS hibernate_examples.reader (
  reader_id numeric(10, 0) PRIMARY KEY DEFAULT nextval('hibernate_examples.reader_id_seq'),
  name varchar(255)
);

DROP SEQUENCE IF EXISTS hibernate_examples.book_reader_seq CASCADE;
CREATE SEQUENCE hibernate_examples.book_reader_seq START 1;

DROP TABLE IF EXISTS hibernate_examples.book_reader CASCADE;
CREATE TABLE hibernate_examples.book_reader (
  book_reader_id numeric(10, 0) PRIMARY KEY DEFAULT nextval('hibernate_examples.book_reader_seq'),
  book_id numeric(10, 0) not null,
  reader_id numeric(10, 0) not null,
  created_on timestamp DEFAULT current_timestamp,
  FOREIGN KEY (book_id) REFERENCES hibernate_examples.book (book_id),
  FOREIGN KEY (reader_id) REFERENCES hibernate_examples.reader (reader_id)
)

delete from hibernate_examples.book_reader;
delete from hibernate_examples.book;
delete from hibernate_examples.reader;

select * from hibernate_examples.book;
select * from hibernate_examples.reader;
select * from hibernate_examples.book_reader;

select *
from
  hibernate_examples.reader r
  left join hibernate_examples.book_reader br
    ON br.reader_id = r.reader_id
  left join hibernate_examples.book b
    ON b.book_id = br.book_id;