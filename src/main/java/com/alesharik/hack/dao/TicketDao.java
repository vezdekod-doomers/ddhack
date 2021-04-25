package com.alesharik.hack.dao;

import com.alesharik.hack.data.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDao {
    private long id;

    private String fio;

    private String phone;

    private String message;

    private LocalDateTime createDate;

    private TicketStatus status;

    private LocalDateTime closeDate;

    private String comment;
}
