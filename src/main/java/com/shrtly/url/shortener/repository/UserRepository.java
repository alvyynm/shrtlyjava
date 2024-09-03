package com.shrtly.url.shortener.repository;

import com.shrtly.url.shortener.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
