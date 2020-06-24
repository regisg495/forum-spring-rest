package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	@Autowired
	private MessageSource msg;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException e) {
		List<ErroDeFormularioDTO> dto = new ArrayList<>();
		List<FieldError> erros = e.getBindingResult().getFieldErrors();
		
		erros.forEach(erro -> {
			String mensagemErro = msg.getMessage(erro, LocaleContextHolder.getLocale());
			ErroDeFormularioDTO err = new ErroDeFormularioDTO(erro.getField(), mensagemErro);
			dto.add(err);
		});
		
		return dto;
		
	}
	 
}
