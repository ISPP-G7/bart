package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SpringBootPostgresCRUD.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT message FROM Message message WHERE message.userReceiver.email= :email")
    public List<Message> findByReceiverEmail(@Param("email") String email);

    @Query("SELECT message FROM Message message WHERE message.userSender.email= :email")
    public List<Message> findBySenderEmail(@Param("email") String email);

    @Query("SELECT message FROM Message message WHERE message.userSender.email= :email or message.userReceiver=:email")
    public List<Message> findBySenderOrReceiverEmail(@Param("email") String email);

    @Query("SELECT message FROM Message message WHERE (message.userReceiver.email= :email1 and message.userSender=:email2) or  (message.userReceiver.email= :email2 and message.userSender=:email1)")
    public List<Message> findByPreviousChat(@Param("email1") String email1, @Param("email2") String email2);

}
