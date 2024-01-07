package com.clever.days.bot;

import com.clever.days.components.BotCommands;
import com.clever.days.components.Buttons;
import com.clever.days.configuration.BotConfig;
import com.clever.days.service.TgDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CleverDaysBot extends TelegramLongPollingBot implements BotCommands {

    @Autowired
    private TgDbService tgDbService;

    final BotConfig config;

    private static final Logger log = LoggerFactory.getLogger(CleverDaysBot.class);

    private Map<Long, UserState> userStates = new HashMap<>();

    private enum UserState {
        STARTED, SAVED, ADDING_TEXT
    }

    private static final String unknownCommand = "Не удалось распознать команду! Пожалуйста, используйте команды для взаимодействия с ботом.";
    private static final String helpText = "Чем помочь, сученька?";
    private static final String startText = "Пиши имя?";

    @Value("${bot.username}")
    private String username;

    private static final String start = "/start";
    private static final String addNote = "/addNote";
    private static final String getNote = "/getNote";
    private static final String account = "/account";
    private static final String help = "/help";

    public CleverDaysBot(@Value("${bot.token}") String botToken, BotConfig config) {
        super(botToken);
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private void clearAndPutSavedUserState(long userId) {
        userStates.remove(userId);
        userStates.put(userId, UserState.SAVED);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            long userId = update.getMessage().getFrom().getId();
            String userName = update.getMessage().getFrom().getFirstName();
            String receivedMessage = update.getMessage().getText();

            botAnswerUtils(receivedMessage, chatId, userId, userName);
        }
    }

    public void botAnswerUtils(String receivedMessage, long chatId, long userId, String userName) {
        switch (receivedMessage) {
            case start:
                sendMessage(chatId, "Hi, " + userName + startText);
                userStates.put(userId, CleverDaysBot.UserState.STARTED);
                break;

            case help:
                sendMessage(chatId, helpText);
                break;

            case addNote:
                sendMessage(chatId, "Пожалуйста, отправьте мне текст заметки");
                userStates.put(userId, UserState.ADDING_TEXT);
                break;

            default:
                if (userStates.containsKey(userId)) {
                    processUserMessage(userId, chatId, receivedMessage);
                }
                break;
        }
    }

    private void processUserMessage(long userId, long chatId, String message) {
        UserState state = userStates.get(userId);

        if (!userStates.containsKey(userId)) {
            sendMessage(chatId, unknownCommand);
            return;
        }

        switch (state) {
            //todo сделать условие на количество символов
            case STARTED -> {
                startBot(chatId, userId, message);
                sendReplyKeyBoardMessage(chatId);
                //        message.setReplyMarkup(Buttons.inlineMarkup());
                clearAndPutSavedUserState(userId);
            }
            //todo реализовать сохранение текста
            case SAVED -> sendMessage(chatId, unknownCommand);

            case ADDING_TEXT -> clearAndPutSavedUserState(userId);
        }
    }

    private void startBot(long chatId, long userId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(userName);

        executeMessage(message, "Reply on start message sent, start saving user");

        if (tgDbService.findUserByTgId(userId) == null) {
            //todo Починить сохранение username
            tgDbService.createUser(userId, userName);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        executeMessage(message);
    }

    private void sendMessage(Long chatId, String text, String logInfo) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        executeMessage(message, logInfo);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void executeMessage(SendMessage message, String logInfo) {

        try {
            execute(message);

            if (logInfo != null)
                log.info(logInfo);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendReplyKeyBoardMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Ты в главном меню, выберай команду:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/actives"));
        row1.add(new KeyboardButton("/deals"));
        row1.add(new KeyboardButton("/analyse"));
        row1.add(new KeyboardButton("/account"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("/addNote"));
        row2.add(new KeyboardButton("/getNotes"));

        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true); // Делаем клавиатуру подгоняемой по размеру
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура остается видимой после использования
        keyboardMarkup.setSelective(true); // Показывать клавиатуру только определенным пользователям

        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }
}
