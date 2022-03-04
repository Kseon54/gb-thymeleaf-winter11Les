package ru.gb.gbthymeleafwinter.dao;

import ru.gb.gbthymeleafwinter.entity.Cart;

import java.util.List;

public interface CartDao extends AbstractDao<Cart, Long> {

    List<Cart> findAllActiveByUserUsername(String username);
}
