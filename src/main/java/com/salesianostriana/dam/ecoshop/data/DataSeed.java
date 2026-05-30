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
                        .image("https://i.imgur.com/MvKNqef.jpeg")   // Naranjas
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
                        .image("https://st4.depositphotos.com/13324256/24812/i/450/depositphotos_248129662-stock-photo-selective-focus-plastic-water-bottles.jpg")    // Botella / agua
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
                        .image("https://www.quonty.com/blog/wp-content/uploads/2018/05/moviles-ordenadores-baratos-62.jpg")    // Teléfono / móvil
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
                        .image("https://frutashrg.com/wp-content/uploads/2023/02/depositphotos_83696420-stock-photo-fuji-organic-apples.webp")   // Manzanas (mismo que naranja, se ve bien)
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
                        .image("https://infinitegarage.eu/cdn/shop/files/mini_copper_sport_500_camiseta_5_de92d4c4-5b63-4f1b-9d4b-d67fe8caaec6_grande.png?v=1774366051")   // Camiseta / ropa
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
                        .image("https://www.recordcase.de/bilder/cms/v-moda-s-80-lifestyle-image-5-category-image.webp")    // Auriculares
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
                        .image("https://foodhub.decorexpro.com/wp-content/uploads/2020/04/kak-opredelit-palmovoe-maslo-v-moloke.jpg")    // Leche / vaso de leche
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
                        .image("https://bomboneriapons.com/cdn/shop/collections/tabletas-de-cacao-de-origen-6222171.jpg?v=1751332591")    // Chocolate
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
                        .image("https://st3.depositphotos.com/4218696/35198/i/450/depositphotos_351983448-stock-photo-bio-organic-detergent-products-for.jpg")   // Detergente / limpieza
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
                        .image("https://st2.depositphotos.com/87847974/87415/i/450/depositphotos_874159632-stock-photo-coffee-bean-drink-morning-nature.jpg")    // Café
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
                        .image("https://es.reneelab.com/wp-content/uploads/sites/12/2019/01/pantalla-negra-del-ordenador-port%C3%A1til.jpg")    // Portátil / laptop
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
                        .image("https://panaldemiel.es/wp-content/uploads/2025/05/Miel_ecologica_al_por_mayor.webp")    // Miel / tarro de miel
                        .category(food)
                        .build(),    
                 
                Product.builder()
                        .name("Salsa de Tomate Artesanal")
                        .price(4.95)
                        .stock(60)
                        .minimumStock(8)
                        .origin("Italia")
                        .ecoCertificate(true)
                        .description("""
                                Salsa elaborada con tomates seleccionados y recetas tradicionales.
                                Ideal para pasta, pizzas y platos mediterráneos.
                                """)
                        .expirationDate(LocalDate.now().plusMonths(18))
                        .image("https://www.aceitesdeolivadeespana.com/wp-content/uploads/2021/05/Salsa-de-tomate-casera.jpg")
                        .category(food)
                        .build(),
                        
               Product.builder()
                        .name("Monitor UltraWide 34 Pulgadas")
                        .price(399.99)
                        .stock(12)
                        .minimumStock(2)
                        .origin("Corea del Sur")
                        .ecoCertificate(false)
                        .description("""
                                Monitor panorámico de alta resolución ideal para productividad y multitarea.
                                Incluye conexiones HDMI y DisplayPort.
                                """)
                        .expirationDate(null)
                        .image("https://shopee.sg/blog/wp-content/uploads/2022/10/caspar-camille-rubin-0qvBNep1Y04-unsplash-1-e1664781436384.jpg")
                        .category(tech)
                        .build(),
                        
              Product.builder()
                        .name("Chaqueta Vaquera")
                        .price(59.95)
                        .stock(22)
                        .minimumStock(4)
                        .origin("Turquía")
                        .ecoCertificate(false)
                        .description("""
                                Chaqueta vaquera clásica confeccionada con tejido resistente.
                                Una prenda versátil para cualquier temporada.
                                """)
                        .expirationDate(null)
                        .image("https://cdn.create.vista.com/api/media/small/251710992/stock-photo-pretty-beautiful-young-woman-blonde-in-a-black-dress-in-a-long-denim-jacket-with")
                        .category(fashion)
                        .build(),
                        
              Product.builder()
                        .name("Gafas de Sol Polarizadas")
                        .price(44.90)
                        .stock(35)
                        .minimumStock(5)
                        .origin("España")
                        .ecoCertificate(false)
                        .description("""
                                Gafas con protección UV y lentes polarizadas.
                                Diseñadas para ofrecer comodidad y protección visual.
                                """)
                        .expirationDate(null)
                        .image("https://lh6.googleusercontent.com/proxy/k9Q8J3Cj2wTtgnjeAU6kqj-CYYOfQgHJLOKzYHUtiMTiAItky3jF29TM8lhher-hOMafHmK4lHBGTp6VUpzYGz39mHet0XyRqRgQt532vMV9bF5njooSQV5pOJTIVJKaKzimTQBqF7-WlBgPwME")
                        .category(fashion)
                        .build(),
                        
              Product.builder()
                        .name("Robot Aspirador")
                        .price(229.99)
                        .stock(14)
                        .minimumStock(2)
                        .origin("China")
                        .ecoCertificate(false)
                        .description("""
                                Robot aspirador programable con sensores inteligentes de navegación.
                                Facilita la limpieza diaria del hogar.
                                """)
                        .expirationDate(null)
                        .image("https://www.lacasadelelectrodomestico.com/public/storage/producto/174276/xiaowa-vacuum-cleaner-lite-c102-robot-aspirador-programable-0026314-600px.jpg")
                        .category(home)
                        .build(),
                        
              Product.builder()
                        .name("Estantería de Bambú")
                        .price(79.90)
                        .stock(18)
                        .minimumStock(3)
                        .origin("Vietnam")
                        .ecoCertificate(true)
                        .description("""
                                Estantería fabricada con bambú resistente y de crecimiento sostenible.
                                Perfecta para organizar libros, plantas y decoración.
                                """)
                        .expirationDate(null)
                        .image("https://st3.depositphotos.com/22220764/35742/i/450/depositphotos_357420464-stock-photo-modern-composition-living-room-design.jpg")
                        .category(home)
                        .build(),
                        
               Product.builder()
                        .name("Crema de Cacahuete Natural")
                        .price(6.50)
                        .stock(45)
                        .minimumStock(6)
                        .origin("España")
                        .ecoCertificate(false)
                        .description("""
                                Crema elaborada exclusivamente con cacahuetes tostados.
                                Rica en proteínas y perfecta para desayunos y meriendas.
                                """)
                        .expirationDate(LocalDate.now().plusMonths(10))
                        .image("https://st2.depositphotos.com/1007298/11090/i/450/depositphotos_110905796-stock-photo-creamy-peanut-butter-and-peanuts.jpg")
                        .category(food)
                        .build(),
                        
               Product.builder()
                        .name("Reloj de Madera")
                        .price(89.99)
                        .stock(15)
                        .minimumStock(3)
                        .origin("Canadá")
                        .ecoCertificate(true)
                        .description("""
                                Reloj fabricado con madera sostenible y correa ajustable.
                                Combina diseño elegante y materiales respetuosos con el medio ambiente.
                                """)
                        .expirationDate(null)
                        .image("https://i.pinimg.com/736x/b0/4d/23/b04d23d5d4d680f9a520d57b7bb5d3be.jpg")
                        .category(fashion)
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