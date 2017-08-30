package com.demo.app.configuration.exceptions;

import java.time.LocalDateTime;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.demo.app.domain.exception.ExceptionInfo;
import com.demo.app.domain.exception.ExceptionType;
import com.demo.app.service.exception.ExceptionInfoService;

@Provider
public class ExceptionsMapper implements ExceptionMapper<Exception> {

	@Autowired
	ExceptionInfoService exceptionInfoService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Response toResponse(Exception e) {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ExceptionType type = AppException.class.isAssignableFrom(e.getClass()) ? ExceptionType.APP_EXCEPTION : ExceptionType.EXCEPTION;
		ExceptionInfo excInfo = new ExceptionInfo(LocalDateTime.now(), username, type, e.getMessage(), e.getStackTrace());
		// Insert the exception on the database
		try {
			exceptionInfoService.saveOne(excInfo);
		} catch (AppException e1) {
			logger.error("Error inserting exception in database", e1);
		}

		excInfo.hideDetails();
		// Sent back to the front end
		ResponseEntity<ExceptionInfo> re = new ResponseEntity<>(excInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(re).build();
	}
}