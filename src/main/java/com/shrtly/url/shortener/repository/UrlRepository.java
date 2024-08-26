package com.shrtly.url.shortener.repository;

import com.shrtly.url.shortener.models.Url;
import org.springframework.data.repository.CrudRepository;

//  the repository that holds Url records
public interface UrlRepository extends CrudRepository<Url, Integer> {
}
