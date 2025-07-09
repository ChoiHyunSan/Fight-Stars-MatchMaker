package fightStars.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import fightStars.matchmaker.dto.response.RoomCreateResponse;

public record RoomInfo(
        @JsonProperty("Ip") String ip,
        @JsonProperty("Port") int port,
        @JsonProperty("RoomId") String roomId,
        @JsonProperty("Password") String password,
        @JsonProperty("UserId") long userId
) {
	public static RoomInfo create(RoomCreateResponse response, long userId) {
		return new RoomInfo(
			response.ip(),
			response.port(),
			response.roomId(),
			response.password(),
			userId
		);
	}
}
