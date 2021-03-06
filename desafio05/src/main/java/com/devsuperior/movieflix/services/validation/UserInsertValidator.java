package com.devsuperior.movieflix.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.devsuperior.movieflix.controllers.exceptions.FieldMessage;
import com.devsuperior.movieflix.dtos.UserInsertDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator  implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsertValid ann) { // Método que recebe alguma lógica quando o objeto for inicializado
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) { // Testa se o objeto UserInsertDTO vai ser válido ou não
		
		List<FieldMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		User user = repository.findByEmail(dto.getEmail());
		if (user != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		for (FieldMessage e : list) { // Percorre a lista de FieldMessage para inserir os erros na lista de Beans validation
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
