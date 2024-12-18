package com.example.scheduledevlv5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login")
public class Login {

    @Id
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Login(String email) {
        this.email = email;
    }

}
