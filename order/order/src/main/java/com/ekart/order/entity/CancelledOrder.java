package com.ekart.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CancelledOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private List<String> productIds;
    private long orderId;}
