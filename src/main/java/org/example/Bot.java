package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        //инициилизируем API
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(
                    DefaultBotSession.class
            );
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    @Override
    //метод возвращающий имя бота указанное при регистрации
    public String getBotUsername() {
        return "My1First1TestBot";
    }

    @Override
    // метод возвращает токен
    public String getBotToken() {
        //возвращаем токен полученный при регистрации бота
        return "5200476829:AAEU5n7bCuJNxwAo8LSMxRezJ1ZPT80e9Ms";
    }

    //создаем метод который будет отправлять сообщения
    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        //включаем возможность разметки
        sendMessage.enableMarkdown(true);
        //устанавливаем id чата на который должен отвечать бот
        sendMessage.setChatId(message.getChatId().toString());
        //определяем на какое сообщение должен ответить бот
        sendMessage.setReplyToMessageId(message.getMessageId());
        //устанавливаем текст который должен ответить бот
        sendMessage.setText(text);

        //устанавливаем отправку сообщения
        try{
            //отправка сообщения по нажатию по подтекстовой клавиатуре
            setButton(sendMessage);
            //отправка любого сообщения
            execute(sendMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    //метод возвращает сообщения
    public void onUpdateReceived(Update update) {
        //создаем объект который будет принимать в себя текст сообщения
        Message message = update.getMessage();
        if (message != null && message.hasText()){
            switch (message.getText()){
                case "/help":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "/setting":
                    sendMsg(message, "Что будем настраивать?");
                    break;
                default:
            }
        }


    }
    //создаем метод для подтекстовой клавиатуры
    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        //устанавливаем разметку для клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        //определяем кому будет выводится клавиатура
        replyKeyboardMarkup.setSelective(true);
        //делаем настройку клавиатуры
        replyKeyboardMarkup.setResizeKeyboard(true);
        //устанавливаем будет ли скрываться клавиатура после использования кнопки
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        //создаем коллекцию кнопок
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        //создаем первую кнопку
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        //Создаем кнопки 1 и 2
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));
        //добавляем кнопки в список
        keyboardRowList.add(keyboardFirstRow);
        //устанавливаем этот список на клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }
}
