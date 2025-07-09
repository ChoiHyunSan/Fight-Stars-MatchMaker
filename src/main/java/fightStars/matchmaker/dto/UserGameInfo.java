package fightStars.matchmaker.dto;

public record UserGameInfo(
	long userId,
	long characterId,
	long skinId
) {
	public static UserGameInfo create(MatchInfo u) {
		return new UserGameInfo(
			u.userId(),
			u.characterId(),
			u.skinId()
		);
	}
}
