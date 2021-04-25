package com.alesharik.hack.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
    @Query("select ticket from Ticket ticket where " +
            "(cast(:from as date) is null or ticket.createDate >= :from) " +
            "and (cast(:to as date) is null or ticket.createDate <= :to)" +
            "and ticket.status in (:statuses) " +
            "and (:id is null or cast(ticket.id as java.lang.String) like %:id%)" +
            "and (:phone is null or ticket.phone like %:phone%)"
    )
    Page<Ticket> findAllToList(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("statuses") List<TicketStatus> statuses,
            @Param("id") String id,
            @Param("phone") String phone,
            Pageable pageable
    );

//    @Query("select new com.alesharik.hack.data.TicketStatsEntity(date_trunc(:period, ticket.createDate), count(ticket)) from Ticket ticket")
//    List<Ticket> findAllByStats(LocalDateTime from, LocalDateTime to, String period);
}
