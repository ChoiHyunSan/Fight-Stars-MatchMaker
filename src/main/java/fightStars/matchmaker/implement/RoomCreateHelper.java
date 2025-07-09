package fightStars.matchmaker.implement;

import java.util.List;

import fightStars.matchmaker.dto.MatchInfo;
import fightStars.matchmaker.dto.request.RoomCreateRequest;
import fightStars.matchmaker.dto.response.RoomCreateResponse;
import fightStars.matchmaker.util.HttpUtil;

public class RoomCreateHelper {

	public static RoomCreateResponse createRoom(List<MatchInfo> bucket) {
		return HttpUtil.createRoom(RoomCreateRequest.of(bucket));
	}
}
