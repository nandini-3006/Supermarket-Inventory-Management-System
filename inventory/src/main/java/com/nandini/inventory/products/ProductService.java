package com.nandini.inventory.products;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Service
public class ProductService {
    public ProductEntity addTask(Double price,Long quantity,String brand,String name,LocalDate expiry){
        ProductEntity product = new ProductEntity();
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setBrand(brand);
        product.setName(name);
        product.setExpiry(expiry);
       return product;
    }
}
