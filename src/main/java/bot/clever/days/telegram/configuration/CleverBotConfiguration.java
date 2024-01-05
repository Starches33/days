package bot.clever.days.telegram.configuration;

import bot.clever.days.telegram.bot.CleverBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class CleverBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(CleverBot cleverBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(cleverBot);
        return api;
    }

}
