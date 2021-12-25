package no.linska.webapp.validator.password;

import no.linska.webapp.validator.password.ValidPassword;
import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }



    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new WhitespaceRule(),
                new CharacterRule(EnglishCharacterData.UpperCase, 1))

);

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {

            return true;
        }



        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                validator
                        .getMessages(result)
                        .stream()
                        .collect(Collectors.joining(",")))
                .addConstraintViolation();
        return false;
    }
}