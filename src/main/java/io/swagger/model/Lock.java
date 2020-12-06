package io.swagger.model;

import java.time.LocalDateTime;

import io.swagger.dto.LockDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lock   {

  @NotNull
  private String documentId;

  @NotNull
  private String owner;

  @CreatedDate
  private LocalDateTime created;

  @Override
  public String toString() {
    return "Lock{" +
            "owner='" + owner + '\'' +
            ", created=" + created +
            '}';
  }

  public LockDto toDto() {
    return LockDto.builder()
            .documentId(documentId)
            .owner(owner)
            .created(created)
            .build();
  }
}
