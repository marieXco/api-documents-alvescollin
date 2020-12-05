package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * a document
 */
@Schema(description = "a document")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-12-05T07:54:55.839Z[GMT]")

public class Document {
  @Id
  @NonNull
  @JsonProperty("documentId")
  private String documentId;

  @CreatedDate
  @JsonProperty("created")
  private OffsetDateTime created;

  @LastModifiedDate
  @JsonProperty("updated")
  private OffsetDateTime updated;

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

  public Document creator(String creator) {
    this.creator = creator;
    return this;
  }

  /**
   * le nom du créateur du document
   * @return creator
   **/
  @Schema(description = "le nom du créateur du document")
  
    public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Document editor(String editor) {
    this.editor = editor;
    return this;
  }

  /**
   * le nom du dernier utilisateur qui l'a mis à jour
   * @return editor
   **/
  @Schema(description = "le nom du dernier utilisateur qui l'a mis à jour")
  
    public String getEditor() {
    return editor;
  }

  public void setEditor(String editor) {
    this.editor = editor;
  }

  public Document body(String body) {
    this.body = body;
    return this;
  }

  /**
   * le texte du document
   * @return body
   **/
  @Schema(description = "le texte du document")
  
    public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Document status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * statut du document
   * @return status
   **/
  @Schema(description = "statut du document")
  
    public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Document documentId(String documentId) {
    this.documentId = documentId;
    return this;
  }

  /**
   * identifiant du document
   * @return documentId
   **/
  @Schema(description = "identifiant du document")

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public Document created(OffsetDateTime created) {
    this.created = created;
    return this;
  }

  /**
   * la date de création
   * @return created
   **/
  @Schema(description = "la date de création")

  @Valid
  public OffsetDateTime getCreated() {
    return created;
  }

  public void setCreated(OffsetDateTime created) {
    this.created = created;
  }

  public Document updated(OffsetDateTime updated) {
    this.updated = updated;
    return this;
  }

  /**
   * date de la mise à jour
   * @return updated
   **/
  @Schema(description = "date de la mise à jour")

  @Valid
  public OffsetDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(OffsetDateTime updated) {
    this.updated = updated;
  }

  public Document title(String title) {
    this.title = title;
    return this;
  }

  /**
   * titre du document
   * @return title
   **/
  @Schema(description = "titre du document")

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Document document = (Document) o;
    return Objects.equals(creator, document.creator) &&
            Objects.equals(editor, document.editor) &&
            Objects.equals(body, document.body) &&
            Objects.equals(documentId, document.documentId) &&
            Objects.equals(created, document.created) &&
            Objects.equals(updated, document.updated) &&
            Objects.equals(title, document.title) &&
            status == document.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(creator, editor, body, documentId, created, updated, title, status);
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
}
