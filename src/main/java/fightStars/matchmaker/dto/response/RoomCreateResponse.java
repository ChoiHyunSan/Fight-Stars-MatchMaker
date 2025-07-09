package fightStars.matchmaker.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RoomCreateResponse(
	String roomId,
	String password,
	String ip,
	int port
) { }
