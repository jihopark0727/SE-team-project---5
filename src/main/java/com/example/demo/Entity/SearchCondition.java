package com.example.demo.Entity;

import lombok.Data;

@Data
public class SearchCondition {
    private String reporter;
    private String assignee;
    private String priority;
    private String status;
}
