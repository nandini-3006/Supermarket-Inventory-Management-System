package com.nandini.inventory.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/stock-in")
    public ResponseEntity<ProductEntity> stock_in(@RequestBody ProductRequestDTO body) {
        try {
            var product = productService.addProduct(body.getPrice(), body.getQuantity(), body.getBrand(),body.getName(),body.getExpiry());
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
@PostMapping("/billing")
public ResponseEntity<String> billing(@RequestBody BillingListDTO body) {
    try {
       productService.billing(body.getList());
        return ResponseEntity.ok("Billing Successful");
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
}
