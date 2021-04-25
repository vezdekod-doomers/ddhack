package com.alesharik.hack.data;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue
    private long id;

    private String fio;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime closeDate;

    private TicketStatus status;

    private String comment;
}
