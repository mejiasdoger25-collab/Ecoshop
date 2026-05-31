package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderLineRepository;
import com.salesianostriana.dam.ecoshop.repository.ProductRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
public class ProductService extends BaseServiceImp <Product, Long, ProductRepository>{

	
	private final ProductRepository productRepository;
	private final List<Product> productList = new ArrayList<Product>();

	
	public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }
	
	
	//lógica de negocio
	
	//descuento del 10% a los productos que tienen ecoCertificate = true, aumento 10% si el stock es bajo
	public double getEffectivePrice(Product product) {
		double price = product.getPrice();

	    if(product.isEcoCertificate()) {
	        price *= 0.90;
	    }

	    if(product.getStock() < product.getMinimumStock()) {
	        price *= 1.10;
	    }

	    
	    //para elegir el de la oferta diaria con llamada al method
	    if(product.equals(getProductOfTheDay())) {
	        price *= 0.85;
	    }
	    
	    return price;
    }
	
	
	//para randomizar las cards products cada vez que se le da a total category
	public List<Product> findAllRandom() {

	    List<Product> products = productRepository.findAll();

	    Collections.shuffle(products);

	    return products;
	}
	
	
	//para randomizar las cards products cada vez que se le da a total category V2, manteniendo la paginación
	public Page<Product> findAllRandomPaged(Pageable pageable) {

	    List<Product> products = productRepository.findAll();

	    Collections.shuffle(products);

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), products.size());

	    List<Product> pageContent = products.subList(start, end);

	    return new PageImpl<>(pageContent, pageable, products.size());
	}
	
	//para la ""categoría"" (filtro) de products no eco en el sidebar
	public Page<Product> getNonEcoProducts(Pageable pageable) {
	    return productRepository.findByEcoCertificateFalse(pageable);
	}
	
	//para la ""categoría"" (filtro) de products ofertas en el sidebar 
	public Page<Product> getDiscountProducts(Pageable pageable) {

	    List<Product> products = findAll().stream()
	            .filter(product -> getEffectivePrice(product) < product.getPrice()).toList();

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), products.size());

	    return new PageImpl<>(
	            products.subList(start, end),
	            pageable,
	            products.size()
	    );
	}
	
	//para la ""categoría"" (filtro) de products stock bajo en el sidebar 
	public Page<Product> getLowStockProducts(Pageable pageable) {

	    List<Product> products = findAll().stream()
	            .filter(product ->
	                product.getStock() < product.getMinimumStock()
	            )
	            .toList();

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), products.size());

	    return new PageImpl<>(
	            products.subList(start, end),
	            pageable,
	            products.size()
	    );
	}
	
	//para el objt diario de oferta random
	public Product getProductOfTheDay() {

	    List<Product> products = findAll();

	    if(products.isEmpty())
	        return null;

	    int day = LocalDate.now().getDayOfYear();

	    return products.get(day % products.size());
	}
	
	/*
	public List<Product> getLista() {
		return Arrays.asList(
				new Product(1L, "Naranja", 3.99, 39, 10, "España", true, "Una fruta muy sabrosa", LocalDate.now(), null),//poner clave externa
				new Product(2L, "Lata reciclada", 2.99, 5, 1, "Francia", true, "Una lata hecha a base de reciclaje", null, null)
				);		
	}
	
	public void add (Product product) {
		productList.add(product);
	}
	
	*/
	
	/*
	@PostConstruct
	public void initUsers() {

	    if (productRepository.count() == 0) {

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Naranja")
	    	                .price(2)
	    	                .stock(10)
	    	                .minimumStock(5)
	    	                .origin("Fuengirola")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Naranjas frescas cultivadas de forma ecológica en la costa andaluza.
	    	                        Destacan por su sabor dulce y su alto contenido en vitamina C.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusWeeks(1))
	    	                .image("https://images.unsplash.com/photo-1580052614034-c55d20bfee3b")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Botella")
	    	                .price(1.20)
	    	                .stock(5000)
	    	                .minimumStock(100)
	    	                .origin("Madrid")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Botella reutilizable fabricada con materiales reciclados.
	    	                        Ideal para reducir el consumo de plástico de un solo uso.
	    	                        """)
	    	                .expirationDate(null)
	    	                .image("https://images.unsplash.com/photo-1602143407151-7111542de6e8")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Teléfono")
	    	                .price(500)
	    	                .stock(10)
	    	                .minimumStock(2)
	    	                .origin("China")
	    	                .ecoCertificate(false)
	    	                .description("""
	    	                        Smartphone de última generación con pantalla OLED y gran autonomía.
	    	                        Diseñado para ofrecer alto rendimiento en trabajo y entretenimiento.
	    	                        """)
	    	                .expirationDate(null)
	    	                .image("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Manzana")
	    	                .price(1.5)
	    	                .stock(80)
	    	                .minimumStock(20)
	    	                .origin("Lleida")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Manzanas rojas ecológicas recogidas en cultivos nacionales.
	    	                        Crujientes, dulces y perfectas para consumir a diario.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusWeeks(2))
	    	                .image("https://images.unsplash.com/photo-1567306226416-28f0efdc88ce")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Camiseta Básica")
	    	                .price(12.99)
	    	                .stock(150)
	    	                .minimumStock(25)
	    	                .origin("Portugal")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Camiseta confeccionada con algodón orgánico de alta calidad.
	    	                        Cómoda, transpirable y diseñada para uso diario.
	    	                        """)
	    	                .expirationDate(null)
	    	                .image("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Auriculares Bluetooth")
	    	                .price(45.5)
	    	                .stock(35)
	    	                .minimumStock(8)
	    	                .origin("China")
	    	                .ecoCertificate(false)
	    	                .description("""
	    	                        Auriculares inalámbricos con cancelación de ruido y sonido envolvente.
	    	                        Perfectos para música, llamadas y sesiones largas de uso.
	    	                        """)
	    	                .expirationDate(null)
	    	                .image("https://images.unsplash.com/photo-1505740420928-5e560c06d30e")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Leche Entera")
	    	                .price(1.1)
	    	                .stock(200)
	    	                .minimumStock(50)
	    	                .origin("Galicia")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Leche fresca procedente de vacas criadas en pastos naturales.
	    	                        Rica en calcio y con un sabor suave y natural.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusDays(10))
	    	                .image("https://images.unsplash.com/photo-1550583724-b2692b85b150")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Chocolate Negro 70%")
	    	                .price(2.8)
	    	                .stock(120)
	    	                .minimumStock(30)
	    	                .origin("España")
	    	                .ecoCertificate(false)
	    	                .description("""
	    	                        Chocolate negro premium con un intenso sabor a cacao.
	    	                        Elaborado con ingredientes seleccionados de alta calidad.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusMonths(8))
	    	                .image("https://images.unsplash.com/photo-1511381939415-e44015466834")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Detergente Ecológico")
	    	                .price(6.75)
	    	                .stock(90)
	    	                .minimumStock(15)
	    	                .origin("Madrid")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Detergente biodegradable respetuoso con el medio ambiente.
	    	                        Limpieza eficaz incluso a bajas temperaturas.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusMonths(18))
	    	                .image("https://images.unsplash.com/photo-1583947582886-f40ec95dd752")
	    	                .build()
	    	);



	    	// NUEVOS PRODUCTOS

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Café Natural")
	    	                .price(7.95)
	    	                .stock(60)
	    	                .minimumStock(15)
	    	                .origin("Colombia")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Café ecológico de tueste natural con aroma intenso.
	    	                        Ideal para preparar espresso o café filtrado.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusMonths(12))
	    	                .image("https://images.unsplash.com/photo-1495474472287-4d71bcdd2085")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Portátil")
	    	                .price(899.99)
	    	                .stock(12)
	    	                .minimumStock(3)
	    	                .origin("Alemania")
	    	                .ecoCertificate(false)
	    	                .description("""
	    	                        Ordenador portátil ultraligero con gran rendimiento multitarea.
	    	                        Perfecto para estudiantes, programación y trabajo remoto.
	    	                        """)
	    	                .expirationDate(null)
	    	                .image("https://images.unsplash.com/photo-1496181133206-80ce9b88a853")
	    	                .build()
	    	);

	    	productRepository.save(
	    	        Product.builder()
	    	                .name("Miel Ecológica")
	    	                .price(5.40)
	    	                .stock(75)
	    	                .minimumStock(20)
	    	                .origin("Extremadura")
	    	                .ecoCertificate(true)
	    	                .description("""
	    	                        Miel artesanal obtenida de flores silvestres naturales.
	    	                        Textura suave y sabor auténtico producido localmente.
	    	                        """)
	    	                .expirationDate(LocalDate.now().plusYears(2))
	    	                .image("https://images.unsplash.com/photo-1587049352851-8d4e89133924")
	    	                .build()
	    	);

	    }

	}
	*/
	
}
