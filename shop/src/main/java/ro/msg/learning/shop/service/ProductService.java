package ro.msg.learning.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public void createProduct(Product product){
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product updatedProduct){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new EntityNotFoundException("ID " + id + " not found. Nothing to update.");
        }
        Product existingProduct = product.get();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setWeight(updatedProduct.getWeight());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setSupplier(updatedProduct.getSupplier());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());

        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id){
        Optional<Product> productToDelete = productRepository.findById(id);
        if(productToDelete.isEmpty()){
            throw new EntityNotFoundException("ID " + id + " not found. Nothing to delete.");
        }
        productRepository.delete(productToDelete.get());
    }
}
