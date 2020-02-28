/**
 * 
 */
package br.com.phsg.inter.challenge.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.phsg.inter.challenge.model.entity.Calculo;
import br.com.phsg.inter.challenge.model.entity.Usuario;
import br.com.phsg.inter.challenge.model.repository.UsuarioRepository;
import br.com.phsg.inter.challenge.security.CriptografiaAssimetrica;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario save(Usuario usuario) {
		usuario = usuarioRepository.save(usuario);
		return usuario;
	}

	@Transactional
	public List<Usuario> getAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}

	@Transactional
	public void delete(Long idUsuario) {
		usuarioRepository.deleteById(idUsuario);
	}

	@Transactional
	public Usuario get(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		return usuario;
	}

	@Transactional
	public Usuario criptografar(@RequestParam Long idUsuario, @RequestParam String chavePublica) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null || usuario.getId() == null) {
			return null;
		} else if (!Strings.isBlank(usuario.getChavePublica())) {
			return usuario;
		}
		
		usuario.setChavePublica(chavePublica);
		
		PrivateKey privatekey;
		try {
			privatekey = CriptografiaAssimetrica.genKeyPair().getPrivate();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível gerar chave privada!");
		}
		
		PublicKey publickey;
		try {
			publickey = CriptografiaAssimetrica.genPublicKey(chavePublica);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível gerar chave publica!");
		}
		
		CriptografiaAssimetrica cript;
		try {
			cript = new CriptografiaAssimetrica(publickey, privatekey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível criar criptografia!");
		}
		
		try {
			String criptpgrafado = cript.criptografar(usuario.getNome());
			usuario.setNome(criptpgrafado);
			
			criptpgrafado = cript.criptografar(usuario.getEmail());
			usuario.setEmail(criptpgrafado);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível criptpgrafar dados!");
		}
		
		usuario = usuarioRepository.save(usuario);
		return usuario;
	}

	@Transactional
	public List<Calculo> getCalculos(@RequestParam Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null || usuario.getId() == null) {
			return null;
		}
		
		return usuario.getCalculos();
	}

}
