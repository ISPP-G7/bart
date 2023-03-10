package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.Message;
import com.example.SpringBootPostgresCRUD.repo.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        List<Message> MessageList = new ArrayList<>();
        messageRepository.findAll().forEach(Message -> MessageList.add(Message));

        return MessageList;
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).get();
    }

    public boolean saveOrUpdateMessage(Message Message) {
        Message ars = messageRepository.save(Message);
        if (messageRepository.findById(ars.getId()) != null) {
            return true;
        }
        return false;
    }

    public boolean deleteMessage(Long id) {
        messageRepository.deleteById(id);
        if (messageRepository.findById(id) != null) {
            return true;
        }
        return false;
    }

    public List<Message> findByPreviousChat(String email1, String email2) {
        return messageRepository.findByPreviousChat(email1, email2);
    }

}
