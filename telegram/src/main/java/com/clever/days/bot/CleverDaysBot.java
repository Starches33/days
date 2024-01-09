package com.clever.days.bot;

import com.clever.days.components.BotCommands;
import com.clever.days.components.Keyboards;
import com.clever.days.configuration.BotConfig;
import com.clever.days.service.NoteService;
import com.clever.days.service.UserService;
import com.clever.days.util.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Component
public class CleverDaysBot extends TelegramLongPollingBot implements BotCommands {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    final BotConfig config;

    private static final Logger log = LoggerFactory.getLogger(CleverDaysBot.class);

    @Value("${bot.username}")
    private String username;

    public CleverDaysBot(@Value("${bot.token}") String botToken, BotConfig config) {
        super(botToken);
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return username;
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

    private Map<Long, Enums.UserState> userStates = new HashMap<>();

    private Enums.NoteType selectedType;

    private void clearAndPutNewUserState(long userId, Enums.UserState newUserState) {
        userStates.remove(userId);
        userStates.put(userId, newUserState);
    }

    public void botAnswerUtils(String receivedMessage, long chatId, long userId, String userName) {
        switch (receivedMessage) {
            case start:
                sendMessage(chatId, "Hi, " + userName + startText);
                userStates.put(userId, Enums.UserState.STARTED);
                break;

            case menu:
                sendMenuMessage(chatId, null);
                clearAndPutNewUserState(userId, Enums.UserState.ACTIVE);
                break;

            case help:
                sendMessage(chatId, helpText);
                break;

            case addNote:
                var addNoteSendMessage = Keyboards.noteTypeReplyKeyBoardMessage(chatId);
                executeMessage(addNoteSendMessage);
                clearAndPutNewUserState(userId, Enums.UserState.SELECTING_NOTE_TYPE);
                break;

            case getAllNotes:
                var noteList = noteService.findNoteListByTgId(userId);

                String allNotes = noteList.toString();

                var menuSendMessage = Keyboards.menuReplyKeyBoardMessage(chatId, "Твои заметки: " + allNotes);

                executeMessage(menuSendMessage);
                clearAndPutNewUserState(userId, Enums.UserState.ACTIVE);
                break;

            default:
                if (userStates.containsKey(userId)) {
                    processUserMessage(userId, chatId, receivedMessage);
                }
                break;
        }
    }

    private void processUserMessage(long userId, long chatId, String message) {
        Enums.UserState state = userStates.get(userId);

        if (!userStates.containsKey(userId)) {
            sendMessage(chatId, unknownCommand);
            return;
        }

        switch (state) {
            case STARTED -> {
                //todo условие на количество символов
                startBot(chatId, userId, message);
                sendMenuMessage(chatId, null);
                clearAndPutNewUserState(userId, Enums.UserState.ACTIVE);
            }

            case SELECTING_NOTE_TYPE -> {
                selectedType = Enums.NoteType.valueOf(message);
                sendMessage(chatId, "Теперь пиши заметку");
                clearAndPutNewUserState(userId, Enums.UserState.ADDING_TEXT);
            }

            case ADDING_TEXT -> {
                //todo условие на количество символов
                noteService.createNote(userId, message, selectedType, null);
                sendMenuMessage(chatId, "Заметка успешно создана. Теперь ты в главном меню");
                clearAndPutNewUserState(userId, Enums.UserState.ACTIVE);
            }

            //todo реализовать сохранение текста???
            case ACTIVE -> sendMessage(chatId, unknownCommand);
        }
    }

    private void startBot(long chatId, long userId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(userName);

        executeMessage(message, "Reply on start message sent, start saving user");

        userService.createUser(userId, userName);
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        executeMessage(message);
    }

    private void sendMenuMessage(Long chatId, String customText) {
        var menuSendMessage = Keyboards.menuReplyKeyBoardMessage(chatId, customText);

        executeMessage(menuSendMessage);
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
}
