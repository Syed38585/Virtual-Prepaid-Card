package com.vpc.TransactionController;

import com.vpc.Model.Transaction;
import com.vpc.TransactionService.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@CrossOrigin
public class TController {

    @Autowired
    TranService service;

    @PostMapping("/funds")   // api to transfer funds
    public ResponseEntity<String> transfer(@RequestBody Transaction transaction){
        return new ResponseEntity<>(service.transfer(transaction.getSenderId(),
                transaction.getReceiverId(),
                transaction.getAmount()), HttpStatus.ACCEPTED);
    }

    @PostMapping("/refund")    // api to refund transaction
    public ResponseEntity<?> refund(@RequestBody Transaction transaction){
        service.refund(transaction.getId());
    return new ResponseEntity<>("DONE",HttpStatus.OK);
    }

    @GetMapping("/get/history/{id}")   // api to get history of transactions
    public List<Transaction> getHistory(@PathVariable long id){
        return service.getHistory(id);
    }
}
