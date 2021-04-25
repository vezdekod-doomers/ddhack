package com.alesharik.hack.dao;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketStats {
    private List<TicketStatsPoint> points;

    @Data
    public static class TicketStatsPoint {
        private LocalDateTime date;

        private int value;
    }

    public enum StatsPeriod {
        SECOND,
        MINUTE,
        HOUR,
        DAY
    }
}
