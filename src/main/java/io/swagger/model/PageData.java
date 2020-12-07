package io.swagger.model;


public class PageData   {
  private Integer page;
  private Integer nbElements;

  public PageData page(Integer page) {
    this.page = page;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public PageData nbElements(Integer nbElements) {
    this.nbElements = nbElements;
    return this;
  }



  public Integer getNbElements() {
    return nbElements;
  }

  public void setNbElements(Integer nbElements) {
    this.nbElements = nbElements;
  }


  @Override
  public String toString() {
    return "PageData{" +
            "page=" + page +
            ", nbElements=" + nbElements +
            '}';
  }
}
