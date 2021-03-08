import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(Double lon, Double lat, Temperature temperature) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon +
                "&units=metric&exclude=current,minutely,hourly,alerts&appid=b3f96e4a731e33f4184e742adf7907e6");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result = scanner.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONArray getArray = object.getJSONArray("daily");

        //цикл для первого дня
        for (int i = 0; i < 1; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperature.setTemp1(temp.getDouble("morn"));
        }

        //цикл для второго дня
        for (int i = 1; i < 2; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperature.setTemp2(temp.getDouble("morn"));
        }

        //цикл для третьего дня
        for (int i = 2; i < 3; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperature.setTemp3(temp.getDouble("morn"));
        }

        //цикл для четвертого дня
        for (int i = 3; i < 4; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperature.setTemp4(temp.getDouble("morn"));
        }

        //цикл для пятого дня
        for (int i = 4; i < 5; i++) {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject temp = obj.getJSONObject("temp");
            temperature.setTemp5(temp.getDouble("morn"));
        }

        double MiddleTemp = (temperature.getTemp1() + temperature.getTemp2() + temperature.getTemp3() +
                temperature.getTemp4() + temperature.getTemp5()) / 5;

        double MaxTemp = temperature.getTemp1();

        if (MaxTemp > temperature.getTemp2()) {
            MaxTemp = temperature.getTemp2();
        }

        if (MaxTemp > temperature.getTemp3()) {
            MaxTemp = temperature.getTemp3();
        }

        if (MaxTemp > temperature.getTemp4()) {
            MaxTemp = temperature.getTemp4();
        }

        if (MaxTemp > temperature.getTemp5()) {
            MaxTemp = temperature.getTemp5();
        }


        return "Средняя температура утром за 5 дней: " + MiddleTemp + "C" + "\n" +
                "Максимальная температура утром за 5 дней: " + MaxTemp + "C";
    }
}
