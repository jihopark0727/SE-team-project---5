package com.example.demo.Service.Factory;

import com.example.demo.Service.DevIssueService;
import com.example.demo.Service.Interface.IUserIssueService;
import com.example.demo.Service.PLIssueService;
import com.example.demo.Service.TesterIssueService;

public class IssueServiceFactory {
    public static IUserIssueService getIssueService(String userType){
        if(userType.equals("tester")){
            return new TesterIssueService();
        }
        else if(userType.equals("pl")){
            return new PLIssueService();
        }
        else if(userType.equals("dev")){
            return new DevIssueService();
        }
        return null;
    }
}
