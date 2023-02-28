package in.joshbetz.careproject.snapshot;

import com.fasterxml.jackson.databind.JsonNode;
import in.joshbetz.careproject.exception.RequestException;
import in.joshbetz.careproject.request.MessageResponse;
import in.joshbetz.careproject.request.SnapshotResponse;
import in.joshbetz.careproject.request.SnapshotUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SnapshotController {

    private final SnapshotRepository repository;

    @GetMapping("/snapshot/get")
    public ResponseEntity<?> getSnapshot() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<PatientSnapshot> optional = repository.findByUser(username);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new SnapshotResponse(optional.get().getAnswers()));
        } else {
            PatientSnapshot snapshot = new PatientSnapshot(username);
            repository.insert(snapshot);
            return ResponseEntity.ok(snapshot.getAnswers());
        }
    }

    @PostMapping("/snapshot/update")
    public ResponseEntity<?> updateSnapshot(@RequestBody SnapshotUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        PatientSnapshot snapshot = repository.findByUser(username)
                .orElseThrow(() -> new RequestException("Unable to save snapshot: Snapshot data not found!"));
        JsonNode update = request.getData();
        update.fieldNames().forEachRemaining(field -> {
            String value = update.get(field).asText();
            snapshot.setAnswer(field, value);
        });
        repository.save(snapshot);
        return ResponseEntity.ok(new MessageResponse("Successfully saved snapshot!"));
    }
}
