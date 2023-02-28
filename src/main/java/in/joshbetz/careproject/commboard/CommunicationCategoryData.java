package in.joshbetz.careproject.commboard;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class CommunicationCategoryData {

    private final String name;
    private final String icon;
    private final List<CommunicationButtonData> buttons;

}
