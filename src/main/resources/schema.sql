create table if not exists urls (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    url_id varchar(40),
    original_url varchar(255),
    shortened_url varchar(255)
);

create table if not exists urlstats (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    url_id INT NOT NULL,
    date DATE NOT NULL,
    click_count INT NOT NULL,
    FOREIGN KEY (url_id) REFERENCES urls(id)
)
