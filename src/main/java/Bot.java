import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi tba = new TelegramBotsApi();

        try {
            tba.registerBot(new Bot());
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Temperature temperature = new Temperature();
        CityModel cityModel = new CityModel();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start": sendMsg(message, """
                            Какой город тебя интересует?""");
                    break;
                case "/help":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "/setting":
                    sendMsg(message, """
                    Данная функция позволяет добавлять кнопку с названием твоего города.
                    Напиши город, который ты хочешь установить.""");
                    break;
                default:
                    try {
                        sendMsg(message, City.getCity(message.getText(),cityModel,temperature));
                    } catch (IOException ex) {
                        sendMsg(message, "Город не найден");
                    }
                    break;
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyBoard);
        keyBoard.setSelective(true);
        keyBoard.setResizeKeyboard(true);
        keyBoard.setOneTimeKeyboard(false);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        keyboardRow.add(new KeyboardButton("/help"));
        keyboardRow.add(new KeyboardButton("/setting"));

        rows.add(keyboardRow);
        keyBoard.setKeyboard(rows);
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "AndrewTest123321Bot";
    }

    @Override
    public String getBotToken() {
        return "1527158539:AAHeMQjPivVyJH6OpL3ELdPlQSeR6fy2ESE";
    }
}
