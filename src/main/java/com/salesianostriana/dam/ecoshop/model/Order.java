package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
	
	@NotEmpty(message = "Order code is required")
	@Size(min = 3, max = 20, message = "Order code must be between 3 and 20 characters")
	private String code;
	
	@Future(message = "Shipping date must be in the future")
	@NotNull(message = "Shipping date is required")
	private LocalDate shippingDate;//en lugar de localdatetime
	
	@PositiveOrZero(message = "Order total cannot be negative")
	private double total;
	
	/*@NotEmpty(message = "Order status is required")
	@Size(max = 30, message = "Status cannot exceed 30 characters")*/
	private String status;
	
	
	private LocalDate returnDate;//en lugar de localdatetime
	
	
	//relación con customer
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey (name = "customer_id"))
    @NotNull(message = "Order must be associated with a customer")
    private Customer customer;

    //relación con líneas
    @OneToMany(mappedBy = "order", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final List<OrderLine> lines = new ArrayList<>();
}
