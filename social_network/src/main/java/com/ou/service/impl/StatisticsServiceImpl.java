package com.ou.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.repository.interfaces.AccountRepository;
import com.ou.service.interfaces.StatisticService;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Object[]> StatisticsNumberOfUsers(Map<String, String> params) throws Exception{
        try{
            return accountRepository.StatisticsNumberOfUsers(params);
        } catch(Exception exception){
            System.out.println("[DEBUG] - " + exception.getMessage());
            throw new Exception("Some things wrong");
        }
    }
    
}
