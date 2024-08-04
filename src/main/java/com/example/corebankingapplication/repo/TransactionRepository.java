package com.example.corebankingapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.corebankingapplication.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where " +
            "lower(t.transType) like concat('%', lower(?1) , '%') or " +
            "concat(t.id,'') like concat('%', ?1 , '%') or " +
            "concat(t.transAmt,'') like concat('%', ?1 , '%')")
    List<Transaction> search(String searchString);

}
