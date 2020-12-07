package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DocumentSummary {

  private String documentId;
  private LocalDateTime created;
  private LocalDateTime updated;
  private String title;

  public DocumentSummary(String documentId, LocalDateTime created, LocalDateTime updated, String title) {
    this.documentId = documentId;
    this.created = created;
    this.updated = updated;
    this.title = title;
  }

  @Override
  public String toString() {
    return "DocumentSummary{" +
            "documentId='" + documentId + '\'' +
            ", created=" + created +
            ", updated=" + updated +
            ", title='" + title + '\'' +
            '}';
  }
}
