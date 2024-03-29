package org.springframework.samples.parchisoca.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**","/welcome").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/games/instructions/**").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/session/**").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/statistics/achievements/user","/boards/**").hasAnyAuthority("player")
				.antMatchers("/statistics/**").hasAnyAuthority("admin")
				.antMatchers("/players/create").anonymous()
				.antMatchers("/players/find","players/myProfile").hasAnyAuthority("player", "admin")
				.antMatchers("/players/**").hasAnyAuthority("admin","player")
				.antMatchers("/games/list").hasAnyAuthority("player", "admin")
				.antMatchers("/games/create").hasAnyAuthority("player", "admin")
				.antMatchers("/games/lobbys/**","/games/lobby/**").authenticated()
				.antMatchers("/games/admin/**").hasAnyAuthority("admin")
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/stats/**").hasAnyAuthority("player")
				.antMatchers("/notifications/**").hasAnyAuthority("player")
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/");
                // Configuración para que funcione la consola de administración
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(" select u.username, a.authority from users "
          + " u join authorities a on u.id = a.user_id "
          + "  where u.username =?")
	      .passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}

}


