package kr.ac.hansung.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.model.Product;
import kr.ac.hansung.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	@RequestMapping // /admin으로 request로 들어오면 이 메소드가 실행됨
	public String adminPage() {
		return "admin";
	}

	@RequestMapping("/productInventory")
	public String getProducts(Model model) { // controller -> model -> view

		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);

		return "productInventory";
	}

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.GET)
	public String addProduct(Model model) {

		Product product = new Product();

		model.addAttribute("컴퓨터");

		model.addAttribute("product", product);

		return "addProduct";
	}

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.POST)
	public String addProductPost(@Valid Product product, BindingResult result) { // spring에 의해 product 객체 자동 생성. 사용자가
																					// 입력한 것이 이 product 객체에 자동 넣어짐
																					// product.java에 정의된 product를 검증하고 그
																					// 결과가 bindingresult에 넘어간다.
		if (result.hasErrors()) {
			System.out.println("Form data has some errors");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // product.java에 정의된 message들이 출력
			}
			
			return "addProduct";
		}

		if (!productService.addProduct(product))
			System.out.println("Adding product cannot be done");

		return "redirect:/admin/productInventory"; // getProducts() 메소드를 다시 부름 -> 이 메소드에서 model에 집어 넣는 역할을 하기 때문 //새로고침
													// 역할을 해줌
	}

	@RequestMapping(value = "/productInventory/deleteProduct/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable int id) {

		if (!productService.deleteProduct(id)) {
			System.out.println("Deleting product cannot be done");
		}

		return "redirect:/admin/productInventory"; // getProducts() 메소드를 다시 부름 -> 이 메소드에서 model에 집어 넣는 역할을 하기 때문 //새로고침
													// 역할을 해줌
	}

	@RequestMapping(value = "/productInventory/updateProduct/{id}", method = RequestMethod.GET)
	public String updateProduct(@PathVariable int id, Model model) { // model 객체가 자동으로 생성되서 넘어옴

		Product product = productService.getProductById(id);

		model.addAttribute("product", product);

		return "updateProduct";
	}

	@RequestMapping(value = "/productInventory/updateProduct", method = RequestMethod.POST)
	public String updateProductPost(@Valid Product product, BindingResult result) { // spring에 의해 product 객체 자동 생성. 사용자가 입력한 것이 이 product 객체에 자동 넣어짐

		// System.out.println(product);
		
		if (result.hasErrors()) {
			System.out.println("Form data has some errors");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // product.java에 정의된 message들이 출력
			}
			
			return "updateProduct";
		}

		if (!productService.updateProduct(product))
			System.out.println("Updating product cannot be done");

		return "redirect:/admin/productInventory"; // getProducts() 메소드를 다시 부름 -> 이 메소드에서 model에 집어 넣는 역할을 하기 때문 //새로고침
													// 역할을 해줌
	}

}
