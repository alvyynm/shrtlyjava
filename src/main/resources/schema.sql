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
);

create table if not exists users (
    userId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    fullName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    userRole VARCHAR(10) DEFAULT 'USER',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ALTER TABLE users
-- ADD COLUMN status VARCHAR(10);
