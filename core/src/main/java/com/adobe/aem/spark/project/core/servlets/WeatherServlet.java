package com.adobe.aem.spark.project.core.servlets;

import com.adobe.aem.spark.project.core.services.configs.WeatherApiConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/weather",
                "sling.servlet.methods=GET"
        })
@Designate(ocd = WeatherApiConfig.class)
public class WeatherServlet extends SlingAllMethodsServlet {

    private String API_KEY ;

    @Activate
    @Modified
    protected void activate(WeatherApiConfig config) {
        this.API_KEY = config.apiKey();
    }


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String city = request.getParameter("city");
        String unit = request.getParameter("unit");

        if (city == null || unit == null) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing city or unit parameter\"}");
            return;
        }

        String weatherUrl = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s",
                city, unit, API_KEY
        );

        URL url = new URL(weatherUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int code = conn.getResponseCode();
        if (code != 200) {
            response.setStatus(code);
            response.getWriter().write("{\"error\": \"Failed to fetch weather data\"}");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(reader).getAsJsonObject();

        String temperature = json.getAsJsonObject("main").get("temp").getAsString();
        String description = json.getAsJsonArray("weather")
                .get(0).getAsJsonObject().get("description").getAsString();
        String icon = json.getAsJsonArray("weather")
                .get(0).getAsJsonObject().get("icon").getAsString();

        JsonObject result = new JsonObject();
        result.addProperty("city", city);
        result.addProperty("temp", temperature);
        result.addProperty("description", description);
        result.addProperty("icon", icon);

        response.setContentType("application/json");
        response.getWriter().write(result.toString());
    }
}
