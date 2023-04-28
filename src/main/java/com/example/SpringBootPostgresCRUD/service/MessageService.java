package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        messageRepository.findAll().forEach(MessageList::add);

        return MessageList;
    }

    public Message getMessageById(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            throw new NoSuchElementException("No se encontró Message con id " + id);
        }
    }

    public boolean saveOrUpdateMessage(Message Message) {
        Message msg = messageRepository.save(Message);
        boolean res = false;
        if (messageRepository.findById(msg.getId()).isPresent()) {
            res =  true;
        }
        return res;
    }

    public boolean deleteMessage(Long id) {
        messageRepository.deleteById(id);
        boolean res= false;
        if (messageRepository.findById(id).isPresent()) {
            res=true;
        }
        return res;
    }

    public List<Message> findBySenderOrReceiverEmail(String email) {
        return messageRepository.findBySenderOrReceiverEmail(email);
    }

    public List<Message> getByPreviousChat(String email1, String email2) {
        return messageRepository.findByPreviousChat(email1, email2);
    }

    public List<Message> getAllMessagesBySenderAndReceiver(String email1, String email2) {
        List<Message> lista1 = messageRepository.findAllMessagesBySenderAndReceiver(email1, email2);
        List<Message> lista2 = messageRepository.findAllMessagesBySenderAndReceiver(email2, email1);
        lista1.addAll(lista2);
        return lista1;
    }

}
