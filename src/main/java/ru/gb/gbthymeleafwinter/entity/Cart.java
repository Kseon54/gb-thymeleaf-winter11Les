package ru.gb.gbthymeleafwinter.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.gbthymeleafwinter.entity.enums.Status;
import ru.gb.gbthymeleafwinter.entity.security.AccountUser;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
@EntityListeners(AuditingEntityListener.class)
public class Cart extends AbstractEntity<Cart> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "cart_product",
            joinColumns = {@JoinColumn(name = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    private Set<Product> products;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Version
    @Column(name = "VERSION")
    private int version;
    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDate createdDate;
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDate lastModifiedDate;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                '}';
    }

    @Override
    public Cart createBuilder() {
        Set<Product> products = getProducts().stream().map(Product::createBuilder).collect(Collectors.toSet());
        return Cart.builder()
                .id(id)
                .products(products)
                .status(status)
                .build();
    }
}
