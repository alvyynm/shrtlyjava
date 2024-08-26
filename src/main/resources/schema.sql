create table if not exists urls (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    url_id varchar(40),
    original_url varchar(255),
    shortened_url varchar(255)
);
