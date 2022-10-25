package in.joshbetz.careproject.snapshot;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

@Data
public class PatientSnapshot {

    @Id
    private String id;
    private String user;
    private Map<String, String> answers;

    public PatientSnapshot(String user) {
        this.user = user;
        this.answers = new HashMap<>();
    }

    public String getAnswer(String questionKey) {
        return answers.getOrDefault(questionKey, "Not Answered");
    }

    public void setAnswer(String questionKey, String answer) {
        answers.put(questionKey, answer);
    }

    public boolean hasAnswered(String questionKey) {
        return answers.containsKey(questionKey);
    }
}
