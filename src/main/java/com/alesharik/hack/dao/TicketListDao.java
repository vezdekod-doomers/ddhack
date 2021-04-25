package com.alesharik.hack.dao;

import lombok.Data;

import java.util.List;

@Data
public class TicketListDao {
    private int totalPages;
    private List<TicketDao> list;
}
