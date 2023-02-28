package in.joshbetz.careproject.analytics;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScreenAnalyticsRepository extends MongoRepository<ScreenAnalytic, String> {

    List<ScreenAnalytic> findAllByUsername(String username);

}
