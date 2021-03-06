/**
 * 
 */
package br.com.phsg.inter.challenge.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 7742100101351765959L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotBlank
    @Size(min = 0, max = 400)
	@Column(length = 400)
    private String nome;
	
	@NotBlank
    @Size(min = 0, max = 850)
	@Column(length = 850)
    private String email;
	
    @JsonIgnore
    @Size(min = 0, max = 500)
    @Column(length = 500)
    private String chavePublica;
    
    @OneToMany
    @JoinColumn
    private List<Calculo> calculos;
}
