package com.clever.days.configuration;

import com.clever.days.bot.CleverDaysBot;
//import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@EnableCaching
@EnableScheduling
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(CleverDaysBot cleverDaysBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(cleverDaysBot);
        return api;
    }

}
