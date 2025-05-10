package com.example.demo.Controller;

import com.example.demo.Model.AdminModel;
import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.AdminService;
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

    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        
    	 // Check if Normal User
        UserModel user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user != null) {
            session.setAttribute("currentUser", user);
            return ResponseEntity.ok("User login successful");
        }
        
    	// Check if Admin
        AdminModel admin = adminService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (admin != null) {
            session.setAttribute("currentAdmin", admin);
            return ResponseEntity.ok("Admin login successful");
        }

        // If neither found
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out successfully";
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        AdminModel admin = (AdminModel) session.getAttribute("currentAdmin");
        if (admin != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("type", "admin");
            data.put("id",admin.getAdminID());
            data.put("username", admin.getAdminname());
            data.put("email", admin.getEmail());
            return ResponseEntity.ok(data);
        }

        UserModel user = (UserModel) session.getAttribute("currentUser");
        if (user != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("type", "user");
            data.put("id",user.getUserId());
            data.put("username", user.getName());
            data.put("email", user.getEmail());
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in");
    }

    // Step 1: Verify phone number and return email
    @PostMapping("/forgot-password")
    public ResponseEntity<?> getEmailByPhone(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("identifier");
        UserModel user = userService.getUserByPhoneNumber(phoneNumber);

        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("phoneNumber", user.getPhoneNumber());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone number not found.");
        }
    }

    // Step 2: Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        String newPassword = payload.get("newPassword");

        boolean success = userService.updatePasswordByPhoneNumber(phoneNumber, newPassword);
        if (success) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update password.");
        }
    }


}
