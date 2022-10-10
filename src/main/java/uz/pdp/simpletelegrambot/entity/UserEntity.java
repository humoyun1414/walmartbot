package uz.pdp.simpletelegrambot.entity;

import lombok.Data;
import uz.pdp.simpletelegrambot.enums.UserBotState;

import javax.persistence.*;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long chatId;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserBotState state;




}
