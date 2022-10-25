package in.joshbetz.careproject.analytics;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickAnalyticsRepository extends MongoRepository<ClickAnalytic, String> {

}
