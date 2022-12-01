package org.springframework.samples.parchisoca.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{

	@ManyToOne
	User user;

	@Size(min = 3, max = 50)
	String authority;

}
