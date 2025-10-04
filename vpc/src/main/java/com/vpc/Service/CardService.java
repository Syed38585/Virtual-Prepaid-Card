package com.vpc.Service;

import com.vpc.Dto.MailDto;
import com.vpc.Feign.MsgFeign;
import com.vpc.Model.CardModel;
import com.vpc.Model.UpdateDto;
import com.vpc.Repo.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepo repo;

   @Autowired
   MsgFeign msgFeign;

    @Value("${spring.mail.username}")
    public static String SENDER_MAIL;


    public String create(CardModel cardModel) {
        repo.save(cardModel);
        MailDto mailDto = new MailDto();    //create obj to send mail
        mailDto.setTo(cardModel.getEmail());
        mailDto.setFrom(SENDER_MAIL);
        mailDto.setContent("Dear [" + cardModel.getName() + "],\n" +
                "\n" +
                "Thank you for registering your card with us!\n" +
                "\n" +
                "We’re happy to confirm that your card ending in ****[" + cardModel.getAccountNo().substring(7) + "] has been successfully added to your account. You can now enjoy seamless transactions, faster checkouts, and enhanced security across our services.\n" +
                "\n" +
                "If you didn’t initiate this registration or have any concerns, please contact our support team immediately at [Support Email or Phone Number].\n" +
                "\n" +
                "Welcome aboard, and thank you for choosing [Your Company Name]!\n" +
                "\n" +
                "Warm regards,  \n" +
                "[Your Name or Team Name]  \n" +
                "[Company Name]  \n" +
                "[Contact Info]  \n");
        msgFeign.sendEmail(mailDto);   // method to send mail
        return "DONE";
    }

    public List<CardModel> getAllCards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);     // to get all cards using pagination
        Page<CardModel> cardPage = repo.findAll(pageable);
        return cardPage.getContent();
    }


    public Object getUserById(long id) {
        return repo.findById(id);
    }

    public String updateExpiryOf(long id) {
        CardModel cardModel = repo.findById(id).orElseThrow(() ->  // to find user
                new RuntimeException("USER NOT FOUND"));
        if(cardModel!=null) {
            if(cardModel.getExpiry().equals(LocalDate.now())) {   // to update expiry
                cardModel.setExpiry(LocalDate.now().plusYears(5));
                repo.save(cardModel);
                return "DONE";
            }else{
                return "CARD IS NOT EXPIRED";
            }
        }
        return "FAILED TO UPDATE";
    }

    public String updatePhoneNo(long id, UpdateDto updateDto) {
        CardModel cardModel = repo.findById(id).orElseThrow(() ->
                new RuntimeException("USER NOT FOUND"));
        if(cardModel!=null){
            cardModel.setPhoneNo(updateDto.getPhoneNo());
            repo.save(cardModel);
            return "DONE";
        }
        return "FAILED TO UPDATE";
    }

    public String deleteUser(long id) {
      CardModel user = repo.findById(id).orElseThrow(() ->   // find and delete user
              new RuntimeException("NULL"));
      if(user!=null){
          repo.delete(user);
          return "DONE";
      }
      return "ERROR";
    }

    public String topup(long id, BigDecimal amount) {
        CardModel user = repo.findById(id).orElseThrow(() ->   // add balance
                new RuntimeException("USER NOT FOUND"));
        if(user!=null){
            user.setBalance(user.getBalance().add(amount));
            return "DONE";
        }
        return "FAILED TO TOP UP";
    }
}
