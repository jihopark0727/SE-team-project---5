
package com.example.demo.DTO;

import com.example.demo.Entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String issue_type;
    private Project project;
    private Date reported_time;
    private Date last_modified_time;
    private String reporterId;
    private String assigneeId;
    private String fixerId;
}
