package com.devhub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Controller
@RequestMapping("/user")
public class UserController {

    // Safe: Input is validated and sanitized
    @GetMapping("/greet")
    @ResponseBody
    public String greetUser(
            @RequestParam @NotBlank @Size(max = 50) String username) {
        
        // Sanitize the input to prevent XSS
        String safeUsername = HtmlUtils.htmlEscape(username);

        return "Hello, " + safeUsername + "! Welcome to DevHub.";
    }
}
