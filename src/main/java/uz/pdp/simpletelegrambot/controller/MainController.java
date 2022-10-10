package uz.pdp.simpletelegrambot.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.simpletelegrambot.MyTelegramBot;
import uz.pdp.simpletelegrambot.entity.ProductEntity;
import uz.pdp.simpletelegrambot.entity.UserEntity;
import uz.pdp.simpletelegrambot.enums.ProductStatus;
import uz.pdp.simpletelegrambot.enums.UserBotState;
import uz.pdp.simpletelegrambot.payload.Components;
import uz.pdp.simpletelegrambot.payload.InLineKeyboards;
import uz.pdp.simpletelegrambot.repository.ProductRepository;

import java.util.Optional;

@Component
public class MainController {
    @Lazy
    @Autowired
    private MyTelegramBot telegramBot;
    @Autowired
    private InLineKeyboards keyboards;
    @Lazy
    @Autowired
    private ProductController productController;
    @Autowired
    private BasketController basketController;

    public void handleText(UserEntity entity, Message message) {

        String text = message.getText();
        Long chatId = entity.getChatId();

        if (text.equals("/start")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Assalomu alaykum PDP WalMartga hush kelibsiz . ");
            entity.setState(UserBotState.MENU);
            sendMessage.setReplyMarkup(keyboards.menuButton());
            telegramBot.sdm(sendMessage);
//            SendMessage adminMessage = new SendMessage();
//            adminMessage.setChatId(Components.ADMIN);
//            adminMessage.setText(entity.getFirstName() + " joined bot.");
//            telegramBot.sdm(adminMessage);
        } else if (text.equals("/menu")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            if (entity.getChatId().toString().equals(Components.ADMIN))
                keyboards.menuListButtonsForAdmin(sendMessage);
            else
                keyboards.menuListButtonsForUser(sendMessage);
            telegramBot.sdm(sendMessage);
//            Optional</ProductEntity> byProductStatus = productRepository.findByProductStatus(ProductStatus.PRODUCT_NAME);

        } else if (Components.PRODUCT_STATUS_MAP.containsKey(entity.getChatId().toString())) {
            switch (Components.PRODUCT_STATUS_MAP.get(entity.getChatId().toString())) {
                case PRODUCT_NAME:
                case PRODUCT_PHOTO:
                case PRODUCT_PRICE:
                case PRODUCT_DELETE:
                    productController.handleText(entity, message);
                    break;

            }
        }
        else if (Components.PRODUCT_STATUS_MAP_UPDATE.containsKey(entity.getChatId().toString())) {
            switch (Components.PRODUCT_STATUS_MAP_UPDATE.get(entity.getChatId().toString())) {
                case PRODUCT_NAME_UPDATE:
                case PRODUCT_PHOTO_UPDATE:
                case PRODUCT_PRICE_UPDATE:
                case PRODUCT_STATUS_UPDATE:
                case PRODUCT_DELETE_UPDATE:
                    productController.handleTextUpdate(entity, message);
                    break;

            }
        }
        else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(entity.getChatId());
            sendMessage.setText("Menu ga o'tmoqchi bo'lsanggiz buttonni bosing");
            sendMessage.setReplyMarkup(keyboards.menuButton());
            telegramBot.sdm(sendMessage);
        }
    }

    public void handleCallBack(UserEntity entity, Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        Long chatId = entity.getChatId();
        sendMessage.setChatId(chatId);
        if (text.equals("menu")) {
            if (entity.getChatId().toString().equals(Components.ADMIN))
                keyboards.menuListButtonsForAdmin(sendMessage);
            else keyboards.menuListButtonsForUser(sendMessage);
            telegramBot.sdm(sendMessage);
        } else if (text.equals("add_product")) {
            productController.handleCallBack(entity, message, text);
        }
        else if (text.equals("product_list")){
            productController.handleCallBack(entity,message,text);
        }else if (text.startsWith("buyurtma")){
            basketController.handleCallBack(entity,message,text);
        }else if (text.equals("admin")){
            basketController.contactAdmin(entity,message,text);
        }
        else if (text.startsWith("orders")){
            basketController.handleCallBack(entity,message,text);        }
        else if (text.startsWith("_delete")){
            productController.handleCallBack(entity,message,text);
        }else if (text.startsWith("_update")){
            productController.handleCallBack(entity,message,text);
        }
    }
}
