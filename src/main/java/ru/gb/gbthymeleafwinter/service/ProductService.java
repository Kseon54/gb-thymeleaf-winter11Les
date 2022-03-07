package ru.gb.gbthymeleafwinter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.gbthymeleafwinter.dao.ProductDao;
import ru.gb.gbthymeleafwinter.entity.Product;

@Service
@Slf4j
public class ProductService extends AbstractService<Product>{

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        super(productDao);
        this.productDao = productDao;
    }

    @Override
    protected Product update(Product product, Product productFromDb) {
        productFromDb.setTitle(product.getTitle());
        productFromDb.setDate(product.getDate());
        productFromDb.setCost(product.getCost());
        productFromDb.setStatus(product.getStatus());
        return productFromDb;
    }
}
