package org.springframework.samples.parchisoca.badWords;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.parchisoca.badWord.BadWords;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BadWordsTest {

    @Test
    public void testBadWords(){
        BadWords bw = BadWords.COCK;
        assertThat(bw.getBadWord().equals("COCK"));
    }
}
