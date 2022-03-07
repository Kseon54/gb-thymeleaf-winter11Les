package ru.gb.gbthymeleafwinter.dao;

import ru.gb.gbthymeleafwinter.entity.Product;

import java.util.List;

public interface ProductDao extends AbstractDao<Product, Long> {
    Product findByTitle(String title);
    List<Product> findAllByTitleContaining(String title);
}
