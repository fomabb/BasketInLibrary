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
@Table(name = "products_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference("bookCart-product")
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @JsonBackReference("bookCart-order")
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_report")
    private DeliveryReport deliveryReport;

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
