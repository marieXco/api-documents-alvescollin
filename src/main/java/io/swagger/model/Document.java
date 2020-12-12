package io.swagger.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.dto.DocumentDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;


@Builder
@Data
@org.springframework.data.mongodb.core.mapping.Document
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Document {

  @Id
  private String documentId;

  @CreatedDate
  private LocalDateTime created;

  @LastModifiedDate
  private LocalDateTime updated;

  private String creator;

  private String editor;

  private String body;

  private String title;

  private StatusEnum status;

  public enum StatusEnum {
    CREATED("CREATED"),

    VALIDATED("VALIDATED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public StatusEnum getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "Document{" +
            "creator='" + creator + '\'' +
            ", editor='" + editor + '\'' +
            ", body='" + body + '\'' +
            ", documentId='" + documentId + '\'' +
            ", created=" + created +
            ", updated=" + updated +
            ", title='" + title + '\'' +
            ", status=" + status +
            '}';
  }

  public DocumentDto toDto() {
    return DocumentDto.builder()
            .documentId(documentId)
            .creator(creator)
            .editor(editor)
            .body(body)
            .title(title)
            .created(created)
            .updated(updated)
            .build();
  }

}
