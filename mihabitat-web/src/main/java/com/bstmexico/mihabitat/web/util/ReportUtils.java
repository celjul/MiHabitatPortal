package com.bstmexico.mihabitat.web.util;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class ReportUtils {

	public final String PDF = "pdf";
	public final String XLSX = "xlsx";
	public final String CSV = "csv";
	public final String TXT = "txt";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] export(String formato, String sourceFile, Map parameters, JRDataSource jrDataSource) {
		
		try {
			switch (formato) {
				case PDF:
						return JasperRunManager.runReportToPdf(sourceFile, parameters, jrDataSource);
						
				case XLSX:
					JasperPrint jasperPrintXlsx = JasperFillManager.fillReport(sourceFile, parameters, jrDataSource);
					
					JRXlsxExporter exporterXlsx = new JRXlsxExporter();
					exporterXlsx.setExporterInput(new SimpleExporterInput(jasperPrintXlsx));
					
					SimpleXlsxReportConfiguration configurationXlsx = new SimpleXlsxReportConfiguration();
					configurationXlsx.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
					configurationXlsx.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
					configurationXlsx.setDetectCellType(Boolean.TRUE);
					exporterXlsx.setConfiguration(configurationXlsx);
					
					ByteArrayOutputStream streamXlsx = new ByteArrayOutputStream();
					SimpleOutputStreamExporterOutput outputXlsx = new SimpleOutputStreamExporterOutput(streamXlsx);
					exporterXlsx.setExporterOutput(outputXlsx);
					
					exporterXlsx.exportReport();
					outputXlsx.close();
					return streamXlsx.toByteArray();
					
				case CSV:
					JasperPrint jasperPrintCsv = JasperFillManager.fillReport(sourceFile, parameters, jrDataSource);
					
					JRCsvExporter exporterCsv = new JRCsvExporter();
					exporterCsv.setExporterInput(new SimpleExporterInput(jasperPrintCsv));
					
					ByteArrayOutputStream streamCsv = new ByteArrayOutputStream();
					SimpleWriterExporterOutput outputCsv = new SimpleWriterExporterOutput(streamCsv);
					exporterCsv.setExporterOutput(outputCsv);
					
					exporterCsv.exportReport();
					outputCsv.close();
					return streamCsv.toByteArray();
					
				case TXT:
					JasperPrint jasperPrintTxt = JasperFillManager.fillReport(sourceFile, parameters, jrDataSource);
					
					JRCsvExporter exporterTxt = new JRCsvExporter();
					exporterTxt.setExporterInput(new SimpleExporterInput(jasperPrintTxt));
					
					SimpleCsvExporterConfiguration configurationTxt = new SimpleCsvExporterConfiguration();
					configurationTxt.setFieldDelimiter("|");
					configurationTxt.setRecordDelimiter("\r\n");
					exporterTxt.setConfiguration(configurationTxt);
					
					ByteArrayOutputStream streamTxt = new ByteArrayOutputStream();
					SimpleWriterExporterOutput outputTxt = new SimpleWriterExporterOutput(streamTxt);
					exporterTxt.setExporterOutput(outputTxt);
					
					exporterTxt.exportReport();
					outputTxt.close();
					return streamTxt.toByteArray();
					
				default:
					throw new ServiceException("Formato no soportado");
			}
		} catch (JRException e) {
			throw new ServiceException(e);
		}
	}
	
	public void setHttpHeaders(HttpHeaders headers, String formato) {
		switch (formato) {
			case PDF:
				headers.setContentType(MediaType.parseMediaType("application/pdf"));
				break;
			case XLSX:
				headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
				break;
			case CSV:
				headers.setContentType(MediaType.parseMediaType("text/csv"));
				break;
			case TXT:
				headers.setContentType(MediaType.parseMediaType("text/plain"));
			break;
		}
	}
}
