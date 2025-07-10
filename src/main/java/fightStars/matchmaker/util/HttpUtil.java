package fightStars.matchmaker.util;

import fightStars.matchmaker.config.MatchMakerConfig;
import fightStars.matchmaker.dto.MatchInfo;
import fightStars.matchmaker.dto.RoomInfo;
import fightStars.matchmaker.dto.request.RoomCreateRequest;
import fightStars.matchmaker.dto.response.RoomCreateResponse;
import okhttp3.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static final String ROOM_API = MatchMakerConfig.ROOM_API;
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static RoomCreateResponse createRoom(RoomCreateRequest request) {
        RequestBody body = RequestBody.create(JsonUtil.toJson(request), MediaType.get("application/json"));
        Request req = new Request.Builder()
            .url(ROOM_API)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + JwtUtil.generateServerToken())
            .post(body)
            .build();

        try (Response res = CLIENT.newCall(req).execute()) {
            return JsonUtil.fromJson(res.body().string(), RoomCreateResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}
