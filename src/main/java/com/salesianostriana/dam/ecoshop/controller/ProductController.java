package com.salesianostriana.dam.ecoshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.ecoshop.model.Category;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.repository.ProductRepository;
import com.salesianostriana.dam.ecoshop.service.CategoryService;
import com.salesianostriana.dam.ecoshop.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor 
@RequestMapping("/products")
public class ProductController {

	private final ProductService service;
	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	
	
	//para renderizar las categories
	@ModelAttribute("categories")
	public List<Category> categories() {
	    return categoryService.findAll();
	}
	
	/*
	@GetMapping({"/", "/home"})
    public String home() {
        return "home"; 
    }
	
	//test-1st-form
	@GetMapping("/newProduct")
	public String newCustomer (Model model) {
		model.addAttribute("product", new Product());
		return "newCustomerForm";
	}
	
	@PostMapping("/newProduct/submit")
	public String newCustomerSubmit (@ModelAttribute("produt") Product product) {
		service.save(product);
		return "redirect:/newCustomer";
	}
	*/
	
	@GetMapping({"/", "/list"})
	public String findAll( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size, Model model) {

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Product> productPage = productRepository.findAllPaged(pageable);

	    model.addAttribute("products", productPage);
	    return "products/list";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createForm(Model model) {
		
		//para settear la category de new product de una forma default
		Product product = new Product();
	    product.setCategory(new Category());
		
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.findAll());
		return "products/form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new/submit")
	public String save (@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "products/form";
		}
		
		
		//para evitar que pete si no le pones nada en el select
		if(product.getCategory() != null && product.getCategory().getId() != null) {
			 Category category =categoryService.findById(product.getCategory().getId()).get();
			 product.setCategory(category);
		}
		service.save(product);
		return "redirect:/products/list";
	}
	
	
	
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editForm (@PathVariable Long id, Model model) {
		
		Product product = service.findById(id).orElse(null);
		model.addAttribute("product", product);
		model.addAttribute("categories", categoryService.findAll());
		
		//se reutiliza el form de create new products
		return "products/form";
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String delete (@PathVariable Long id, Model model) {
		
		Optional<Product> product = service.findById(id);
		
		if(product.isPresent()) 
			service.delete(product.get());
		
		return "redirect:/products/list";
	}
	
	
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable Long id, Model model) {

	    Optional<Product> product = service.findById(id);

	    if(product.isEmpty())
	        return "redirect:/products/list";

	    model.addAttribute("product", product.get());

	    return "products/details";
	}
	
	
	
	
	//reutilizado para las categories
	@GetMapping("/category/{id}")
	public String productsByCategory(@PathVariable Long id, Pageable pageable, Model model) {
		Page<Product> page =
			    productRepository.findByCategory_Id(id,pageable);
		
		model.addAttribute("products", page);
		model.addAttribute("selectedCategory", id);
		
		return "products/list";
	}
}
