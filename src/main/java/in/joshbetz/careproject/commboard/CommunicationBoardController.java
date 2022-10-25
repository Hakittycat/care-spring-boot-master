package in.joshbetz.careproject.commboard;


import in.joshbetz.careproject.contentful.ContentfulCommunicationBoardService;
import in.joshbetz.careproject.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunicationBoardController {

    private final ContentfulCommunicationBoardService service;

    @GetMapping("/categoryList")
    public ResponseEntity<?> getCategoryList() {
        return ResponseEntity.ok(service.getBasicCategoryData());
    }

    @GetMapping("/categoryButtons/{id}")
    public ResponseEntity<?> getCommunicationCategory(@PathVariable("id") String id) {
        CommunicationCategoryData data = service.getCategory(id)
                .orElseThrow(() -> new RequestException("Invalid communication board category!"));
        return ResponseEntity.ok(data.getButtons());
    }
}
