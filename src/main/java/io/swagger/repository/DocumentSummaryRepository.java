package io.swagger.repository;
import io.swagger.model.DocumentSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentSummaryRepository extends MongoRepository<DocumentSummary, String> {
}
