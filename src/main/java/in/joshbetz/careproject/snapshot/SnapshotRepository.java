package in.joshbetz.careproject.snapshot;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SnapshotRepository extends MongoRepository<PatientSnapshot, String> {

    Optional<PatientSnapshot> findByUser(String username);

}
