package uz.pdp.simpletelegrambot.payload;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class InLineKeyboards {

    public InlineKeyboardButton button(String text, String callback) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        return button;
    }

    public List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    public InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
//    public InlineKeyboardMarkup markups(String text, String callback) {
//        InlineKeyboardButton button = new InlineKeyboardButton();
//        button.setText(text);
//        button.setCallbackData(callback);
//        List<InlineKeyboardButton> row = new LinkedList<>();
//        row.add(button);
//        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
//        rowList.add(row);
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        markup.setKeyboard(rowList);
//        return markup;
//    }

    public InlineKeyboardMarkup menuButton() {
        InlineKeyboardButton button = button("Go to Menu", "menu");
        List<InlineKeyboardButton> row = row(button);
        List<List<InlineKeyboardButton>> lists = rowList(row);
        InlineKeyboardMarkup keyboard = keyboard(lists);
        return keyboard;
    }

    public void menuListButtonsForUser(SendMessage sendMessage) {
        sendMessage.setText("Menu List");
        sendMessage.setParseMode("HTML");

        InlineKeyboardButton button = button("Products", "product_list");
        InlineKeyboardButton button2 = button("Basket", "orders");
        InlineKeyboardButton button3 = button("Call with admin", "admin");

        List<InlineKeyboardButton> row = row(button, button2);
        List<InlineKeyboardButton> row2 = row(button3);

        List<List<InlineKeyboardButton>> lists = rowList(row, row2);
        InlineKeyboardMarkup keyboard = keyboard(lists);

        sendMessage.setReplyMarkup(keyboard);
    }

    public void menuListButtonsForAdmin(SendMessage sendMessage) {
        sendMessage.setText("Menu List");
        sendMessage.setParseMode("HTML");

        InlineKeyboardButton button = button("Products", "product_list");
        InlineKeyboardButton button2 = button("Add Product", "add_product");
//        InlineKeyboardButton button3 = button("Delete Product", "delete_product");
//        InlineKeyboardButton button4 = button("Update Product", "update_product");

        List<InlineKeyboardButton> row = row(button, button2);
//        List<InlineKeyboardButton> row2 = row(button3, button4);

        List<List<InlineKeyboardButton>> lists = rowList(row);
        InlineKeyboardMarkup keyboard = keyboard(lists);
        sendMessage.setReplyMarkup(keyboard);
    }

    public InlineKeyboardMarkup basketKeyboard(Integer id) {

        InlineKeyboardButton button = button("Buyurtma berish", "buyurtma+" + id.toString());


        List<InlineKeyboardButton> row = row(button);

        List<List<InlineKeyboardButton>> lists = rowList(row);
        InlineKeyboardMarkup keyboard = keyboard(lists);
        return keyboard;
    }

    public InlineKeyboardMarkup buyurtmalar() {

        InlineKeyboardButton button = button("Buyurtmalar", "orders");


        List<InlineKeyboardButton> row = row(button);

        List<List<InlineKeyboardButton>> lists = rowList(row);
        InlineKeyboardMarkup keyboard = keyboard(lists);
        return keyboard;
    }

    public InlineKeyboardMarkup contactWithAdmin() {

        InlineKeyboardButton button = button("Admin bilan bog'lanish", "admin");


        List<InlineKeyboardButton> row = row(button);

        List<List<InlineKeyboardButton>> lists = rowList(row);
        InlineKeyboardMarkup keyboard = keyboard(lists);
        return keyboard;
    }

    public InlineKeyboardMarkup productListKeyboardFromAdmin(Integer id) {
        InlineKeyboardButton button1 = button("Update Product", "_update_product+" + id);
        InlineKeyboardButton button = button("Delete Product", "_delete_product+" + id);

        List<InlineKeyboardButton> row = row(button1);
        List<InlineKeyboardButton> row2 = row(button);

        List<List<InlineKeyboardButton>> lists = rowList(row, row2);
        InlineKeyboardMarkup keyboard = keyboard(lists);
        return keyboard;
    }
//    public InlineKeyboardMarkup productListKeyboardFromAdmin() {
//        InlineKeyboardButton button = button("Delete Product", "_delete_product");
//        InlineKeyboardButton button1 = button("Update Product", "_update_product");
//
//        List<InlineKeyboardButton> row = row(button,button1);
//
//        List<List<InlineKeyboardButton>> lists = rowList(row);
//        InlineKeyboardMarkup keyboard = keyboard(lists);
//        return keyboard;
//    }
}
