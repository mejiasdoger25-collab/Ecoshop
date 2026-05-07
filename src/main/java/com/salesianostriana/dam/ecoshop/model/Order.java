package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Order {

	private String code;
	private LocalDateTime shippingDate;
	private double total;
	private String status;
	private LocalDateTime returnDate;
	
	
	//relación con customer
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey (name = "customer_id"))
    private Customer customer;

    //relación con líneas
    @OneToMany(mappedBy = "order", fetch=FetchType.EAGER)
    @Builder.Default
    private final ArrayList<OrderLine> lines = new ArrayList<>();
}
