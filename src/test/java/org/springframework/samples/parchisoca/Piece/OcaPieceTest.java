package org.springframework.samples.parchisoca.piece;

import static org.junit.jupiter.api.Assertions.assertThrows;


import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceRepository;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaPieceTest {

    @Autowired(required = false)
    OcaPieceRepository or;

    @Autowired(required = false)
    OcaPieceService os;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void testNewOcaPiece(){
        testConstraints();
    }

    void testConstraints() {
        OcaPiece o = new OcaPiece();
        o.setId(5);
        o.setXPosition(100);
        o.setYPosition(6);
        o.setColour(Colour.RED);

        assertThrows(ConstraintViolationException.class,() -> or.save(o),
        "You are not constraining "+ "X position can not be < 7");  
        
        
        OcaPiece o2 = new OcaPiece();
        o.setId(7);
        o.setXPosition(6);
        o.setYPosition(100);
        o.setColour(Colour.RED);

        assertThrows(ConstraintViolationException.class,() -> or.save(o2),
        "You are not constraining "+ "Y position can not be < 7"); 

    }


    
}
