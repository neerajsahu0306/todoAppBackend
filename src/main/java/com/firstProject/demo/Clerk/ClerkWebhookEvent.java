package com.firstProject.demo.Clerk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClerkWebhookEvent {
    private ClerkUserDto data;
}
