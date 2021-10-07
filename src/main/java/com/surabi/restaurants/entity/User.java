package com.surabi.restaurants.entity;
import com.sun.istack.NotNull;
import com.surabi.restaurants.Enum.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    @NotEmpty(message = "Id must not be null")
    @Column(length=50)
    private String username;

    @NotNull
    private String password;

    @NotNull
    boolean enabled;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;


    public User(String sk, String sk1, boolean enabled, GrantedAuthority authority) {
    }
}
