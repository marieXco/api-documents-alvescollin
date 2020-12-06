package io.swagger.repository;

import io.swagger.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockRepository extends MongoRepository<Lock, String> {
    Lock findLockByDocumentId(String documentId);
    void deleteLockByDocumentId(String documentId);
}
