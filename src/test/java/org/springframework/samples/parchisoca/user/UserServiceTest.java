package org.springframework.samples.parchisoca.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {

    @Autowired(required = false)
    UserService us;

    @Autowired(required = false)
    UserRepository ur;

    @Autowired(required = false)
    AuthoritiesService authSer;

    @Test
	@Transactional
	public void shouldFindUserById() {

        User u1 = new User();
        u1.setUsername("usuarioTest");
        u1.setPassword("1234");
        u1.setId(20);
        u1.setEnabled(true);
        us.saveUser(u1);

		Optional<User> user = us.findUser(20);
		assertThat(user.isPresent());
	}

    @Test
    @Transactional
    public void shouldFindPasswordAndIsEnabled() {
        User u1 = new User();
        u1.setUsername("usuarioTest");
        u1.setPassword("1234");
        u1.setId(20);
        u1.setEnabled(true);
        us.saveUser(u1);

        assertThat(u1.getPassword().equals("1234"));
        assertTrue(u1.isEnabled());
    }

    @Test
	@Transactional
	public void shouldFindUserByUsername() {
        User u1 = new User();
        u1.setUsername("usuarioTest");
        u1.setPassword("1234");
        u1.setId(20);
        u1.setEnabled(true);
        us.saveUser(u1);

		Optional<User> user = us.findUserByUsername("usuarioTest");
		assertThat(user.isPresent());
	}

    @Test
    @Transactional
    public void shouldRetrieveAuthorities() {

        User u1 = new User();
        u1.setUsername("usuarioTest");
        u1.setPassword("1234");
        u1.setId(20);
        u1.setEnabled(true);
        us.saveUser(u1);

        Authorities auth = new Authorities();
        Set<Authorities> authorities = new HashSet<>();
        auth.setAuthority("player");
        authSer.saveAuthorities(auth);
        authorities.add(auth);

        u1.setAuthorities(authorities);
        us.saveUser(u1);
        
        assertThat(u1.getAuthorities().equals(authorities));
    }

    @Test
    public void shouldDontSaveAuthorities(){
        Integer i = null;
        try{
            authSer.saveAuthorities(i, "player", "userTest");
        }catch(DataAccessException a){
            System.out.println("User with id: '"+ i + "' not found!");
        }
    }


}
