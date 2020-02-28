package br.com.phsg.inter.challenge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phsg.inter.challenge.math.DigitoUnico;
import br.com.phsg.inter.challenge.model.entity.Calculo;
import br.com.phsg.inter.challenge.model.entity.Usuario;
import br.com.phsg.inter.challenge.web.controller.ApiRequestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterChallengeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class InterChallengeApplicationTests {

	@Autowired
	private ApiRequestController apiRequestController;

	@Autowired
	private TestGeneric<ApiRequestController> testGeneric;

	private ResultMatcher isNotFound = MockMvcResultMatchers.status().isNotFound();
	private ResultMatcher isOK = MockMvcResultMatchers.status().isOk();

	private ObjectMapper mapper = new ObjectMapper();
	private Usuario user;
	private String userData = "{\"nome\":\"Pedro\",\"email\":\"pedrohsgomes@gmail.com\"}";

	@BeforeEach
	public void init() throws Exception {
		testGeneric.init(apiRequestController);
		this.user = saveUsusario(null);
	}

	public Usuario saveUsusario(String json) throws Exception {
		String path = "/api/usuarios";
		String data = json == null || json.isEmpty() ? userData : json;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("data", data);

		String response = testGeneric.postTest(path, data, isOK);
		Usuario obj = mapper.readValue(response, Usuario.class);

		return obj;
	}

	/***
	 * Success test for save Usuario
	 * 
	 * @throws Exception
	 */
	@Test
	public void generateUsusario() throws Exception {
		Usuario obj = saveUsusario("");
		Assert.notNull(obj, ">> generateUsusario << Usuario not generated");
	}

	/***
	 * Success test for get Usuario
	 * 
	 * @throws Exception
	 */
	@Test
	public Usuario getUsusario() throws Exception {
		String path = "/api/usuarios/" + this.user.getId();
		String response = testGeneric.getTest(path, isOK);
		Usuario obj = mapper.readValue(response, Usuario.class);
		Assert.notNull(obj, ">> getUsusario << usuário não recuperado");
		Assert.isTrue(obj.getId() == this.user.getId(), ">> getUsusario << usuário não recuperado");
		
		return obj;
	}

	/***
	 * Success test for get Usuarios
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUsusarios() throws Exception {
		saveUsusario("{\"nome\":\"Teste\",\"email\":\"teste@gmail.com\"}");
		String path = "/api/usuarios";
		String response = testGeneric.getTest(path, isOK);
		List<Usuario> list = mapper.readValue(response, new TypeReference<List<Usuario>>() {
		});
		Assert.notNull(list, ">> getUsusarios << usuários não recuperado");
		Assert.isTrue(list.size() > 2, ">> getUsusarios << usuários não recuperado");
	}

	/***
	 * Success test for delete Usuario
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteUsusario() throws Exception {
		Usuario user = saveUsusario("{\"nome\":\"Teste2\",\"email\":\"teste2@gmail.com\"}");
		String path = "/api/usuarios";
		String response = testGeneric.deleteTest(path, "idUsuario", user.getId().toString(), isOK);
//		Assert.notNull(list, ">> getUsusarios << usuários não recuperado");
//		Assert.isTrue(list.size() > 2, ">> getUsusarios << usuários não recuperado");

		path = "/usuarios/" + this.user.getId();
		response = testGeneric.getTest(path, isOK);
		Usuario obj = mapper.readValue(response, Usuario.class);
		Assert.isNull(obj, ">> deleteUsusario << usuário deletado");
	}

	/***
	 * Success test for criptografar Usuario
	 * 
	 * @throws Exception
	 */
	@Test
	public void criptografarUsuario() throws Exception {
		String path = "/api/usuarios/" + this.user.getId() + "/criptografar";
		String response = testGeneric.getTest(path, "chavePublica", "" + this.user.getId(), isOK);
		Usuario obj = mapper.readValue(response, Usuario.class);
		Assert.notNull(obj, ">> criptografarUsuario << usuário não criptografado");
		Assert.isTrue(obj.getId() == this.user.getId(), ">> criptografarUsuario << usuário não criptografado");
		Assert.isTrue(!obj.getNome().equalsIgnoreCase(this.user.getNome()),
				">> criptografarUsuario << nome não criptografado");
		Assert.isTrue(!obj.getEmail().equalsIgnoreCase(this.user.getEmail()),
				">> criptografarUsuario << email não criptografado");
	}
	
	/***
	 * Success test for get Digito para Usuario
	 * 
	 * @throws Exception
	 */
	@Test
	public void getDigitoUsuario() throws Exception {
		String path = "/api/digito_unico";
		
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("numero", "9875");
		param.add("multiplicador", "4");
		param.add("idUsuario", this.user.getId().toString());	
		
		String response = testGeneric.getTest(path, param, isOK);
		Integer obj = mapper.readValue(response, Integer.class);
		Assert.notNull(obj, ">> getDigitoUsuario << usuários não recuperado");
		Assert.isTrue(obj == 8, ">> getDigito << dígito não compatível");
		
		Usuario usuario = getUsusario();
		Assert.isTrue(usuario.getId() == this.user.getId(), ">> getDigitoUsuario << usuários não recuperado");
		Assert.isTrue(usuario.getCalculos().size() > 0, ">> getDigitoUsuario << calculos de usuários não recuperado");
		Assert.isTrue(usuario.getCalculos().get(0).getDigito() == 8, ">> getDigitoUsuario << digito não compatível");
	}

	/***
	 * Success test for get Usuarios
	 * 
	 * @throws Exception
	 */
	@Test
	public void getCalculos() throws Exception {
		getDigitoUsuario();
		
		String path = "/api/usuarios/" + this.user.getId() + "/calculos";
		String response = testGeneric.getTest(path, isOK);
		List<Calculo> list = mapper.readValue(response, new TypeReference<List<Calculo>>() {});
		Assert.notNull(list, ">> getCalculos << usuários não recuperado");
		Assert.isTrue(list.size() > 0, ">> getCalculos << usuários não recuperado");
	}
	
	/***
	 * Success test for get Digito
	 * 
	 * @throws Exception
	 */
	@Test
	public void getDigito() throws Exception {
		String path = "/api/digito_unico";
		
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("numero", "9875");
		param.add("multiplicador", "4");	
		
		String response = testGeneric.getTest(path, param, isOK);
		Integer obj = mapper.readValue(response, Integer.class);
		Assert.notNull(obj, ">> getDigito << dígito não recuperado");
		Assert.isTrue(obj == 8, ">> getDigito << dígito não compatível");
	}

	@Test
	void digitoUnicoTest() {
		int numero = DigitoUnico.digitoUnico("9875", 4);
		Assert.isTrue(numero == 8, "Não é igual a 8!");
	}

}
