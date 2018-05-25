# Desafio-iMusica(Loja Virtual)

* Aplicação Web REST que realiza operações básicas de CRUD para um produto.
* Tendo como objetivo realizar a venda deste produto, mantendo a integridade do estoque quando uma determinada transação de venda não é concluída 100%

## Instruções

Após realizar o clone do projeto, importar no Eclipse: File -> Import -> Maven -> Existing Maven Projects , clicar em Next e selecionar o diretório onde o clone se encontra e clicar em Finish.

## Arquiteturas e Tecnologias usadas:

Banco de dados MySQL: 
    Faz-se necessário executar a instrução <CREATE DATABASE desafio;>

Servidor de aplicação Wildfly-10.0.0.Final:
    No arquivo standalone.xml, que se encontra em ..\standalone\configuration , na tag 'datasource' copiar e colar o seguinte trecho:
               
                <datasource jta="true" jndi-name="java:jboss/desafioDS" pool-name="desafio" enabled="true" use-java-context="true" use-ccm="true">
                    <connection-url>jdbc:mysql://localhost:3306/desafio</connection-url>
                    <driver>com.mysql</driver>
                    <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
                    <pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>100</max-pool-size>
                        <prefill>true</prefill>
                    </pool>
                    <security>
                        <user-name>root</user-name>
                        <password>root</password>
                    </security>
                    <statement>
                        <prepared-statement-cache-size>32</prepared-statement-cache-size>
                        <share-prepared-statements>true</share-prepared-statements>
                    </statement>
                </datasource>
            
    
   Representa o data source do banco de dados que foi configurado para o projeto.

   Ainda no arquivo standalone.xml, na tag 'drivers' , que fica logo abaixo de 'datasource' copiar o seguinte trecho:
    
                    <driver name="com.mysql" module="com.mysql">
                        <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                    </driver>
                    
   Representa do driver do mysql.

Após encerradas as configurações, basta rodar a aplicação que estará no seguinte endereço: http://localhost:8085/Desafio

## Frameworks utilizados
- J2EE
- JDK 1.8
- EJB 3.0
- JPA 2.0
- JAX-RS 2.0
- AngularJS para o frontend
