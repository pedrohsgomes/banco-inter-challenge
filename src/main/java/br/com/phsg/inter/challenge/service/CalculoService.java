/**
 * 
 */
package br.com.phsg.inter.challenge.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.phsg.inter.challenge.bean.DigitoCache;
import br.com.phsg.inter.challenge.math.DigitoUnico;
import br.com.phsg.inter.challenge.model.entity.Calculo;
import br.com.phsg.inter.challenge.model.entity.Usuario;
import br.com.phsg.inter.challenge.model.repository.ResultadoRepository;
import br.com.phsg.inter.challenge.model.repository.UsuarioRepository;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Service
public class CalculoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DigitoCache digitoCache;

	@Autowired
	private ResultadoRepository resultadoRepository;

	@Transactional
	public Integer digitoUnico(String numero, int multiplicador, Long idUsuario) {
		Integer resultado = null;
		
		if (digitoCache.contains(numero, multiplicador)) {
			resultado = digitoCache.getResultado(numero, multiplicador);
		} else {
			resultado = DigitoUnico.digitoUnico(numero, multiplicador);
			digitoCache.adicionar(numero, multiplicador, resultado);
		}
		
		if (idUsuario != null) {
			Usuario user = null;
			user = usuarioRepository.getOne(idUsuario);
			Calculo result = new Calculo(null, numero, multiplicador, resultado);
			result = resultadoRepository.save(result);
			
			if (user.getCalculos() == null) {
				user.setCalculos(new ArrayList<Calculo>());
			}
			user.getCalculos().add(result);
			user = usuarioRepository.save(user);
		}
		
		return resultado;		
	}
	
}
