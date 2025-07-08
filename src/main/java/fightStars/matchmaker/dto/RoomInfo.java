package fightStars.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomInfo(
        @JsonProperty("Ip") String ip,
        @JsonProperty("Port") int port,
        @JsonProperty("RoomId") String roomId,
        @JsonProperty("UserId") long userId
) {}