package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
public class AccountController {

    private AccountService accountService;
    private MessageService messageService;


    @Autowired
    public AccountController(AccountService accountService,MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseEntity<Account> login(@RequestBody Account accountBody){
            Account account = accountService.login(accountBody.getUsername(), accountBody.getPassword());

            if (account != null) {
                return ResponseEntity.ok(account);
            }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @RequestMapping(value = "accounts/{account_id}/messages",method = RequestMethod.GET)
    public ResponseEntity<List<Message>> accountMessages(@PathVariable int account_id){
        return ResponseEntity.ok(messageService.findMessagesByAccountId(account_id));
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResponseEntity<Account> registerUser(@RequestBody Account userAccount){
        Account account = accountService.getAccountByUsername(userAccount.getUsername());

        if (account != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (isAccountValid(userAccount)) {
            return ResponseEntity.ok(accountService.saveOrUpdate(userAccount));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    private boolean isAccountValid(Account account){
        return !account.getUsername().isBlank() && account.getPassword().length() > 4;
    }
}
