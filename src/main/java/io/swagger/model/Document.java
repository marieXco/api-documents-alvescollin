package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.dto.DocumentDto;
import io.swagger.utils.RestUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import javafx.scene.control.PopupControlBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.annotation.Generated;

/**
 * a document
 */

@Builder
@Schema(description = "a document")
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-12-05T07:54:55.839Z[GMT]")
@Data
@org.springframework.data.mongodb.core.mapping.Document
public class Document {
  @Id
  @NonNull
  @JsonProperty("documentId")
  private String documentId;

  @CreatedDate
  @JsonProperty("created")
  private LocalDateTime created;

  @LastModifiedDate
  @JsonProperty("updated")
  private LocalDateTime updated;

  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("editor")
  private String editor = null;

  @JsonProperty("body")
  private String body = null;

  @JsonProperty("title")
  private String title = null;

  /**
   * statut du document
   */
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
  @JsonProperty("status")
  private StatusEnum status = null;

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
            .created(RestUtils.convertToZoneDateTime(created))
            .updated(RestUtils.convertToZoneDateTime(updated))
            .build();
  }
}
