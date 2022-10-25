package in.joshbetz.careproject.contentful;

import com.contentful.java.cda.CDAEntry;
import in.joshbetz.careproject.schedule.ScheduleEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContentfulScheduleService {

    private final ContentfulClientProvider clientProvider;
    private final List<ScheduleEvent> eventList;

    private static ScheduleEvent createScheduleEvent(CDAEntry entry) {
        String id = entry.getField("id");
        String name = entry.getField("name");
        String imageUrl = entry.getField("imageUrl");
        return new ScheduleEvent(id, name, imageUrl);
    }

    @Scheduled(fixedRate = 300000)
    private synchronized void load() {
        List<CDAEntry> entries = clientProvider.cdaClient().fetch(CDAEntry.class)
                .withContentType("event")
                .all()
                .items()
                .stream()
                .map(resource -> (CDAEntry) resource)
                .collect(Collectors.toList());
        eventList.clear();
        eventList.addAll(entries.stream()
                .map(ContentfulScheduleService::createScheduleEvent)
                .collect(Collectors.toList()));
    }

    public synchronized List<ScheduleEvent> getEventList() {
        return Collections.unmodifiableList(eventList);
    }

    public synchronized Optional<ScheduleEvent> getEvent(String id) {
        return eventList.stream().filter(event -> event.getId().equalsIgnoreCase(id)).findFirst();
    }
}
