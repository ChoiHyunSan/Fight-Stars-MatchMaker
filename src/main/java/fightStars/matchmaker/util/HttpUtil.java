package fightStars.matchmaker.util;

import fightStars.matchmaker.dto.MatchRequest;
import fightStars.matchmaker.dto.RoomInfo;
import okhttp3.*;

import java.util.List;

public class HttpUtil {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static final String ROOM_API =
            System.getenv().getOrDefault("ROOM_API", "http://localhost:8080/rooms");

    public static RoomInfo createRoom(List<MatchRequest> users) {
        RequestBody body = RequestBody.create(JsonUtil.toJson(users), MediaType.get("application/json"));
        Request req = new Request.Builder().url(ROOM_API).post(body).build();

        try (Response res = CLIENT.newCall(req).execute()) {
            return JsonUtil.fromJson(res.body().string(), RoomInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}