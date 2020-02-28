/**
 * 
 */
package br.com.phsg.inter.challenge.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calculo implements Serializable {
	
	private static final long serialVersionUID = 8156932792223075584L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numero;
    private int multiplicador;
    private int digito;

}
