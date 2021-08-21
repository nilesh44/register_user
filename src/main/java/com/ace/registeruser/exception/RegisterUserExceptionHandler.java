package com.ace.registeruser.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ace.registeruser.vo.create.ConstraintVoilationError;
import com.ace.registeruser.vo.create.Error;

@ControllerAdvice
public class RegisterUserExceptionHandler {

	@ExceptionHandler(RecordAlradyPresent.class)
	public ResponseEntity<Error> recordAlradyPresentHandler(RecordAlradyPresent ex) {
		return new ResponseEntity<Error>(Error.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Error> recordNotFoundExceptionHandler(RecordNotFoundException ex) {
		return new ResponseEntity<Error>(Error.builder().message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Error> InvalidDataExceptionExceptionHandler(InvalidDataException ex) {
		return new ResponseEntity<Error>(Error.builder().message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<ConstraintVoilationError>> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		List<ConstraintVoilationError> errors=new ArrayList<ConstraintVoilationError>();
		ex.getConstraintViolations().forEach(x->{
			
			errors.add(	ConstraintVoilationError.builder().invalidValue(x.getInvalidValue()).message(x.getMessage()).build());
		});
	
		
		return new ResponseEntity<List<ConstraintVoilationError>>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
	
		FieldError fieldError=ex.getBindingResult().getFieldError();
		return new ResponseEntity<Error>(Error.builder().message(fieldError.getRejectedValue()+" "+fieldError.getDefaultMessage()).build(), HttpStatus.BAD_REQUEST);

	}
	
}
