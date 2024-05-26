package com.example.demo.Service.Interface;

import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;

public interface ITesterIssueService {
    ResponseDto<IssueDto> addIssue(IssueDto issue, Long projectId, String reporterId);
}
