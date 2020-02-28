# Banco Inter Challenge


### Desafio técnico para o banco Inter.



###### Executando
- 1) Para execução do projeto não precisa ter o maven instalado, já esta incluso no projeto um wrapper do mesmo.
- 2) Para instalação do maven: (http://maven.apache.org/) download: (http://maven.apache.org/download.cgi)
- 3) Ir para a pasta raiz do projeto e executar o seguinte comando:

###### Maven Instalado / Maven Wrapper
* mvn spring-boot:run ou ./mvnw spring-boot:run


###### Executar Testar
- 1) Para execução dos testes unitários/integração seguinte comando:

###### Maven Instalado / Maven Wrapper
* mvn test  ou ./mvnw test

###### Finalização da applicação

###### Via swagger
* http://localhost:8081/inter-challenge/swagger-ui

ou

###### Via URL
* http://localhost:8081/inter-challenge/shutdown

ou

###### Via post
* http://localhost:8081/inter-challenge/shutdown

ou

###### Via shell/cmd
* curl -X POST localhost:8081/inter-challenge/actuator/shutdown


###### Documentação api-docs/swagger
* http://localhost:8081/inter-challenge/api-docs
* http://localhost:8081/inter-challenge/api-docs.yaml
* http://localhost:8081/inter-challenge/swagger-ui

###### Url de criptografia
* Obs: parâmetro chavePublica tem que estar "encoded"
* http://localhost:8081/inter-challenge/api/usuarios/{id}/criptografar?&chavePublica=
* ex: http://localhost:8081/inter-challenge/api/usuarios/1/criptografar?&chavePublica=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt7PbmmgOHpHw0eUdq3mXLvKZnm%2F%2BaiR4l%2BDJQG%2FTNUhsuPwml71cFYK6VxIEjH6hjCN4ewznM80jJOtwAFMVRzFTsTDdKLB23%2FWnYutABhaXhEyGNdxX4pVNAAllkSYEGNh%2FmCl3B0XC3uwRUjOssrC8w%2F1396Lk83S0kW0W7tgnphDQKW9tS9AD96qWRszasY%2BxQjbrak6mkS7T1drrfrrRBRQynjDYszDKcjago0%2BYqDusjR70pQpUZwPImAWkX7nBue2NPOy%2BRoj8butJil86%2BzQPQ%2BwLq1k0iGhfnW%2F%2F1T4CmIKLT4UwFhg%2F%2FyzqLjiMunEzw6j0xENtdxxPrwIDAQAB


###### Configuração do banco H2
* Schema: jdbc:h2:mem:bancointer-challenge-db
* Usuário: sa
* Senha: password
* Url: http://localhost:8081/inter-challenge/h2-console



#### Agora é ser feliz :D 