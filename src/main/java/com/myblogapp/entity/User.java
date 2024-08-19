package com.myblogapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})}

      )
public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String username;

    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
              joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

}
