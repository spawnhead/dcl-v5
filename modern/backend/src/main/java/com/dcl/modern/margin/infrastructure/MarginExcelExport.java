package com.dcl.modern.margin.infrastructure;

import com.dcl.modern.margin.domain.MarginLine;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Export Margin grid to Excel (CONTRACTS.md Margin Excel Export).
 * Legacy: MarginAction.generateExcel; this phase: POI XSSF.
 */
public final class MarginExcelExport {

    private MarginExcelExport() {}

    public static byte[] toBytes(List<MarginLine> data) {
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("Маржа");
            Row header = sheet.createRow(0);
            String[] headers = {
                "Контрагент", "Страна", "№ контракта", "Дата контракта", "№ спецификации", "Дата спецификации",
                "Сумма", "Валюта", "Продукт", "№ отгрузки", "Дата отгрузки", "Дата оплаты",
                "Сумма EUR", "Сумма без НДС", "Транспорт", "Транспорт Минск-Клиент", "Таможенные", "Логистика",
                "Монтаж и наладка", "Время на монтаж", "Ст-ть монтажа", "Корректировка", "Сумма товара", "Сумма закупки",
                "Маржа", "Средний коэфф-т", "Пользователь", "Отдел"
            };
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (MarginLine line : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(line.ctr_name());
                row.createCell(1).setCellValue(line.cut_name());
                row.createCell(2).setCellValue(line.con_number_formatted());
                row.createCell(3).setCellValue(line.con_date_formatted());
                row.createCell(4).setCellValue(line.spc_number_formatted());
                row.createCell(5).setCellValue(line.spc_date_formatted());
                row.createCell(6).setCellValue(line.spc_summ_formatted());
                row.createCell(7).setCellValue(line.cur_name());
                row.createCell(8).setCellValue(line.stf_name_show());
                row.createCell(9).setCellValue(line.shp_number_show());
                row.createCell(10).setCellValue(line.shp_date_show());
                row.createCell(11).setCellValue(line.pay_date_show());
                row.createCell(12).setCellValue(line.lps_summ_eur_formatted());
                row.createCell(13).setCellValue(line.lps_summ_formatted());
                row.createCell(14).setCellValue(line.lps_sum_transport_formatted());
                row.createCell(15).setCellValue(line.lcc_transport_formatted());
                row.createCell(16).setCellValue(line.lps_custom_formatted());
                row.createCell(17).setCellValue(line.lcc_charges_formatted());
                row.createCell(18).setCellValue(line.lcc_montage_formatted());
                row.createCell(19).setCellValue(line.lps_montage_time_formatted());
                row.createCell(20).setCellValue(line.montage_cost_formatted());
                row.createCell(21).setCellValue(line.lcc_update_sum_formatted());
                row.createCell(22).setCellValue(line.summ_formatted());
                row.createCell(23).setCellValue(line.summ_zak_formatted());
                row.createCell(24).setCellValue(line.margin_formatted());
                row.createCell(25).setCellValue(line.koeff_formatted());
                row.createCell(26).setCellValue(line.usr_name_show());
                row.createCell(27).setCellValue(line.dep_name_show());
            }
            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Margin Excel export failed", e);
        }
    }
}
