package in.joshbetz.careproject.story;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StoryPage {

    private final String text;
    private final String imageUrl;
    private final String audioUrl;

}
