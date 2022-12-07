package org.springframework.samples.parchisoca.piece;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaPieceTest {

    @Autowired(required = false)
    OcaPieceRepository or;

    @Autowired(required = false)
    OcaPieceService os;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    // @Test
    // public void testNewOcaPiece(){
    //     testConstraints();
    // }

    // void testConstraints() {
    //     OcaPiece o = new OcaPiece();
    //     o.setId(5);
    //     o.setColour(Colour.RED);

    //     assertThrows(ConstraintViolationException.class,() -> or.save(o),
    //     "You are not constraining "+ "X position can not be < 7");  
        
        
    //     OcaPiece o2 = new OcaPiece();
    //     o.setId(7);
    //     o.setColour(Colour.RED);

    //     assertThrows(ConstraintViolationException.class,() -> or.save(o2),
    //     "You are not constraining "+ "Y position can not be < 7"); 

    // }


    
}
