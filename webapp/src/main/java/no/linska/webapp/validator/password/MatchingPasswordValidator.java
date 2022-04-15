package no.linska.webapp.validator.password;

import no.linska.webapp.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPassword, UserRegistrationDto> {
    @Override
    public boolean isValid(UserRegistrationDto user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
