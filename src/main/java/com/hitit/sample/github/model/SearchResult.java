package com.hitit.sample.github.model;

import java.util.List;

public class SearchResult<T> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
