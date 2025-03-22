package me.dionclei.dslist.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import me.dionclei.dslist.services.exceptions.GameIndexOutOfBoundsException;
import me.dionclei.dslist.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionsHandlerControl {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceException(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> databaseException(DataIntegrityViolationException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, "You can not delete an entity that results on an integrity violation", request.getRequestURI()); 
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PageException.class)
	public ResponseEntity<StandardError> pageException(PageException e, HttpServletRequest request) {
		String error = "Paged result error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e, HttpServletRequest request) {
		String error = "Validation error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<String> list = new ArrayList<>();
		e.getAllErrors().forEach(ex -> { 
			list.add(ex.getDefaultMessage());
		});
		StandardError err = new StandardError(Instant.now(), status.value(), error, list.toString(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(GameIndexOutOfBoundsException.class)
	public ResponseEntity<StandardError> moveGameException(GameIndexOutOfBoundsException e, HttpServletRequest request) {
		String error = "Moving game error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
