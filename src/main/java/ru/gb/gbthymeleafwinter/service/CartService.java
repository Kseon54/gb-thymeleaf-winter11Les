package ru.gb.gbthymeleafwinter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.gbthymeleafwinter.dao.CartDao;
import ru.gb.gbthymeleafwinter.entity.Cart;

import java.util.List;

@Slf4j
@Service
public class CartService extends AbstractService<Cart> {

    private final CartDao cartDao;

    @Autowired
    public CartService(CartDao cartDao) {
        super(cartDao);
        this.cartDao = cartDao;
    }

    @Override
    protected Cart update(Cart entity, Cart entityFromDb) {
        entityFromDb.setStatus(entity.getStatus());
        entityFromDb.setProducts(entity.getProducts());
        return entityFromDb;
    }

    public List<Cart> findAllActiveByUserName(String username) {
        return cartDao.findAllActiveByUserUsername(username);
    }
}
