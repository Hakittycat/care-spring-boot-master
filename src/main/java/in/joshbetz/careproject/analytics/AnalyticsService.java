package in.joshbetz.careproject.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ClickAnalyticsRepository clickAnalyticsRepository;
    private final ScreenAnalyticsRepository screenAnalyticsRepository;

    public void recordClick(String user, String buttonId) {
        ClickAnalytic analytic = new ClickAnalytic(user, buttonId, new Date().toString());
        clickAnalyticsRepository.insert(analytic);
    }

    public void recordScreenTime(String user, String screenId, int timeInSeconds) {
        ScreenAnalytic analytic = new ScreenAnalytic(user, screenId, timeInSeconds, new Date().toString());
        screenAnalyticsRepository.insert(analytic);
    }
}
