package com.angela.vjezba6.validation;


import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.angela.vjezba6.model.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;


@Service
public class ReadingValidationService implements ConstraintValidator<ReadingYearValidator, Usage> {

    @Autowired
    private Validator validator;
    private ReadingYearValidator ReadingYearValidator;
    private String month;
    private String year;
    private String errorMessage;
    
	@Override
	public void initialize(ReadingYearValidator constraintAnnotation) { 
		this.ReadingYearValidator = constraintAnnotation;
		month = constraintAnnotation.first();
		year = constraintAnnotation.second();
		errorMessage = constraintAnnotation.errorMessage();
	}

	@Override
	public boolean isValid(Usage usage, ConstraintValidatorContext context) {

		if (usage.getYear() < LocalDate.now().getYear())
			return true;
		if (usage.getYear() == LocalDate.now().getYear() && usage.getMonth() <= LocalDate.now().getMonthValue())
			return true;
		 context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
	                .addNode( year).addConstraintViolation();
		return false;

	}
}
