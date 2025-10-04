package com.vpc.Repo;

import com.vpc.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
        List<Transaction> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
