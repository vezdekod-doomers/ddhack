package com.alesharik.hack.service;

import com.alesharik.hack.data.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketStatsService {
    private final TicketRepository ticketRepository;

//    public TicketStats calculateClosedFor(LocalDateTime from, LocalDateTime to, TicketStats.StatsPeriod period) {
//        ticketRepository.
//    }
}
