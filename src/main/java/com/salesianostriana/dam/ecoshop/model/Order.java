package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name ="orders")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final ArrayList<OrderLine> lines = new ArrayList<>();
}
