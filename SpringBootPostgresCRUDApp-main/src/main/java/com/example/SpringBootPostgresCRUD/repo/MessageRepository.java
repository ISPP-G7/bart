package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootPostgresCRUD.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
