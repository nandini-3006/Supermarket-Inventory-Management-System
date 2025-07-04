package com.nandini.inventory.products;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public ProductEntity addProduct(Double price,Long quantity,String brand,String name,LocalDate expiry){
        ProductEntity product = new ProductEntity();
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setBrand(brand);
        product.setName(name);
        product.setExpiry(expiry);
        return productRepository.save(product);
    }
public void billing(List<BillingDTO> list){
        int size=list.size();
        for(int i=0;i<size;i++){
        BillingDTO product =list.get(i);
        ProductEntity set=productRepository.findFirstByNameAndBrandOrderByExpiryAsc(product.getName(),product.getBrand()).orElseThrow(() -> new RuntimeException("Product not found"));
        set.setQuantity(set.getQuantity()-product.getQuantity());
            productRepository.save(set);
        }
}
}
