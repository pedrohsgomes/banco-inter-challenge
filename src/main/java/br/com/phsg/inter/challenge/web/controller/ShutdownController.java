/**
 * 
 */
package br.com.phsg.inter.challenge.web.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pedro.gomes - 2020/02/28
 * 
 */
@RestController
public class ShutdownController implements ApplicationContextAware {
     
    private ApplicationContext context;
     
    @PostMapping("/shutdown")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }
    
    @GetMapping("/shutdown")
    public void shutdownContextGet() {
        ((ConfigurableApplicationContext) context).close();
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
         
    }
}