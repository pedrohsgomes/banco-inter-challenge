/**
 * 
 */
package br.com.phsg.inter.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phsg.inter.challenge.model.entity.Calculo;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Repository
public interface ResultadoRepository extends JpaRepository<Calculo, Long> {

}
