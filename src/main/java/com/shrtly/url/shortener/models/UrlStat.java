package com.shrtly.url.shortener.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("URLSTATS")
public class UrlStat {
    @Id
    private Integer id;

    private Integer urlId;

    private LocalDate date;

    private Integer clickCount;

    public UrlStat(Integer urlId, LocalDate date, Integer clickCount) {
        this.urlId = urlId;
        this.date = date;
        this.clickCount = clickCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}
