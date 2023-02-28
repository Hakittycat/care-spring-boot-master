package in.joshbetz.careproject.story;

import in.joshbetz.careproject.contentful.ContentfulStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoryController {

    private final ContentfulStoryService storyService;

    @GetMapping("/storyList")
    public ResponseEntity<?> getStories() {
        return ResponseEntity.ok(storyService.getStories());
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<?> getStory(@PathVariable("id") String id) {
        try {
            Story story = storyService.getStoryData(id);
            return ResponseEntity.ok(story);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
