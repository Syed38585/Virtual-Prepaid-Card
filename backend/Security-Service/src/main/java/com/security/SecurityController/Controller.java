package com.security.SecurityController;

import com.security.Model.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/current/user")
    public long getUser(@AuthenticationPrincipal Users userDetails){
        return userDetails.getId();
    }
}
