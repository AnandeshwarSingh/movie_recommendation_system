package com.example.demo.Controller;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/current-user")
    public Object getCurrentUser(HttpSession session) {
        Object user = session.getAttribute("user");
        return user != null ? user : "No user logged in";
    }
}
