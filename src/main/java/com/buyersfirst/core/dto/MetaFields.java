package com.buyersfirst.core.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // Needed for serialization
public class MetaFields {
    Integer total;
    Integer perPage;
    Integer page;

    public MetaFields() {
    }

    public MetaFields(Integer total, Integer perPage, Integer page) {
        this.total = total;
        this.perPage = perPage;
        this.page = page;
    }
}