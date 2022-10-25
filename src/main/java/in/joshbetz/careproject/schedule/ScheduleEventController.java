package in.joshbetz.careproject.schedule;

import in.joshbetz.careproject.contentful.ContentfulScheduleService;
import in.joshbetz.careproject.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleEventController {

    private final ContentfulScheduleService service;

    @GetMapping("/schedule/eventList")
    public ResponseEntity<List<ScheduleEvent>> getScheduleEvents() {
        return ResponseEntity.ok(service.getEventList());
    }

    @GetMapping("schedule/event/{id}")
    public ResponseEntity<ScheduleEvent> getScheduledEvent(@PathVariable("id") String id) {
        ScheduleEvent event = service.getEvent(id).orElseThrow(() -> new RequestException("Invalid schedule event id"));
        return ResponseEntity.ok(event);
    }
}
