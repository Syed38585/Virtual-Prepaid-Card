package com.vpc.Repo;

import com.vpc.Model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<CardModel,Long> {
}
