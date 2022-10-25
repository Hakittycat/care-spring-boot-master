package in.joshbetz.careproject.analytics;

import lombok.Data;

@Data
public class ClickAnalytic {

    private final String username;
    private final String buttonId;
    private final String timestamp;

}
