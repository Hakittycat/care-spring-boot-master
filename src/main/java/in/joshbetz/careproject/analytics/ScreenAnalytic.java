package in.joshbetz.careproject.analytics;

import lombok.Data;

@Data
public class ScreenAnalytic {

    private final String username;
    private final String screenId;
    private final int time;
    private final String timestamp;

}
