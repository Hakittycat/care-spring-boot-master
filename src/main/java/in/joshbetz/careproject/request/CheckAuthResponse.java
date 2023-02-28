package in.joshbetz.careproject.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckAuthResponse {

    private boolean valid;

}
