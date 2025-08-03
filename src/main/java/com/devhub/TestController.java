package com.devhub;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    // Insecure endpoint for testing: No auth, no input validation
    @GetMapping("/")
    public String test() {
        return "::: Controller Tested ::: ";
    }

    // Simulated SQL Injection vulnerable endpoint (for static code scanners)
    @GetMapping("/user")
    public String getUser(@RequestParam String username) {
        // Simulate SQL injection pattern (not actually executing DB logic)
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        return "Running query: " + query;
    }

    // Simulated XSS vulnerable endpoint
    @PostMapping("/comment")
    public String postComment(@RequestParam String comment) {
        // Reflected output (XSS risk if rendered on frontend without sanitization)
        return "You posted: " + comment;
    }

    // Simulated endpoint for insecure info disclosure
    @GetMapping("/debug")
    public Map<String, Object> debugInfo() {
        Map<String, Object> debug = new HashMap<>();
        debug.put("java.version", System.getProperty("java.version"));
        debug.put("os.name", System.getProperty("os.name"));
        debug.put("user.dir", System.getProperty("user.dir"));
        debug.put("secretKey", "hardcoded-secret-123"); // not recommended
        return debug;
    }

    // Simulated endpoint with missing CSRF protection
    @PostMapping("/delete")
    public String deleteItem(@RequestParam int id) {
        return "Item with id " + id + " deleted (no CSRF protection simulated)";
    }
}
