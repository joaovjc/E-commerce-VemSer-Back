package com.dbc.vemserback.ecommerce.service;

import java.util.List;

public class Page<T> {
    private Integer pageNumber;
    private Integer resultsPerPage;
    private Integer totalResults;
    private List<T> items;


    private static final int RESULTS_PER_PAGE = 5;
    public Page(Integer pageNumber, Integer totalResults, List<T> items) {
        this.pageNumber = pageNumber;
        this.resultsPerPage = RESULTS_PER_PAGE;
        this.totalResults = totalResults;
        this.items = items;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public Integer getResultsPerPage() {
        return resultsPerPage;
    }
    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }
    public Integer getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}