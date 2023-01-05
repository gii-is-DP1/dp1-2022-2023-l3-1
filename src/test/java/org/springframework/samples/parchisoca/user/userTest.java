package org.springframework.samples.parchisoca.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class userTest {
    @Autowired
    UserRepository ur;


    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void testValidationNull(){
        testConstraints();
    }

    private void testConstraints() {
        User u = new User();
        u.setPassword(null);

        assertThrows(ConstraintViolationException.class,() -> ur.save(u),
        "You are not constraining "+ "Password can not be null");
    }
    
}
