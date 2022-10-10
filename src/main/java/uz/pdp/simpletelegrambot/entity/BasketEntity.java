package uz.pdp.simpletelegrambot.entity;


import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "basket")
public class BasketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<ProductEntity> products;

}
