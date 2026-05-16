package com.salesianostriana.dam.ecoshop.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLinePK implements Serializable {

    private Long orderId;
    private Long lineNumber;

}