package in.joshbetz.careproject.analytics;

import in.joshbetz.careproject.request.ClickDataRequest;
import in.joshbetz.careproject.request.ScreenDataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping("/analytics/buttonClick")
    public ResponseEntity<?> click(@RequestBody ClickDataRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        analyticsService.recordClick(username, request.getButton());
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/analytics/screenTime")
    public ResponseEntity<?> screenTime(@RequestBody ScreenDataRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        analyticsService.recordScreenTime(username, request.getScreen(), request.getTime());
        return ResponseEntity.ok("Success");
    }
}
