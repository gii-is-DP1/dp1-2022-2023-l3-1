package org.springframework.samples.parchisoca.user;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {

    @Autowired(required = false)
    UserService us;

    @Autowired(required = false)
    UserRepository ur;


    @Test
	@Transactional
	public void shouldCreateNewUserAndFindUser() {

        User u1 = new User();
        u1.setUsername("usuario10");
        u1.setPassword("1234");

        us.saveUser(u1);
		Optional<User> user = us.findUser("usuario10");
		assertThat(user.isPresent());
	}


}
