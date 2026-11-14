package com.linkedout.userservice.event;
import java.util.List;
import java.util.UUID;

public record UserUpdatedEvent(
    UUID userId,
    String firstName,
    String lastName,
    String headline,
    String location,
    List<String> skills
) {}
