/**
 * 
 */
package br.com.phsg.inter.challenge.web.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.phsg.inter.challenge.model.entity.Calculo;
import br.com.phsg.inter.challenge.model.entity.Usuario;
import br.com.phsg.inter.challenge.service.CalculoService;
import br.com.phsg.inter.challenge.service.UsuarioService;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
@Consumes
@Produces(MediaType.APPLICATION_JSON)
public class ApiRequestController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CalculoService calculoService;

	@PostMapping("/usuarios")
	@ResponseBody
	public Usuario saveUsuario(@RequestBody Usuario usuario) {
		usuario = usuarioService.save(usuario);
		return usuario;
	}
	
	@GetMapping("/usuarios")
	@ResponseBody
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = usuarioService.getAll();
		return usuarios;
	}
	
	@DeleteMapping("/usuarios")
	@ResponseBody
	public void deleteUsuario(@RequestParam Long idUsuario) {
		usuarioService.delete(idUsuario);
	}
	
	@GetMapping("/usuarios/{id}")
	@ResponseBody
	public Usuario getUsuario(@PathVariable Long id) {
		Usuario usuario = usuarioService.get(id);
		return usuario;
	}
	
	@GetMapping("/usuarios/{id}/criptografar")
	@ResponseBody
	public Usuario criptografar(@PathVariable Long idUsuario, @RequestParam String chavePublica) {
		Usuario usuario = usuarioService.criptografar(idUsuario, chavePublica);
		return usuario;
	}
	
	@GetMapping("/usuarios/{id}/calculos")
	@ResponseBody
	public List<Calculo> getCalculosUsuario(@PathVariable Long idUsuario) {
		List<Calculo> calculos = usuarioService.getCalculos(idUsuario);
		return calculos;
	}

	@GetMapping("/digito_unico")
	@ResponseBody
	public int digitoUnico(@RequestParam String numero, @RequestParam int multiplicador,
			@RequestParam(required = false) Long idUsuario) {
		int digito = calculoService.digitoUnico(numero, multiplicador, idUsuario);
		return digito;
	}
}
