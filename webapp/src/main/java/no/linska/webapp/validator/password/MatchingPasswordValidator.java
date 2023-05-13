package no.linska.webapp.validator.password;

import no.linska.webapp.dto.RegistrationRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPassword, RegistrationRequestDto> {
    @Override
    public boolean isValid(RegistrationRequestDto user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
