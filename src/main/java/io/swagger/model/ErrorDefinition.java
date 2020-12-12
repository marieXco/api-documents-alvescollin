package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ErrorDefinition implements Serializable {

  public enum ErrorTypeEnum {
    TECHNICAL("TECHNICAL"),

    FUNCTIONAL("FUNCTIONAL");

    private String value;

    ErrorTypeEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ErrorTypeEnum fromValue(String text) {
      for (ErrorTypeEnum b : ErrorTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  private ErrorTypeEnum errorType;
  private String errorMessage;
  private String errorCode;

  public String getErrorMessage() {
    return errorMessage;
  }

}
