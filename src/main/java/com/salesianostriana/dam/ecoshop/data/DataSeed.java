package com.salesianostriana.dam.ecoshop.data;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.repository.ProductRepository;
import com.salesianostriana.dam.ecoshop.security.user.Role;
import com.salesianostriana.dam.ecoshop.security.user.User;
import com.salesianostriana.dam.ecoshop.security.user.UserRepository;
import com.salesianostriana.dam.ecoshop.model.Category;
import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.repository.CategoryRepository;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String ... args) throws Exception {

    	//users & customers
        if (userRepository.count() == 0) {
            createDefaultUsersAndCustomers();
        }

        //categories 
        if (categoryRepository.count() == 0) {
            createDefaultCategories();
        }

        //products 
        if (productRepository.count() == 0) {
            createDefaultProducts();
        }
    }

    
    //users & customers
    private void createDefaultUsersAndCustomers() {

	        User admin = userRepository.save(
	                User.builder()
	                        .username("admin")
	                        .password(passwordEncoder.encode("admin"))
	                        .role(Role.ADMIN)
	                        .build()
	        );

	        User user = userRepository.save(
	                User.builder()
	                        .username("user")
	                        .password(passwordEncoder.encode("user"))
	                        .role(Role.USER)
	                        .build()
	        );

	        User vip = userRepository.save(
	                User.builder()
	                        .username("vip")
	                        .password(passwordEncoder.encode("vip"))
	                        .role(Role.VIP)
	                        .build()
	        );

	        customerRepository.save(
	                Customer.builder()
	                        .name("Admin System")
	                        .email("admin@ecoshop.com")
	                        .phone("111111111")
	                        .registrationDate(LocalDateTime.now())
	                        .totalSpent(0)
	                        .vip(false)
	                        .balance(0)
	                        .user(admin)
	                        .build()
	        );

	        customerRepository.save(
	                Customer.builder()
	                        .name("User Normal")
	                        .email("user@ecoshop.com")
	                        .phone("222222222")
	                        .registrationDate(LocalDateTime.now())
	                        .totalSpent(0)
	                        .vip(false)
	                        .balance(0)
	                        .user(user)
	                        .build()
	        );

	        customerRepository.save(
	                Customer.builder()
	                        .name("VIP Premium")
	                        .email("vip@ecoshop.com")
	                        .phone("333333333")
	                        .registrationDate(LocalDateTime.now())
	                        .totalSpent(0)
	                        .vip(true)
	                        .balance(0)
	                        .user(vip)
	                        .build()
	        );

	    
    }

    
    
    
  //products
    private void createDefaultProducts() {
        
    	Category food =
    		    categoryRepository.findByName("Alimentación").get();

    		Category tech =
    		    categoryRepository.findByName("Tecnología").get();

    		Category fashion =
    		    categoryRepository.findByName("Moda").get();

    		Category home =
    		    categoryRepository.findByName("Hogar").get();
    	
        List<Product> products = List.of(
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
                        .image("https://picsum.photos/id/1080/600/400")   // Naranjas
                        .category(food)
                        .build(),

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
                        .image("https://picsum.photos/id/106/600/400")    // Botella / agua
                        .category(home)
                        .build(),

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
                        .image("https://picsum.photos/id/201/600/400")    // Teléfono / móvil
                        .category(tech)
                        .build(),

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
                        .image("https://picsum.photos/id/1080/600/400")   // Manzanas (mismo que naranja, se ve bien)
                        .category(food)
                        .build(),

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
                        .image("https://picsum.photos/id/1060/600/400")   // Camiseta / ropa
                        .category(fashion)
                        .build(),

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
                        .image("https://picsum.photos/id/367/600/400")    // Auriculares
                        .category(tech)
                        .build(),

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
                        .image("https://picsum.photos/id/292/600/400")    // Leche / vaso de leche
                        .category(food)
                        .build(),

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
                        .image("https://picsum.photos/id/292/600/400")    // Chocolate
                        .category(food)
                        .build(),

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
                        .image("https://picsum.photos/id/1015/600/400")   // Detergente / limpieza
                        .category(home)
                        .build(),

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
                        .image("https://picsum.photos/id/292/600/400")    // Café
                        .category(food)
                        .build(),

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
                        .image("https://picsum.photos/id/180/600/400")    // Portátil / laptop
                        .category(tech)
                        .build(),

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
                        .image("https://picsum.photos/id/870/600/400")    // Miel / tarro de miel
                        .category(food)
                        .build()
        );

        productRepository.saveAll(products);
    }
    
    
    
    
    //default categories
    private void createDefaultCategories() {

        categoryRepository.save(
            Category.builder()
                .name("Alimentación")
                .build()
        );

        categoryRepository.save(
            Category.builder()
                .name("Tecnología")
                .build()
        );

        categoryRepository.save(
            Category.builder()
                .name("Moda")
                .build()
        );

        categoryRepository.save(
            Category.builder()
                .name("Hogar")
                .build()
        );
    }
    
}