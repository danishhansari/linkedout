package com.linkedout.userservice.event;
import java.util.UUID;

public record UserCreatedEvent (
    UUID userId,
    String firstName,
    String lastName,
    String email,
    String headline,
    String location
) {}
