package Horario;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        out.value(TIME_FORMATTER.format(value));
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        return LocalTime.parse(in.nextString(), TIME_FORMATTER);
    }

}
