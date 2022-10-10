package uz.pdp.simpletelegrambot.entity;

import lombok.Data;
import org.hibernate.type.LocalDateType;
import uz.pdp.simpletelegrambot.enums.ProductStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Data
@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String status;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToOne
    private PhotoEntity photo;

    private LocalDateTime createdDate = LocalDateTime.now();

    @Override
    public String toString() {
        return "Product :" +
                "\nid=" + id +
                ",\nname=" + name +
                ",\nprice=" + price +
                ",\nstatus=" + status +
                ",\ncreated=" + createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}