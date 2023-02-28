package in.joshbetz.careproject.request;

import in.joshbetz.careproject.user.ResponsePhoto;
import lombok.Data;
import okhttp3.Response;

import java.util.ArrayList;

@Data
public class GetPhotosResponse {
    private final ArrayList<ResponsePhoto> photos;
}
