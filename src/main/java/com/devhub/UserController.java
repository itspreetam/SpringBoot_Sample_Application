package com.devhub;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

   // Safe hello endpoint
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    // Safe echo with sanitized input
    @GetMapping("/echo")
    public String echo(@RequestParam String input) {
        return "Echo: " + input.replaceAll("[<>]", ""); // minimal sanitization
    }

    // Safe response with secure headers
    @GetMapping("/headers")
    public ResponseEntity<String> secureHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Content-Type-Options", "nosniff");
        headers.add("X-Frame-Options", "DENY");
        headers.add("Content-Security-Policy", "default-src 'self'");
        headers.add("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        return new ResponseEntity<>("Secure headers set", headers, HttpStatus.OK);
    }

    // Dummy endpoint with safe cookie
    @GetMapping("/set-cookie")
    public ResponseEntity<String> secureCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "sessionId=secure123; HttpOnly; Secure; SameSite=Strict; Path=/");
        return new ResponseEntity<>("Secure cookie set", headers, HttpStatus.OK);
    }

    // Placeholder for encrypted data using strong algorithm
    @GetMapping("/encrypt")
    public String secureEncryption(@RequestParam String data) {
        return "Data encryption disabled in public API.";
    }

    // Simulated admin endpoint with access control
    @GetMapping("/admin")
    public ResponseEntity<String> secureAdminEndpoint(@RequestParam String user, @RequestHeader("X-ROLE") String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Welcome, Admin " + user, HttpStatus.OK);
    }

    // Safe user update with predefined allowed fields
    @PostMapping("/update")
    public String safeUpdate(@RequestBody UserUpdateRequest request) {
        return "Updated user: name=" + request.getName() + ", email=" + request.getEmail();
    }

    // DTO class for safe update
    public static class UserUpdateRequest {
        private String name;
        private String email;

        // Getters & Setters
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
    }
}
    
}
