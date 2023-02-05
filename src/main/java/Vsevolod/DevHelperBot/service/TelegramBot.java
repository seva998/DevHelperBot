package Vsevolod.DevHelperBot.service;

import Vsevolod.DevHelperBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                        startCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                        break;

                case  "/java":
                        javaCommandReceived(chatId);
                        break;

                default:
                        sendMessage(chatId,"Sorry, unknown command");

            }
        }

    }
    private void startCommandReceived(long chatId,String name){

        String answer = "Приветствую, " + name + " ! \n Я бот - помощник программиста. Я могу выдавать ссылки на статьи с действительно полезной информацией на следующие темы: \n" +
                " java - /java \n java core - /core \n spring framework - /spring \n github - /git \n docker - /docker"
                ;

        sendMessage(chatId, answer);
    }

    private void javaCommandReceived(long chatId){

        String answer = "Основы Java - https://proglib.io/p/osnovy-java-za-30-minut-samouchitel-dlya-nachinayushchih-2021-09-06\n" +
                "Синтаксис и ключевые слова - https://proglang.su/java/syntax\n" +
                "API Java - https://overapi.com/java\n" +
                "Базовые методы Java - https://cheatography.com/sefergus/cheat-sheets/java-midterm/\n" +
                "API общих библеотек (англ) - https://introcs.cs.princeton.edu/java/11cheatsheet/";

        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId,String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch(TelegramApiException e) {

        }
    }
}
