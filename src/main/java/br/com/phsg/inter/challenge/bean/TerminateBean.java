/**
 * 
 */
package br.com.phsg.inter.challenge.bean;

import javax.annotation.PreDestroy;

/**
 * @author pedro.gomes - 2020/02/28
 * 
 */
public class TerminateBean {

	@PreDestroy
    public void onDestroy() throws Exception {
        System.out.println("Spring Container is destroyed!");
    }
}
