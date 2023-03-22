package com.example.SpringBootPostgresCRUD.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringBootPostgresCRUD.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT message FROM Message message WHERE message.userReceiver.email= :email ORDER BY message.date")
    public List<Message> findByReceiverEmail(@Param("email") String email);

    @Query("SELECT message FROM Message message WHERE message.userSender.email= :email ORDER BY message.date")
    public List<Message> findBySenderEmail(@Param("email") String email);

    @Query("SELECT message FROM Message message WHERE message.userSender.email= :email or message.userReceiver.email=:email ORDER BY message.date")
    public List<Message> findBySenderOrReceiverEmail(@Param("email") String email);

    @Query("SELECT message FROM Message message WHERE (message.userReceiver.email= :email1 and message.userSender.email=:email2) or (message.userReceiver.email= :email2 and message.userSender.email=:email1) ORDER BY message.date")
    public List<Message> findByPreviousChat(@Param("email1") String email1, @Param("email2") String email2);

    @Query("SELECT message FROM Message message WHERE message.userReceiver.email= :email1 and message.userSender.email=:email2 ORDER BY message.date")
    public List<Message> findAllMessagesBySenderAndReceiver(@Param("email1") String email1,
            @Param("email2") String email2);
}
