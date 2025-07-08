package fightStars.matchmaker.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T fromJson(String j, Class<T> c) {
        try {
            return MAPPER.readValue(j, c);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object o){
        try {
            return MAPPER.writeValueAsString(o);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
