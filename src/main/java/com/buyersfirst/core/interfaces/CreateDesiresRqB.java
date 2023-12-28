package com.buyersfirst.core.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDesiresRqB {
    public String title;
    public Double price;
    public String description;
    public Integer[] tags_id;
    public String picture;
}
