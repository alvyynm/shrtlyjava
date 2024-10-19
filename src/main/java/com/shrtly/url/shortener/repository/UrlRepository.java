package com.shrtly.url.shortener.repository;

import com.shrtly.url.shortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.CrudRepository;

//  the repository that holds Url records
@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {
    Url findByUrlId(String urlId);
}
