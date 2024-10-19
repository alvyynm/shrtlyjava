package com.shrtly.url.shortener.repository;

import com.shrtly.url.shortener.models.UrlStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UrlStatsRepository extends JpaRepository<UrlStat, Integer> {
    List<UrlStat> findByUrlIdAndDate(Integer urlId, LocalDate date);

    Iterable<UrlStat> findByUrlId(Integer urlId);
}
