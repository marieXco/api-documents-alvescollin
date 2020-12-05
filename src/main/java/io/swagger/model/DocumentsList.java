package io.swagger.model;

import java.util.ArrayList;
import java.util.List;

public class DocumentsList extends PageData  {

  private List<DocumentSummary> data;

  public DocumentsList data(List<DocumentSummary> data) {
    this.data = data;
    return this;
  }

  public DocumentsList addDataItem(DocumentSummary dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<DocumentSummary>();
    }
    this.data.add(dataItem);
    return this;
  }


  public List<DocumentSummary> getData() {
    return data;
  }

  public void setData(List<DocumentSummary> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "DocumentsList{" +
            "data=" + data +
            '}';
  }
}
