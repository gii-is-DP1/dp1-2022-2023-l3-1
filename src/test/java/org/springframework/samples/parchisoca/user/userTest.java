package org.springframework.samples.parchisoca.user;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class userTest {
    @Autowired
    UserRepository ur;


    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void testValidationNull(){
        testConstraints();
    }

    void testConstraints() {
        User u = new User();
        u.setPassword(null);
        u.setUsername(null);

        assertFalse(validator.validate(u).isEmpty(), "It should not allow empty username");
        assertFalse(validator.validate(u).isEmpty(), "It should not allow empty username");

    }
    
}
