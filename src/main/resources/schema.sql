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
    user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(10) DEFAULT 'USER',
    status VARCHAR(10) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
