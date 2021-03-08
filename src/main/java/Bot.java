import Model.CityModel;
import Model.TemperatureModel;
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

    //переопределённый метод для принятия сообщения
    @Override
    public void onUpdateReceived(Update update) {
        TemperatureModel temperatureModel = new TemperatureModel();
        CityModel cityModel = new CityModel();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start": sendMsg(message, "Какой город тебя интересует?");
                    break;
                case "/help":
                    sendMsg(message, "Данная функция находится в разработке.");
                    break;
                default:
                    try {
                        sendMsg(message, City.getCity(message.getText(),cityModel, temperatureModel));
                    } catch (IOException ex) {
                        sendMsg(message, "Город не найден");
                    }
                    break;
            }
        }
    }

    //добавляем кнопки
    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyBoard);
        keyBoard.setSelective(true);
        keyBoard.setResizeKeyboard(true);
        keyBoard.setOneTimeKeyboard(false);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        keyboardRow.add(new KeyboardButton("/help"));

        rows.add(keyboardRow);
        keyBoard.setKeyboard(rows);
    }

    //отправка сообщения
    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
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
