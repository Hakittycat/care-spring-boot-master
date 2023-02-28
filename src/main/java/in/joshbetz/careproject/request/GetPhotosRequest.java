package in.joshbetz.careproject.request;

import lombok.Data;

@Data
public class GetPhotosRequest {
    private final String username;
    private final String photoType;
}
