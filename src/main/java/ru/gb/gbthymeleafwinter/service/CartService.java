package ru.gb.gbthymeleafwinter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.gbthymeleafwinter.dao.CartDao;
import ru.gb.gbthymeleafwinter.entity.Cart;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService extends AbstractService<Cart> {

    private final CartDao cartDao;

    @Autowired
    public CartService(CartDao cartDao) {
        super(cartDao);
        this.cartDao = cartDao;
    }

    public Cart save(Cart cart) {
        if (cart.getId() != null) {
            Optional<Cart> CartFromDbOptional = cartDao.findById(cart.getId());
            if (CartFromDbOptional.isPresent()) {
                Cart cartFromDb = CartFromDbOptional.get();
                cartFromDb.setStatus(cart.getStatus());
                cartFromDb.setProducts(cart.getProducts());
                return cartDao.save(cartFromDb);
            }
        }
        return cartDao.save(cart);
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
