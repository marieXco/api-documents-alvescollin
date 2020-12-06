package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.model.Lock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockDto {
    @JsonIgnore
    private String documentId;
    private String owner;
    private LocalDateTime created;

    public Lock toEntity() {
        return Lock.builder()
                .documentId(documentId)
                .owner(owner)
                .created(created)
                .build();
    }
}
