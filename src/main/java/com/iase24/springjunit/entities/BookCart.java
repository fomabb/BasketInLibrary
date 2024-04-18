package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference("bookCart-book")
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @JsonBackReference("bookCart-cart")
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_report")
    DeliveryReport deliveryReport;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "delivery_report_date")
    private LocalDateTime deliveryReportDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "report_on_the_event_date")
    private LocalDateTime reportOnTheEventDate;

    @Column(name = "status_delivery_id")
    private Integer statusDeliveryId;
}
