/**
 * 
 */
package br.com.phsg.inter.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phsg.inter.challenge.model.entity.Usuario;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
