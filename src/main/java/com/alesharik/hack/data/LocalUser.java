package com.alesharik.hack.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode
@Entity
@Data
@Table(indexes = {@Index(columnList = "login", name = "idx_local_user_login")})
public class LocalUser {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true)
    private UUID id;

    @Column(unique = true)
    private String login;

    private String password;

    private boolean admin;
}
