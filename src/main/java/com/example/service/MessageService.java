package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;


    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> findMessagesByAccountId(int accountId){
        return messageRepository.findMessagesByPostedBy(accountId);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }

        return null;
    }

    public void deleteMessageById(int messageId){
        messageRepository.deleteById(messageId);
    }

    public Message saveOrUpdateMessage(Message message){
        return messageRepository.save(message);
    }

    
}
