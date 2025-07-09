package fightStars.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MatchInfo(
	@JsonProperty("UserId") long userId,
	@JsonProperty("CharacterId") long characterId,
	@JsonProperty("SkinId") long skinId,
	@JsonProperty("ConnectionServerId") String connectionServerId,
	@JsonProperty("Mode") String mode
) {}
