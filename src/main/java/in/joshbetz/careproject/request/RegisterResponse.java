package in.joshbetz.careproject.request;

import lombok.Data;

@Data
public class RegisterResponse {

    private final String message;
    private final boolean success;
}
