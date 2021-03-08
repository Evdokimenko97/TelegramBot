import Model.TemperatureModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Weather {

    public static String getWeather(Double lon, Double lat, TemperatureModel temperatureModel) throws IOException {
        //получаем ссылку для города с текущими коородинатами
        URL url = new URL("http://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon +
                "&units=metric&exclude=current,minutely,hourly,alerts&appid=b3f96e4a731e33f4184e742adf7907e6");

        //добавляем в переменную result данные из JSON
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result = scanner.nextLine();
        }

        //вытаскиваем данные из списка daily
        JSONObject object = new JSONObject(result);
        JSONArray getArray = object.getJSONArray("daily");

        //добавляем переменную с утренней температурой первого дня
        for (int i = 0; i < 1; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperatureModel.setTemp1(temp.getDouble("morn"));
        }

        //цикл для второго дня
        for (int i = 1; i < 2; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperatureModel.setTemp2(temp.getDouble("morn"));
        }

        //цикл для третьего дня
        for (int i = 2; i < 3; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperatureModel.setTemp3(temp.getDouble("morn"));
        }

        //цикл для четвертого дня
        for (int i = 3; i < 4; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperatureModel.setTemp4(temp.getDouble("morn"));
        }

        //цикл для пятого дня
        for (int i = 4; i < 5; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperatureModel.setTemp5(temp.getDouble("morn"));
        }

        //находим среднюю температуру за 5 дней, сокращаем её до 2 цифр после запятой
        double MiddleTemp = (temperatureModel.getTemp1() + temperatureModel.getTemp2() + temperatureModel.getTemp3() +
                temperatureModel.getTemp4() + temperatureModel.getTemp5()) / 5;
        String middleTemp = new DecimalFormat("#0.00").format(MiddleTemp);

        //нахоим максимальную темепературу сравнивая все переменные
        double MaxTemp = temperatureModel.getTemp1();

        if (MaxTemp > temperatureModel.getTemp2()) {
            MaxTemp = temperatureModel.getTemp2();
        }

        if (MaxTemp > temperatureModel.getTemp3()) {
            MaxTemp = temperatureModel.getTemp3();
        }

        if (MaxTemp > temperatureModel.getTemp4()) {
            MaxTemp = temperatureModel.getTemp4();
        }

        if (MaxTemp > temperatureModel.getTemp5()) {
            MaxTemp = temperatureModel.getTemp5();
        }


        return "Средняя температура утром за 5 дней: " + middleTemp + "C" + "\n" +
                "Максимальная температура утром за 5 дней: " + MaxTemp + "C";
    }
}
