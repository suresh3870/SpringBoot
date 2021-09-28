package com.surabi.restaurants.entity;
import com.sun.istack.NotNull;
import com.surabi.restaurants.Enum.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    @NotNull
    @Column(length=50)
    private String username;

    @NotNull
    private String password;

    @NotNull
    boolean enabled;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

}
