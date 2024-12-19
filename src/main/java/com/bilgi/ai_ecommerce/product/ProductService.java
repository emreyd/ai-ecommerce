package com.bilgi.ai_ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    // Add a new product
    public Product addProduct(Product product) {
        product.setCreatedAt(String.valueOf(LocalDateTime.now()));
        product.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        return productRepository.save(product);
    }


    // Update a product
    public Product updateProduct(String id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setStock(productDetails.getStock());
        existingProduct.setImages(productDetails.getImages());
        existingProduct.setUpdatedAt(String.valueOf(LocalDateTime.now()));

        return productRepository.save(existingProduct);
    }


    // Delete a product
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

}
