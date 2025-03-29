package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends MovieModel {
	private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
