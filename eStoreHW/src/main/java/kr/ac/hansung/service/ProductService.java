package kr.ac.hansung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.dao.ProductDao;
import kr.ac.hansung.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts() {
		
		return productDao.getProducts();
	}

	public boolean addProduct(Product product) {

		return productDao.addProduct(product); // 성공하면 1, 실패하면 0 return
	}

	public boolean deleteProduct(int id) {
		
		return productDao.deleteProduct(id); // 성공하면 1, 실패하면 0 return;
	}

	public Product getProductById(int id) {
		
		return productDao.getProductById(id);
	}

	public boolean updateProduct(Product product) {

		return productDao.updateProduct(product);
	}
}
