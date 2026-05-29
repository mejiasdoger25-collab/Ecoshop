package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;
//import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderLineRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
public class OrderService extends BaseServiceImp <Order, Long, OrderRepository>{

	public OrderService(OrderRepository repository) {
        super(repository);
    }
	
	/*
	private final OrderRepository repository;
	
	
	public List<Order> getLista() {
		return Arrays.asList(
				new Order(1L, "123ABC", LocalDateTime.now(), 73.99, "sent", null, new Customer(), null),//último valor por poner
				new Order(2L, "121ABA", LocalDateTime.now(), 2.50, "sent", null, new Customer(), null)
				);		
	}
	*/
	
	//new sample with the new data model, ahora se usa el id con la clase pk, es decir, id(new OrderLinePK(order.getId(), nextLine)) en lugar de usar .id(1L)
	/*ya no se usa este method, ahora se usa el checkout para las líneas del pedido
	public OrderLine addProductToOrder(Order order,Product product, int amount) {
	    Long nextLine =(long) order.getLines().size() + 1;

	    OrderLine line = OrderLine.builder()
	            .id(new OrderLinePK(order.getId(), nextLine))
	            .amount(amount)
	            .unitPrice(product.getPrice())
	            .subTotal(product.getPrice() * amount)
	            .order(order)
	            .product(product)
	            .build();

	    order.getLines().add(line);

	    getRepository().save(order);

	    return line;
	}
	*/
	
	
	/*
	 * por ejemplo, si ahora hago: Order order = new Order();
	 * y despues lo uso asi: OrderLinePK pk = new OrderLinePK(order.getId(), 1L);
	 * esto va a petar porque order.getId() == null
	 * 
	 * entonces, con el nuevo modelo de datos con la clave compuesta y parcialmente generada, 
	 * ahora tendía que hacer: Order savedOrder = repository.save(order);
	 * para luego usarlo así: savedOrder.getId()
	 * */
	
}
