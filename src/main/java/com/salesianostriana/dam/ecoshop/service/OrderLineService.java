package com.salesianostriana.dam.ecoshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.OrderLine;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Builder
public class OrderLineService {

	public List<OrderLine> getLista() {
		return (List<OrderLine>) OrderLine.builder()
				.id(1L)
				.amount(3)
				.unitPrice(5)
				.subTotal(15)
				.build();		
	}
}
