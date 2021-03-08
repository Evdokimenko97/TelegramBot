import Model.CityModel;
import Model.TemperatureModel;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class City {
    public static String getCity(String message, CityModel model, TemperatureModel temp) throws IOException {
        //принимаем ссылку с выбранным городом
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message +
                "&lang=ru&units=metric&b=?&lang=ru&appid=6fff53a641b9b9a799cfd6b079f5cd4e");

        //добавляем в переменную result данные из JSON
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result = scanner.nextLine();
        }

        //вытаскиваем из JSON координаты и добавляем их в сеттеры
        JSONObject object = new JSONObject(result);
        JSONObject coord = object.getJSONObject("coord");
        model.setLon(coord.getDouble("lon"));
        model.setLat(coord.getDouble("lat"));

        return Weather.getWeather(model.getLon(), model.getLat(), temp);
    }
}
