package com.rafu.sistrab.errors;

import java.io.IOException;

public class DocumentoException extends RuntimeException {
  public DocumentoException(final IOException e) {
    super(e);
  }
}
