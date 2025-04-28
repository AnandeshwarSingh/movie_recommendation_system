package com.example.demo.Controller;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173",  // your React frontend URL
allowCredentials = "true")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        UserModel user = userService.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            session.setAttribute("user", user);
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out successfully";
    }

    @GetMapping("/currentUser")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("username", user.getName()); // or getName() depending on your UserModel
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
