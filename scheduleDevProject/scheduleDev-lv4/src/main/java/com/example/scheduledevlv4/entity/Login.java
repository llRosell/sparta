package com.example.scheduledevlv4.entity;

import jakarta.persistence.*;
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

}
