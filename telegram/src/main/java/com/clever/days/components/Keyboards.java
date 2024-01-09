package com.clever.days.components;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {

    public static SendMessage menuReplyKeyBoardMessage(Long chatId, String customText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());

        message.setText(customText == null ? "Ты в главном меню, выбирай команду:" : customText);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/actives"));
        row1.add(new KeyboardButton("/deals"));
        row1.add(new KeyboardButton("/analyse"));
        row1.add(new KeyboardButton("/account"));
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("/addNote"));
        row2.add(new KeyboardButton("/getAllNotes"));
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true); // Делаем клавиатуру подгоняемой по размеру
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура остается видимой после использования
        keyboardMarkup.setSelective(true); // Показывать клавиатуру только определенным пользователям

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    public static SendMessage noteTypeReplyKeyBoardMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Выбери тип заметки");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("MINDS"));
        row1.add(new KeyboardButton("IDEAS"));
        row1.add(new KeyboardButton("USEFUL"));
        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setSelective(true);

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }
}
