package com.vpc.Controller;

import com.vpc.Model.CardModel;
import com.vpc.Model.UpdateDto;
import com.vpc.Service.CardService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/card")
@CrossOrigin
public class CardController {

        @Autowired
        CardService service;


        @PostMapping("/create") //api to create Card
        public ResponseEntity<String> createCard(@RequestBody CardModel cardModel) {
            cardModel.setAccountNo(UUID.randomUUID().toString().substring(0,10));
            return new ResponseEntity<>(service.create(cardModel), HttpStatus.OK);
        }

        @GetMapping("/get")  //api to get all card
        public ResponseEntity<List<CardModel>> getAll(@RequestParam (defaultValue = "0") int page,
                                                      @RequestParam (defaultValue = "5") int size){
            System.out.println("RECEIVED");
            return new ResponseEntity<>(service.getAllCards(page,size),HttpStatus.OK);
        }

        @Cacheable(key = "card",value = "#id")
        @GetMapping("/get/{id}") // api to get single card (by id)
        public ResponseEntity<?> getById(@PathVariable long id){
            return new ResponseEntity<>(service.getUserById(id),HttpStatus.OK);
        }

        @PostMapping("/update/expiry/{id}")  // api to update expiry
        public ResponseEntity<String> updateExpiry(@PathVariable long id){
            return new ResponseEntity<>(service.updateExpiryOf(id),HttpStatus.OK);
        }

       @PatchMapping("/update/phoneNo/{id}")  // api to update phone number
       public ResponseEntity<String> updatePhoneNo(@PathVariable long id,
                                                   @RequestBody UpdateDto updateDto){
            return new ResponseEntity<>(service.updatePhoneNo(id,updateDto),HttpStatus.OK);
       }

       @PostMapping("/topup/{id}")  // api to add balance
       public ResponseEntity<?> topup(@PathVariable long id,
                                      @RequestParam BigDecimal amount){
            return new ResponseEntity<>(service.topup(id,amount),HttpStatus.OK);
       }

       @DeleteMapping("/delete/{id}")  // api to delete card
       public ResponseEntity<?> delete(@PathVariable long id){
            return new ResponseEntity<>(service.deleteUser(id),HttpStatus.OK);
       }
    }
