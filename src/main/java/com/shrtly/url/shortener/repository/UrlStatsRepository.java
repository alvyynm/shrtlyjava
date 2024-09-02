package com.shrtly.url.shortener.repository;

import com.shrtly.url.shortener.models.UrlStat;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface UrlStatsRepository extends CrudRepository<UrlStat, Integer> {
    List<UrlStat> findByUrlIdAndDate(Integer urlId, LocalDate date);

    Iterable<UrlStat> findByUrlId(Integer urlId);
}
