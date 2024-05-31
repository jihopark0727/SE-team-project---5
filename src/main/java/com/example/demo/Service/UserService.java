package com.example.demo.Service;

import com.example.demo.DTO.ForgotPasswordResponseDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interface.IUserIssueService;
import com.example.demo.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // 사용자가 프로젝트 리더인지 확인하는 메서드
    @Override
    public boolean isUserPL(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        return user.getUser_type().equals("pl");  // user_type이 'pl'인지 확인
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getDevsByProjectIdOrderByCareerDesc(Long projectId) {
        return userRepository.findDevsByProjectIdOrderByCareerDesc(projectId);
    }

    @Override
    public List<User> getRecommendDevs(Long projectId, Long issueId) {
        var issue = issueRepository.findById(issueId);
        String issueType;
        if(issue.isPresent()){
            issueType = issue.get().getIssueType();
        }
        else return null;
        class UserFixed implements Comparable<UserFixed> {
            public User user;
            public Long fixedNum;
            public UserFixed(User user, Long fixedNum) {
                this.user = user;
                this.fixedNum = fixedNum;
            }
            public int compareTo(UserFixed o) {
                if(fixedNum > o.fixedNum)  return -1;
                else if(fixedNum < o.fixedNum)  return 1;
                else{
                    if(user.getCareer() > o.user.getCareer()) return -1;
                    else if (user.getCareer() < o.user.getCareer()) return 1;
                    else return 0;
                }
            }
        }
        List<User> devs = userRepository.findDevsByProjectIdOrderByCareerDesc(projectId);
        ArrayList<UserFixed> fixedNums = new ArrayList<UserFixed>();
        for (User user : devs) {
            Long num = issueRepository.countByIssueTypeAndFixerId(issueType, user.getId());
            fixedNums.add(new UserFixed(user, num));
        }
        Collections.sort(fixedNums);
        devs.clear();
        for(UserFixed userFixed : fixedNums) {
            devs.add(userFixed.user);
        }
        return devs;
    }

}
