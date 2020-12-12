package io.swagger.model;

public class ErrorDefinitionErrors   {

  private String errorCode;
  private String errorMessage;

  public ErrorDefinitionErrors errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorDefinitionErrors errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }


  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public String toString() {
    return "ErrorDefinitionErrors{" +
            "errorCode='" + errorCode + '\'' +
            ", errorMessage='" + errorMessage + '\'' +
            '}';
  }
}
