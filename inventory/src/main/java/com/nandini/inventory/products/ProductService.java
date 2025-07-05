package com.nandini.inventory.products;

import com.nandini.inventory.profit.ProfitRepository;
import com.nandini.inventory.sales.SalesEntity;
import com.nandini.inventory.sales.SalesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final ProfitRepository profitRepository;
    public ProductService(ProductRepository productRepository,SalesRepository salesRepository,ProfitRepository profitRepository) {
        this.productRepository = productRepository;
        this.salesRepository=salesRepository;
        this.profitRepository=profitRepository;
    }
    public ProductEntity addProduct(Double price,Long quantity,String brand,String name,LocalDate expiry,Double profit){
        ProductEntity product = new ProductEntity();
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setBrand(brand);
        product.setName(name);
        product.setExpiry(expiry);
        product.setProfit(profit);
        return productRepository.save(product);
    }

    public void billing(List<BillingDTO> list){
        int size=list.size();
        for(int i=0;i<size;i++){
        BillingDTO product =list.get(i);
        ProductEntity set=productRepository.findFirstByNameAndBrandOrderByExpiryAsc(product.getName(),product.getBrand()).orElseThrow(() -> new RuntimeException("Product not found"));
        set.setQuantity(set.getQuantity()-product.getQuantity());
        if(set.getQuantity()<=0){
            productRepository.delete(set);
        }
            productRepository.save(set);
            Optional<SalesEntity> set2 = salesRepository.findByNameAndBrandAndExpiry(set.getName(),set.getBrand(),set.getExpiry());
            if(set2.isPresent()){
                SalesEntity sale = set2.get();
                sale.setQuantity(sale.getQuantity()+product.getQuantity());
                sale.setTotal(sale.getTotal() + product.getQuantity() * set.getPrice());
                salesRepository.save(sale);
            }
            else{
                SalesEntity sale = new SalesEntity();
                sale.setPrice(set.getPrice());
                sale.setQuantity(product.getQuantity());
                sale.setBrand(set.getBrand());
                sale.setName(set.getName());
                sale.setExpiry(set.getExpiry());
                sale.setTotal(product.getQuantity()*set.getPrice());
                salesRepository.save(sale);
            }
        }
}
    @Scheduled(fixedRate = 1000)
    public void expired(){
        LocalDate today=LocalDate.now();
        List<ProductEntity> product = productRepository.findAll();
        int s=product.size();
        for(int i=0;i<s;i++) {
            if (today.isAfter(product.get(i).getExpiry())) {
                productRepository.delete(product.get(i));
            }
        }
    }
public void damaged(List<DamagedDTO> list){
        int s=list.size();
        for(int i=0;i<s;i++) {
            ProductEntity set = productRepository.findByNameAndBrandAndExpiry(list.get(i).getName(),list.get(i).getBrand(),list.get(i).getExpiry()).orElseThrow(() -> new RuntimeException("Product not found"));
            set.setQuantity(set.getQuantity() - list.get(i).getQuantity());
            if(set.getQuantity()<=0){
                productRepository.delete(set);
            }
            else {
                productRepository.save(set);
            }
        }
        }


}
