package in.joshbetz.careproject.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class SnapshotUpdateRequest {

    private JsonNode data;

}
