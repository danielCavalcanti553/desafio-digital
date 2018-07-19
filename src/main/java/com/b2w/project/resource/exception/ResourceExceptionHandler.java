package com.b2w.project.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.b2w.project.service.exception.ObjectNotFoundException;
import com.b2w.project.service.exception.UnavailableServiceException;

/*
 * Intercepta as requisições, resposta personalizada
 * */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> notFound(ObjectNotFoundException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError erro = new StandardError(System.currentTimeMillis(),status.value(),"Não Encontrado",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ErrorValidation err = new ErrorValidation(System.currentTimeMillis(),  HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação",e.getMessage(), request.getRequestURI()); 
		
		for(FieldError x: e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(UnavailableServiceException.class)
	public ResponseEntity<StandardError> unavailableService(UnavailableServiceException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
		StandardError erro = new StandardError(System.currentTimeMillis(),status.value(),"Não Encontrado",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(erro);
	}
	
}
