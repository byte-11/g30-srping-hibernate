package uz.pdp.g30springhibernate.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.LocalDateTime;

@UtilityClass
public class ResponseManager {
    public static void sendJsonResponse(HttpServletResponse resp, Object o) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(o));
    }

    public static void sendBadRequestJsonResponse(HttpServletResponse resp, String message) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        resp.setContentType("application/json");
        resp.setStatus(400);
        resp.getWriter().write(gson.toJson(new BadRequestResponse(message)));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class BadRequestResponse{
        private String message;
        private LocalDateTime time;

        public BadRequestResponse(String message) {
            this.message = message;
            this.time = LocalDateTime.now();
        }
    }

    private class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime>{

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            if (localDate == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localDate.toString());
            }
        }
        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            } else {
                return LocalDateTime.parse(jsonReader.nextString());
            }
        }
    }
}
