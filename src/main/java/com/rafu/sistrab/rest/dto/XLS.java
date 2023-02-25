package com.rafu.sistrab.rest.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class XLS {
  private final byte[] content;
  private final String contentType;
  private final String fileExtension;

  public XLS(final byte[] array, final String contentType, final String fileExtension) {
    this.content = array;
    this.contentType = contentType;
    this.fileExtension = fileExtension;
  }

  public String getName() {
    return String.format("relatorio-%s", LocalDate.now().getMonth().name());
  }
}
