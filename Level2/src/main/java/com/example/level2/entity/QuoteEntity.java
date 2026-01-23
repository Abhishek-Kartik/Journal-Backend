package com.example.level2.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class QuoteEntity implements Serializable {
    private String quote;
    private String work;
    private String author;
}
