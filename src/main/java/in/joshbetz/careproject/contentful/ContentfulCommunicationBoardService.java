package in.joshbetz.careproject.contentful;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import in.joshbetz.careproject.commboard.BasicCategoryData;
import in.joshbetz.careproject.commboard.CommunicationButtonData;
import in.joshbetz.careproject.commboard.CommunicationCategoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentfulCommunicationBoardService {

    private final CDAClient contentfulClient;
    private final List<CommunicationCategoryData> categories;

    private static CommunicationCategoryData createCategory(CDAEntry entry) {
        String name = entry.getField("name");
        String icon = entry.getField("iconUrl");
        List<CommunicationButtonData> buttons = fromEntries(entry.getField("buttons"));
        return new CommunicationCategoryData(name, icon, buttons);
    }

    private static List<CommunicationButtonData> fromEntries(List<CDAEntry> entries) {
        return entries.stream().map(ContentfulCommunicationBoardService::createButtonData).collect(Collectors.toList());
    }

    private static CommunicationButtonData createButtonData(CDAEntry entry) {
        String name = entry.getField("name");
        String iconUrl = entry.getField("iconUrl");
        String audioUrl = entry.getField("audioUrl");
        return new CommunicationButtonData(name, iconUrl, audioUrl);
    }

    public synchronized List<BasicCategoryData> getBasicCategoryData() {
        return categories.stream().map(category -> new BasicCategoryData(category.getName(), category.getIcon()))
                .collect(Collectors.toList());
    }

    private synchronized void loadCategories() {
        Collection<CDAEntry> collection = contentfulClient.fetch(CDAEntry.class)
                .withContentType("communicationBoardCategory")
                .all()
                .items()
                .stream().map(resource -> (CDAEntry) resource)
                .collect(Collectors.toList());
        categories.clear();
        List<CommunicationCategoryData> data = collection.stream()
                .map(ContentfulCommunicationBoardService::createCategory)
                .collect(Collectors.toList());
        categories.addAll(data);
    }


    public synchronized List<CommunicationCategoryData> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public synchronized Optional<CommunicationCategoryData> getCategory(String name) {
        return categories.stream().filter(category -> category.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Scheduled(fixedRate = 300000)
    public void scheduleStoryUpdates() {
        loadCategories();
    }
}
