package com.vpc.TransactionService;

import com.vpc.Dto.TwilioDto;
import com.vpc.Feign.MsgFeign;
import com.vpc.Model.CardModel;
import com.vpc.Model.Transaction;
import com.vpc.Repo.CardRepo;
import com.vpc.Repo.TransactionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TranService {

    @Autowired
    TransactionRepo repo;

    @Autowired
    CardRepo cardRepo;

    @Autowired
    MsgFeign msgFeign;

    //method to transfer funds
    @Transactional
    public String transfer(long senderId, long receiverId, BigDecimal amount){
        CardModel sender = cardRepo.findById(senderId).orElseThrow(() ->  //find the sender in DB
                new RuntimeException("SENDER NOT FOUND"));
        if (receiverId <= 0) {
            throw new IllegalArgumentException("Invalid receiver ID: " + receiverId);
        }
        CardModel receiver = cardRepo.findById(receiverId).orElseThrow(() ->  // find the receiver in DB
                new RuntimeException("RECEIVER NOT FOUND"));
        if(sender.getBalance().compareTo(amount)<0){
            return "INSUFFICIENT FUNDS";
        }else if(sender.isLocked()){
            return "SENDER ACCOUNT IS LOCKED";
        }else if(receiver.isLocked()){
            return "RECEIVER ACCOUNT IS LOCKED";
        }
        sender.setBalance(sender.getBalance().subtract(amount));    //deduct the amount from sender card
            receiver.setBalance(receiver.getBalance().add(amount));  //add the amount to receiver card
            cardRepo.save(sender);    //save the transaction in DB
            cardRepo.save(receiver);


            Transaction transaction = new Transaction();  // keep a record of transaction
            transaction.setSenderId(senderId);
            transaction.setReceiverId(receiverId);
            transaction.setAmount(amount);
            transaction.setDateTime(LocalDateTime.now());
            transaction.setMode("TRANSFER");

            repo.save(transaction);

        TwilioDto senderMsg = new TwilioDto();    // sends SMS after transaction
        senderMsg.setPhoneNo(sender.getPhoneNo());
        senderMsg.setContent("Dear [" + sender.getName() + "], ₹[" + amount + "] has been successfully transferred to [" + receiver.getName() + " ] on [" + transaction.getDateTime() + " ]. " +
                "Transaction ID: [" + transaction.getId()  + " ]. Thank you for using [Your Company Name].\n");

        TwilioDto receiverMsg = new TwilioDto();

        receiverMsg.setPhoneNo(receiver.getPhoneNo());
        receiverMsg.setContent("Dear [" + receiver.getName() + "], ₹[" + amount + "] has been successfully credited by  [" + sender.getName() + " ] on [" + transaction.getDateTime() + " ]. " +
                "Transaction ID: [" + transaction.getId()  + " ]. Thank you for using [Your Company Name].\n");

        msgFeign.TransactionSms(senderMsg);
        msgFeign.TransactionSms(receiverMsg);

        return "TRANSACTION DONE";
    }


    //method for refund
    public void refund(long id) {
        Transaction tid = repo.findById(id).orElseThrow(() ->
                new RuntimeException("TRANSACTION HISTORY NOT FOUND"));
        BigDecimal amountToBeRefunded = tid.getAmount();
        if(tid!=null) {
            long senderId = tid.getSenderId();
            long receiverId = tid.getReceiverId();
            CardModel sender = cardRepo.findById(senderId).orElseThrow(() ->
                    new RuntimeException("SENDER NOT FOUND"));
            CardModel receiver = cardRepo.findById(receiverId).orElseThrow(() ->
                    new RuntimeException("RECEIVER NOT FOUND"));
            sender.setBalance(sender.getBalance().add(amountToBeRefunded));
            receiver.setBalance(receiver.getBalance().subtract(amountToBeRefunded));
            Transaction transaction = new Transaction();
            transaction.setSenderId(senderId);
            transaction.setReceiverId(receiverId);   // keep record of the transactions
            transaction.setAmount(amountToBeRefunded);
            transaction.setDateTime(LocalDateTime.now());
            transaction.setMode("REFUND");

            TwilioDto twilioDto = new TwilioDto();    // send SMS after the refund
            twilioDto.setPhoneNo(sender.getPhoneNo());
            twilioDto.setContent("Dear [" + sender.getName() + " ], your refund of ₹[" + amountToBeRefunded + "] for [Product/Service] has been processed on [" + transaction.getDateTime() + "]. " +
                    "It will reflect in your account within [3] working days. Thank you for your patience.\n");

            twilioDto.setPhoneNo(receiver.getPhoneNo());
            twilioDto.setContent("Dear [" + receiver.getName() + " ], refund of ₹[" + amountToBeRefunded + "] has been debited for [Product/Service] has been processed on [" + transaction.getDateTime() + "]. " +
                    "It will reflect in your account within [3] working days. Thank you for your patience.\n");

            repo.save(transaction);   // save the changes in DB
        }
    }

    //method to get the transaction history
    public List<Transaction> getHistory(long id) {
        return repo.findBySenderIdOrReceiverId(id,id);
    }
}
