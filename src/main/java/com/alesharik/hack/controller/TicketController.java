package com.alesharik.hack.controller;

import com.alesharik.hack.dao.PutTicketDao;
import com.alesharik.hack.dao.TicketDao;
import com.alesharik.hack.dao.TicketListDao;
import com.alesharik.hack.data.Ticket;
import com.alesharik.hack.data.TicketRepository;
import com.alesharik.hack.data.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketRepository repository;

    @PutMapping("/add")
    public void put(@RequestBody PutTicketDao dao) {
        var entity = new Ticket();
        entity.setFio(dao.getFio());
        entity.setMessage(dao.getMessage());
        entity.setPhone(dao.getPhone());
        entity.setCreateDate(LocalDateTime.now());
        entity.setStatus(TicketStatus.OPEN);
        repository.save(entity);
    }

    @GetMapping
    public TicketListDao get(
            @RequestParam int count,
            @RequestParam int page,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) TicketStatus[] statuses,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String phone
    ) {
        var list = statuses == null ? Arrays.asList(TicketStatus.values()) : Arrays.asList(statuses);
        var found = repository.findAllToList(from, to, list, id, phone, PageRequest.of(page, count));
        var collect = found
                .stream()
                .map(this::mapDao)
                .collect(Collectors.toList());
        var ticketListDao = new TicketListDao();
        ticketListDao.setList(collect);
        ticketListDao.setTotalPages(found.getTotalPages());
        return ticketListDao;
    }

    private TicketDao mapDao(Ticket ticket) {
        var dao = new TicketDao();
        dao.setFio(ticket.getFio());
        dao.setMessage(ticket.getMessage());
        dao.setPhone(ticket.getPhone());
        dao.setCreateDate(ticket.getCreateDate());
        dao.setStatus(ticket.getStatus());
        dao.setId(ticket.getId());
        dao.setComment(ticket.getComment());
        dao.setCloseDate(ticket.getCloseDate());
        return dao;
    }

    @GetMapping("/get")
    public TicketDao getById(@RequestParam long id) {
        return mapDao(repository.findById(id).get());
    }

    @PostMapping("/close")
    public void close(
            @RequestParam long id,
            @RequestParam String comment
    ) {
        repository.findById(id).ifPresent(ticket -> {
            ticket.setStatus(TicketStatus.CLOSED);
            ticket.setComment(comment);
            ticket.setCloseDate(LocalDateTime.now());
            repository.save(ticket);
        });
    }

//    @GetMapping("/stats")
//    public
}
