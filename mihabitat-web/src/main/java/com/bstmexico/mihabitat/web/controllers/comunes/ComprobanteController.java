package com.bstmexico.mihabitat.web.controllers.comunes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.web.dto.FileMetaData;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "comprobante")
public class ComprobanteController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ComprobanteController.class);

	public static final String COMPROBANTE = "comprobante";

	@RequestMapping(method = RequestMethod.POST, value = "subir")
	public @ResponseBody FileMetaData processUpload(
			@RequestParam MultipartFile file, HttpServletRequest request,
			HttpSession session) {

		session.removeAttribute(COMPROBANTE);

		FileMetaData fileMeta = new FileMetaData();
		fileMeta.setName(file.getOriginalFilename());
		fileMeta.setSize(file.getSize() / 1024 + " Kb");
		fileMeta.setType(file.getContentType());

		try {
			fileMeta.setBytes(file.getBytes());
		} catch (IOException ex) {
			String message = "Error al intentar subir el archivo al servidor.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
		session.setAttribute(COMPROBANTE, fileMeta);
		return fileMeta;
	}

	@RequestMapping(method = RequestMethod.POST, value = "eliminararchivo")
	public @ResponseBody FileMetaData processDelete(
			@RequestParam String file, HttpServletRequest request,
			HttpSession session) {

		FileMetaData fileMetaData = (FileMetaData)session.getAttribute(COMPROBANTE);

		if (fileMetaData != null && file != null
				&& file.equals(fileMetaData.getName())) {
			session.removeAttribute(COMPROBANTE);
		} else {
			fileMetaData = new FileMetaData();
		}

		return fileMetaData;
	}

	@RequestMapping(value = "comprobantepornombre", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getComprobanteByName(@RequestParam String nombre, HttpSession session) {
		FileMetaData fileMetaData = (FileMetaData)session.getAttribute(COMPROBANTE);
		if(fileMetaData != null && fileMetaData.getName().equals(nombre)) {
			byte[] bytes = fileMetaData.getBytes();
			HttpHeaders headers = new HttpHeaders();
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			String filename = null;

			try {
				String extension = Magic.getMagicMatch(bytes).getExtension();
				filename = "comprobante_" + String.valueOf(fileMetaData.getName()) + "."
						+ extension;
				if (extension.equals("pdf")) {
					headers.setContentType(MediaType
							.parseMediaType("application/pdf"));
				} else if (extension.equals("doc")) {
					headers.setContentType(MediaType
							.parseMediaType("application/ms-word"));
				} else if (extension.equals("docx")) {
					headers.setContentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
				} else if (extension.equals("jpg")) {
					headers.setContentType(MediaType.parseMediaType("image/jpeg"));
				} else if (extension.equals("gif")) {
					headers.setContentType(MediaType.parseMediaType("image/gif"));
				} else if (extension.equals("png")) {
					headers.setContentType(MediaType.parseMediaType("image/png"));
				}
			} catch (MagicParseException | MagicMatchNotFoundException
					| MagicException e) {
				LOG.warn("Error al procesar el archivo " + e);
			}
			headers.setContentDispositionFormData(filename, filename);
			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		}
		return null;
	}
}
