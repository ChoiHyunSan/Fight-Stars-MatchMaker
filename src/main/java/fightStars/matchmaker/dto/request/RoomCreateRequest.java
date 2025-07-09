package fightStars.matchmaker.dto.request;

import java.util.List;

import fightStars.matchmaker.dto.MatchInfo;
import fightStars.matchmaker.dto.UserGameInfo;

public record RoomCreateRequest(
	String mode,
	List<UserGameInfo> userInfos
) {
	public static RoomCreateRequest of(List<MatchInfo> bucket) {
		return new RoomCreateRequest(
			bucket.getFirst().mode().toLowerCase(),
			bucket.stream().map(UserGameInfo::create).toList()
		);
	}
}
