package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;;;

@RestController
@RequestMapping("messages")
public class MessageController {


    private MessageService messageService;
    private AccountService accountService;


    @Autowired
    public MessageController(MessageService messageService,AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }


    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestBody Message message){
        Account account = accountService.getAccountById(message.getPostedBy());

        if (account != null && isMessageValid(message)) {
            Message createdMessage = messageService.saveOrUpdateMessage(message);

            return ResponseEntity.ok(createdMessage);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("{message_id}")
    public ResponseEntity<Message> getMessage(@PathVariable int message_id){
        return ResponseEntity.ok(messageService.getMessageById(message_id));
    }


    @PatchMapping("{message_id}")
    public ResponseEntity<String> updateMessage(@PathVariable int message_id,@RequestBody Message messageBody){
        Message message = messageService.getMessageById(message_id);

        if (message != null && isMessageValid(messageBody)) {
            messageService.saveOrUpdateMessage(messageBody);
            return ResponseEntity.ok("1");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @DeleteMapping("{message_id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int message_id){
       Message message = messageService.getMessageById(message_id);
        if (message != null) {
            messageService.deleteMessageById(message_id);
            return ResponseEntity.ok("1");
        }
        return ResponseEntity.ok("");
    }


    private boolean isMessageValid(Message message){
        return (message.getMessageText().length() < 256 && !message.getMessageText().isBlank());
    }

}
