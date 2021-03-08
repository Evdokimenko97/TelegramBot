import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class City {
    public static String getCity(String message, CityModel model, Temperature temp) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message +
                "&lang=ru&units=metric&b=?&lang=ru&appid=6fff53a641b9b9a799cfd6b079f5cd4e");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result = scanner.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONObject coord = object.getJSONObject("coord");
        model.setLon(coord.getDouble("lon"));
        model.setLat(coord.getDouble("lat"));
        return Weather.getWeather(model.getLon(), model.getLat(), temp);
    }
}
