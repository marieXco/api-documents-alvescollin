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
@NoArgsConstructor
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
  private List<ErrorDefinitionErrors> errors;

  public ErrorDefinition errorType(ErrorTypeEnum errorType) {
    this.errorType = errorType;
    return this;
  }


  public ErrorTypeEnum getErrorType() {
    return errorType;
  }

  public void setErrorType(ErrorTypeEnum errorType) {
    this.errorType = errorType;
  }

  public ErrorDefinition errors(List<ErrorDefinitionErrors> errors) {
    this.errors = errors;
    return this;
  }

  public ErrorDefinition addErrorsItem(ErrorDefinitionErrors errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<ErrorDefinitionErrors>();
    }
    this.errors.add(errorsItem);
    return this;
  }

  public List<ErrorDefinitionErrors> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDefinitionErrors> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "ErrorDefinition{" +
            "errorType=" + errorType +
            ", errors=" + errors +
            '}';
  }
}
