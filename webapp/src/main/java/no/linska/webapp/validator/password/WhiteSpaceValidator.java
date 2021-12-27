package no.linska.webapp.validator.password;

import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Properties;

import static java.lang.String.format;


@Configuration
public class PasswordLengthConstraint implements ConstraintValidator<ValidPasswordLength, String> {


    Properties properties;

    PasswordValidator validator;


    @Override
    public void initialize(ValidPasswordLength arg0) {

       validator = new PasswordValidator(
               List.of(new LengthRule(8, 30))
        );
    }



    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        return false;
    }
}