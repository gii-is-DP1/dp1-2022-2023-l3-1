package org.springframework.samples.parchisoca.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Generated;
import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

	@NotNull
	@NotEmpty
	@Length(min = 4, max = 15)
	private String username;

	@NotNull
	@NotEmpty
	@Length(min = 4, max = 15)
	private String password;

	private boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
}
