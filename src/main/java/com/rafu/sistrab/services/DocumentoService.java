package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.errors.DocumentoException;
import com.rafu.sistrab.rest.dto.RelatorioTarefasDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentoService {
  private static final String[] COLUNAS = {
    "Author",
    "Summary",
    "Issue",
    "Parent",
    "Epic",
    "Started",
    "Assignee",
    "Status",
    "Issue Type",
    "sum(Time spent)",
    "total(R$)"
  };
  private static final String AUTOR = "Rafael Henrique";

  public ByteArrayOutputStream getRelatorio(final List<Tarefa> tarefas) {
    try (final var workbook = new HSSFWorkbook()) {
      final var sheet = workbook.createSheet();
      final var header = sheet.createRow(0);

      final var headerStyle = workbook.createCellStyle();

      final var font = workbook.createFont();
      font.setFontName("Calibri");
      font.setFontHeightInPoints((short) 11);
      font.setBold(true);
      headerStyle.setFont(font);

      for (int i = 0; i < COLUNAS.length; i++) {
        final var headerCell = header.createCell(i);
        headerCell.setCellValue(COLUNAS[i]);
        headerCell.setCellStyle(headerStyle);
      }
      final var secondRow = sheet.createRow(1);

      final var headerCell = secondRow.createCell(1);
      headerCell.setCellValue(AUTOR);
      headerCell.setCellStyle(headerStyle);

      final var fileOut = new ByteArrayOutputStream();
      workbook.write(fileOut);
      return fileOut;

    } catch (IOException ex) {
      throw new DocumentoException(ex);
    }
  }

  public String getRelatorioCSV(final RelatorioTarefasDto dto) {
    final var sb = new StringBuilder();
    sb.append(String.join(";", COLUNAS));
    sb.append("\n");
    for (int i = 0; i < COLUNAS.length; i++) {
      if (i == 0) {
        sb.append(AUTOR).append(";");
      }
      if (i == 9) {
        sb.append(dto.getHoras()).append(";");
      }
      if (i == 10) {
        sb.append(dto.getTotal()).append(";");
      }
      if (i != 0 && i != 9 && i != 10) {
        sb.append(" - ").append(";");
      }
    }
    sb.append("\n");
    dto.getRelatorioTarefa()
        .forEach(
            d -> {
              sb.append(";")
                  .append(d.getDescricao())
                  .append(";")
                  .append(d.getCodigo())
                  .append(";")
                  .append(d.getCodigo())
                  .append(";")
                  .append(" - ")
                  .append(";")
                  .append(d.getInicio())
                  .append(";")
                  .append(AUTOR)
                  .append(";")
                  .append("Concluído")
                  .append(";")
                  .append(d.getTipoTarefa())
                  .append(";")
                  .append(d.getHoras())
                  .append(";")
                  .append("\n");
            });
    return sb.toString();
  }
}
