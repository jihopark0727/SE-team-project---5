package com.example.demo.Entity;

import lombok.Data;

@Data
public class SearchCondition {
    private String submit;
    private String assignee;
    private String priority;
    private String status;
}
