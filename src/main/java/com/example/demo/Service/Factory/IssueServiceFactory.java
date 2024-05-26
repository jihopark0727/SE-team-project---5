package com.example.demo.Service.Factory;

import com.example.demo.Service.DevIssueService;
import com.example.demo.Service.Interface.IUserIssueService;
import com.example.demo.Service.PLIssueService;
import com.example.demo.Service.TesterIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceFactory {
    @Autowired
    private TesterIssueService tester;
    @Autowired
    private PLIssueService pl;
    @Autowired
    private DevIssueService dev;
    public IUserIssueService getIssueService(String userType){
        if(userType.equals("tester")){
            return tester;
        }
        else if(userType.equals("pl") || userType.equals("admin")){
            return pl;
        }
        else if(userType.equals("dev")){
            return dev;
        }
        return null;
    }
}
