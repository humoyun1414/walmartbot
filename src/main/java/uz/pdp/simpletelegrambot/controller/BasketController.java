package uz.pdp.simpletelegrambot.controller;

import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.simpletelegrambot.MyTelegramBot;
import uz.pdp.simpletelegrambot.entity.BasketEntity;
import uz.pdp.simpletelegrambot.entity.ProductEntity;
import uz.pdp.simpletelegrambot.entity.UserEntity;
import uz.pdp.simpletelegrambot.payload.Components;
import uz.pdp.simpletelegrambot.payload.InLineKeyboards;
import uz.pdp.simpletelegrambot.repository.BasketRepository;
import uz.pdp.simpletelegrambot.repository.ProductRepository;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class BasketController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private InLineKeyboards inLineKeyboards;
    @Autowired
    private MyTelegramBot telegramBot;

    public void handleCallBack(UserEntity entity, Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(entity.getChatId());
        if (text.startsWith("buyurtma")) {
            String Id = text.substring(text.length() - 1);
            int id = Integer.parseInt(Id);
            Optional<ProductEntity> entityOptional = productRepository.findById(id);
            if (entityOptional.isPresent()) {
                ProductEntity product = entityOptional.get();
                BasketEntity basket = new BasketEntity();
                basket.setProducts(Collections.singletonList(product));
                basketRepository.save(basket);
            }
            sendMessage.setText("Buyurtma savatchaga joylandi ! ");
            sendMessage.setReplyMarkup(inLineKeyboards.buyurtmalar());
            telegramBot.sdm(sendMessage);

        } else if (text.startsWith("orders")) {
            List<BasketEntity> basketlist = basketRepository.findAll();
            if (basketlist.isEmpty()) {
                sendMessage.setText("Hozirda sizda buyurtmalar mavjud emas !!");
                sendMessage.setReplyMarkup(inLineKeyboards.menuButton());
                telegramBot.sdm(sendMessage);
            }
            for (BasketEntity basket : basketlist) {

                for (ProductEntity product : basket.getProducts()) {

                    StringBuilder builder = new StringBuilder();
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(String.valueOf(entity.getChatId()));

                    sendPhoto.setPhoto(new InputFile(product.getPhoto().getFileId()));

                    builder.append("Id: ");
                    builder.append(product.getId());
                    builder.append("\n");
                    builder.append("Nomi: ");
                    builder.append(product.getName());
                    builder.append("\n");
                    builder.append("Narxi: ");
                    builder.append(product.getPrice());
                    builder.append("\n");
                    builder.append("Status: ");
                    builder.append(product.getStatus());
                    builder.append("\n");
                    builder.append("Created: ");
                    builder.append(product.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    builder.append("\n-------------------------------\n");

                    SendMessage editMessageText = new SendMessage();
                    editMessageText.setChatId(message.getChatId().toString());
                    editMessageText.setText(builder.toString());

                    telegramBot.sdm(sendPhoto);

                    editMessageText.setReplyMarkup(inLineKeyboards.contactWithAdmin());
                    telegramBot.sdm(editMessageText);
                }
            }
        }
    }

    public void contactAdmin(UserEntity entity, Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(entity.getChatId());
        sendMessage.setText("Admin : --------\nNomer : 9999999");
        sendMessage.setReplyMarkup(inLineKeyboards.menuButton());
        telegramBot.sdm(sendMessage);
    }
}
