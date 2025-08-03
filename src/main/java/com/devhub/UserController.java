package com.devhub;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    // Insecure endpoint for testing: No auth, no input validation
    @GetMapping("/")
    public String user() {
        return "::: User Controller Tested ::: ";
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

    @GetMapping("/credentials")
public Map<String, String> credentialsLeak() {
    Map<String, String> credentials = new HashMap<>();
    credentials.put("username", "admin");
    credentials.put("password", "password123"); // hardcoded secret
    return credentials;
}

// 2. Insecure file path exposure (Directory Traversal simulation)
@GetMapping("/file")
public String readFile(@RequestParam String path) {
    return "Trying to read file from: /data/files/" + path;  // ../../etc/passwd
}

// 3. Command Injection simulation
@GetMapping("/ping")
public String pingHost(@RequestParam String host) {
    String cmd = "ping -c 1 " + host;
    return "Executing: " + cmd;  // Potential for injection like 127.0.0.1 && rm -rf /
}

// 4. Insecure redirect
@GetMapping("/redirect")
public String insecureRedirect(@RequestParam String url) {
    return "Redirecting to: " + url;  // Test with: http://evil.com
}

// 5. Insecure deserialization simulation
@PostMapping("/deserialize")
public String deserializeData(@RequestBody String serializedObject) {
    return "Simulated deserialization of: " + serializedObject;
    // Add an actual ObjectInputStream test if needed
}

// 6. Using eval (simulated, Java doesn't directly support it but it's flagged conceptually)
@GetMapping("/eval")
public String unsafeEval(@RequestParam String input) {
    return "Evaluating expression: " + input; // Simulate potential expression injection
}

// 7. Excessive logging (Information Leakage)
@GetMapping("/login")
public String login(@RequestParam String username, @RequestParam String password) {
    System.out.println("Login attempt with username: " + username + " and password: " + password);
    return "Login attempt logged";
}

@GetMapping("/headers")
public ResponseEntity<String> missingHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Custom-Header", "Test"); // missing CSP, X-Content-Type-Options, etc.
    return new ResponseEntity<>("Missing security headers", headers, HttpStatus.OK);
}
    
}
