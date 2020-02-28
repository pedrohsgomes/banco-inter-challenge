/**
 * 
 */
package br.com.phsg.inter.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.phsg.inter.challenge.bean.TerminateBean;

/**
 * @author pedro.gomes - 2020/02/28
 * 
 */
@Configuration
@ComponentScan(basePackages = "br.com.phsg.inter.challenge")
public class ShutdownConfig {

	@Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}
