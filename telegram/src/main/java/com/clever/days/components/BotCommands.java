package com.clever.days.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

//todo вынести все необходимые не-клавиатурные команды сюда
public interface BotCommands {

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info"),
            new BotCommand("/abc", "bot io")
    );

    String HELP_TEXT = "This bot will help to count the number of messages in the chat. " +
            "The following commands are available to you:\n\n" +
            "/start - start the bot\n" +
            "/help - help menu";


    public static final String unknownCommand = "Не удалось распознать команду! Пожалуйста, используйте команды для взаимодействия с ботом.";
    public static final String helpText = "Чем помочь, сученька?";
    public static final String startText = "Пиши имя?";

    public static final String start = "/start";
    public static final String menu = "/menu";
    public static final String addNote = "/addNote";
    public static final String getAllNotes = "/getAllNotes";
    public static final String getNote = "/getNote";
    public static final String account = "/account";
    public static final String help = "/help";
}
