package com.vpc.TransactionService;

import com.vpc.Model.CardModel;
import com.vpc.Repo.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LimitResetService {

    @Autowired
    CardRepo cardRepo;


    //method to schedule auto limit reset
    @Scheduled(cron = " 0 0 0 * * *", zone = "Asia/Kolkata")
    public void resetLimit(){
        List<CardModel> cardModelList = cardRepo.findAll();
        for(CardModel card : cardModelList){
            card.setSpent(BigDecimal.ZERO);
            cardRepo.save(card);
        }
        System.out.println("LIMIT RESET DONE SUCCESSFULLY");
    }

}