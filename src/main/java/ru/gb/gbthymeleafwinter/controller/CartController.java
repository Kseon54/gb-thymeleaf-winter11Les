package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.gbthymeleafwinter.entity.Cart;
import ru.gb.gbthymeleafwinter.entity.Product;
import ru.gb.gbthymeleafwinter.entity.enums.Status;
import ru.gb.gbthymeleafwinter.entity.security.AccountUser;
import ru.gb.gbthymeleafwinter.security.JpaUserDetailService;
import ru.gb.gbthymeleafwinter.service.CartService;
import ru.gb.gbthymeleafwinter.service.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
//Предполагается что у пользавателя будет только одна активная козина
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final JpaUserDetailService userDetailService;

    @GetMapping()
    public String getProductList(Model model, Principal principal) {
        Cart cart = getActiveCart(principal);
        model.addAttribute("products", cart.getProducts());
        return "cart-listProduct";
    }

//    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/add/{id}")
    public String addProduct(@PathVariable(name = "id") Long productId,
                             Principal principal) {

        Cart cart = getActiveCart(principal);
        cart.getProducts().add(productService.findById(productId));
        cartService.save(cart);
        return "redirect:/product/all";
    }

    @GetMapping("/deleteProduct/{idProduct}")
    public String deleteProductById(@PathVariable(name = "idProduct") Long idProduct,
                                    Principal principal) {
        Cart cart = getActiveCart(principal);

        List<Product> products = cart.getProducts().stream()
                .filter(product -> product.getId().equals(idProduct))
                .collect(Collectors.toList());

        if (products.size() != 0) {
            cart.getProducts().remove(products.get(0));
            cartService.save(cart);
        }

        return "redirect:/cart";
    }

    private Cart getActiveCart(Principal principal) {
        List<Cart> allActive = cartService.findAllActiveByUserName(principal.getName());
        Cart cart;
        if (allActive.size() == 0) {
            AccountUser user = userDetailService.findByName(principal.getName());
            cart = cartService.save(
                    Cart.builder()
                            .user(user)
                            .status(Status.ACTIVE)
                            .build()
            );
        } else {
            cart = allActive.get(0);
        }
        return cart;
    }
}
