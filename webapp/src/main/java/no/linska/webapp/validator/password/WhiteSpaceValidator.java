package no.linska.webapp.validator.password;

import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Properties;

import static java.lang.String.format;


@Configuration
public class WhiteSpaceValidator implements ConstraintValidator<NoWhiteSpaceAllowed, String> {


    Properties properties;

    PasswordValidator validator;


    @Override
    public void initialize(NoWhiteSpaceAllowed arg0) {

       validator = new PasswordValidator(
               new WhitespaceRule()
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