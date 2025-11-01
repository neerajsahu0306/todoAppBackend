package com.firstProject.demo.Clerk;




import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClerkUserDto {
    @JsonProperty("id")
    private String id;


    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("last_name")
    private String last_name;

    @JsonProperty("email_addresses")
    private List<EmailAddressDto> email_addresses;
}
