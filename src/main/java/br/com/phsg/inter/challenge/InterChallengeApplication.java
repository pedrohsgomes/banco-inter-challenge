package br.com.phsg.inter.challenge;

import javax.ws.rs.core.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.phsg.inter.challenge.bean.TerminateBean;

@SpringBootApplication
public class InterChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterChallengeApplication.class, args);
	}

	private static void closeApplication() {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run();
        System.out.println("Spring Boot application started");
        ctx.getBean(TerminateBean.class);
        ctx.close();
    }

    private static void exitApplication() {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run();

        int exitCode = SpringApplication.exit(ctx, () -> {
            // return the error code
            return 0;
        });

        System.out.println("Exit Spring Boot");

        System.exit(exitCode);
    }

    private static void writePID() {
        SpringApplicationBuilder app = new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE);
        app.build().addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
        app.run();
    }
}