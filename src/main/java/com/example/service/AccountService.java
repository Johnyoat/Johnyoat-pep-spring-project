package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account login(String username,String Passowrd){
        return accountRepository.findAccountByUsernameAndPassword(username, Passowrd);
    }

    public Account getAccountById(int accountId){
        return accountRepository.findById(accountId);
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findAccountByUsername(username);
    }

    public Account saveOrUpdate(Account account){
        return accountRepository.save(account);
    }

}
