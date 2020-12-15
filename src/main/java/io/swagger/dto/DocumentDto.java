package io.swagger.dto;

import io.swagger.model.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private String documentId;
    private String creator;
    private String editor;
    private String body;
    private String title;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer version;

    public Document toEntity() {
        return Document.builder()
                .documentId(documentId)
                .creator(creator)
                .editor(editor)
                .body(body)
                .title(title)
                .version(version)
                .build();
    }

}
