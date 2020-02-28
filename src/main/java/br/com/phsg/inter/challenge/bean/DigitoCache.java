/**
 * 
 */
package br.com.phsg.inter.challenge.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class DigitoCache {

	Map<String, Map<Integer, Integer>> cacheDigits = new HashMap<String, Map<Integer, Integer>>();

	public void adicionar(String numero, int multiplicador, int resultado) {
		if (contains(numero, multiplicador)) {
			return;
		} else if (cacheDigits.size() == 10) {
			cacheDigits.remove(cacheDigits.keySet().toArray()[0]);
		}

		if (cacheDigits.containsKey(numero) && !contains(numero, multiplicador)) {
			cacheDigits.get(numero).put(multiplicador, resultado);
		} else {
			Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
			mapa.put(multiplicador, resultado);
			cacheDigits.put(numero, mapa);
		}
	}

	public boolean contains(String numero, int multiplicador) {
		return cacheDigits.containsKey(numero) && cacheDigits.get(numero).containsKey(multiplicador);
	}

	public Integer getResultado(String numero, int multiplicador) {
		Integer resultado = null;
		if (contains(numero, multiplicador)) {
			resultado = cacheDigits.get(numero).get(multiplicador);
		}
		return resultado;
	}
}
