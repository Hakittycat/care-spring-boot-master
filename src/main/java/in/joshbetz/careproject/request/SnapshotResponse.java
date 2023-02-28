package in.joshbetz.careproject.request;

import lombok.Data;

import java.util.Map;

@Data
public class SnapshotResponse {

    private final Map<String, String> answers;

}
