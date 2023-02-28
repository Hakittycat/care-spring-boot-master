package in.joshbetz.careproject.story;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Story {

    private final String id;
    private final String name;
    private final String icon;
    private final String description;
    private final List<StoryPage> pages;

}
