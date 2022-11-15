package org.springframework.samples.parchisoca.board;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Service;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OcaBoardServiceTest {
    @Autowired
    OcaBoardService os;

    @Autowired
    OcaPieceService ocaPieceService;

    @Test
    void shouldFindOcaBoardById(){
       OcaBoard o =  os.findById(1);
       assertThat(o.getHeight()==800);
    }

}
