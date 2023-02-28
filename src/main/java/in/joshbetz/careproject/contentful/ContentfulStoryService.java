package in.joshbetz.careproject.contentful;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import in.joshbetz.careproject.story.Story;
import in.joshbetz.careproject.story.StoryPage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentfulStoryService {

    private final CDAClient contentfulClient;
    private final List<Story> storyList = new ArrayList<>();

    private static Story createStoryFrom(CDAEntry entry) {
        if (entry.contentType().getAttribute("id").equals("story")) {
            String name = entry.getField("name");
            String id = entry.getField("id");
            String iconUrl = entry.getField("icon");
            String description = entry.getField("description");
            List<StoryPage> pages = fromEntries(entry.getField("pages"));
            return new Story(id, name, iconUrl, description, pages);
        }
        return null;
    }

    private static List<StoryPage> fromEntries(List<CDAEntry> entries) {
        return entries.stream().map(ContentfulStoryService::createStoryPageFrom).collect(Collectors.toList());
    }

    private static StoryPage createStoryPageFrom(CDAEntry entry) {
        String text = entry.getField("text");
        String imageUrl = entry.getField("imageUrl");
        String audioUrl = entry.getField("audioUrl");
        return new StoryPage(text, imageUrl, audioUrl);
    }

    public synchronized void loadStories() {
        Collection<CDAEntry> entries = contentfulClient.fetch(CDAEntry.class)
                .withContentType("story")
                .all()
                .items()
                .stream()
                .map(resource -> (CDAEntry) resource)
                .collect(Collectors.toList());
        storyList.clear();
        List<Story> stories = entries.stream().map(ContentfulStoryService::createStoryFrom).collect(Collectors.toList());
        storyList.addAll(stories);
    }

    public synchronized List<Story> getStories() {
        return Collections.unmodifiableList(storyList);
    }

    public synchronized Story getStoryData(String storyName) throws IllegalArgumentException {
        return storyList.stream()
                .filter(story -> story.getName().equalsIgnoreCase(storyName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid story name was given"));
    }

    @Scheduled(fixedRate = 300000)
    public void scheduleStoryUpdates() {
        loadStories();
    }
}
