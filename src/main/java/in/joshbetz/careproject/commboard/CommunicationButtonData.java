package in.joshbetz.careproject.commboard;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommunicationButtonData {

    private final String name;
    private final String iconUrl;
    private final String audioUrl;

}
