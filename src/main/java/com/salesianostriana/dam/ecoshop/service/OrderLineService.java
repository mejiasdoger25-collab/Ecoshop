package com.salesianostriana.dam.ecoshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderLineRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
public class OrderLineService extends BaseServiceImp <OrderLine, OrderLinePK, OrderLineRepository>{

	public OrderLineService(OrderLineRepository repository) {
        super(repository);
    }
	
	/*
	private final OrderLineRepository repository;
	
	public List<OrderLine> getLista() {
		return (List<OrderLine>) OrderLine.builder()
				.id(new OrderLinePK(orderId, lineNumber))
				.amount(3)
				.unitPrice(5)
				.subTotal(15)
				.build();		
	}
	*/
}
