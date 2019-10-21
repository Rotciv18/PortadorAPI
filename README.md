# PortadorAPI

## O que ele faz
A aplicação em questão é uma API que satisfaz o diagrama a seguir: </br>
![diagramaAPI](https://github.com/Rotciv18/PortadorAPI/blob/master/diagramaAPI.png)
</br>
## Funcionalidades
A princípio, um usuário pode fazer, a partir de uma requisição POST, o cadatro de um Portador enviando uma Proposta. Com pelo menos um portador cadastrado, a aplicação pode ser usada para requisições GET, POST, PUT, DEL das entidades Portador, Cartão e Lançamento (apenas GET para Fatura). Em algumas requisições será necessária autenticação do usuário.</br>
Ao tentar cadastrar um Portador, a proposta é enviada para uma fila e será eventualmente processada. No momento que um portador é cadastrado com sucesso, um e-mail será enviado confirmando o cadastro.</br>
O Portador será vinculado a um cartão no momento em que for cadastrado, mas há a possibilidade de ter mais de um cartão. <br>
Por fim, um portador pode realizar uma compra utilizando um de seus cartões, havendo uma lista de Lançamentos vinculadas a cada cartão.</br>
## Requisições
##### A seguir será listada todas as requisições para a API
##### <b>GET</b> localhost:8080/v1/portador
Retorna todos os portadores cadastrados
##### <b>GET</b> localhost:8080/v1/portador/{id}
Retorna um portador a partir de um id
##### <b>POST</b> localhost:8080/v1/portador/add
Cadastra um novo Portador (corpo deve conter campos de entidade Proposta)
##### <b>PUT</b> localhost:8080/v1//protected/portador/edit
Edita um portador já cadastrado. Id deve ser informado junto com o body
##### <b>DELETE</b> localhost:8080/v1/protected/portador/delete/{id}
Deleta um portador
##### GET localhost:8080/v1/cartao/{id}
Retorna um cartão a partir de seu id
##### POST localhost:8080/v1/cartao/add/{id}
Adiciona um cartão a partir do id de um portador
##### DELETE localhost:8080/v1/protected/cartao/desativar/{id}
Desativa um cartão a partir de seu id
##### PUT localhost:8080/v1/cartao/edit
Edita dados de um cartão. Id deve ser informado no body
##### PUT localhost:8080/v1/cartao/ativar/{id}
Ativa um cartão a partir de seu id
##### GET localhost:8080/v1/lancamento/{id}
Retorna detalhes de todos os lançamentos a partir do id de um cartão
##### POST localhost:8080/v1/lancamento/add/{id}
Adiciona um lançamento a partir de um id de cartão
##### localhost:8080/v1/lancamento/delete/{id}
Deleta um lançamento pelo seu id
##### PUT localhost:8080/v1/protected/lancamento/edit
Edita um lançamento. Id deve ser informado no body
##### GET localhost:8080/v1/fatura/{id}
Retorna a fatura de um cartão pelo id do cartão
## Métodos Utilizados
### Spring Boot
Spring Boot foi utilizado para a implementação de toda a API, com todas as requisições e conexão com o banco de dados.
### Entidades
Foram modeladas as seguintes entidades para satisfazer o problema: 
#### Para a API
Proposta, Portador, Cartao, Lancamento, Fatura. Adicionalmente, foi criada uma AbstractEntity para simplificar a implementação de todas as outras.
#### Para Autenticação
O modelo User também existirá no DB como uma tabela apenas para guardar dados de usuários a serem autenticados, para conceder acesso a algumas requisições.
#### Para E-Mail
Modelo simples para simplificar os códigos para envio de e-mail. Não utilizado no banco de dados.
### Banco de Dados
Como banco de dados para armazenar todos os dados passados pelas requisições, foi utilizado o PostgreSQL. Nele haverão 5 tabelas: Portador, Cartao, Lancamento, Fatura e User. Cada tabela terá todas as chaves equivalente aos atributos de suas respectivas entidades, com algumas exceções.</br> Configurações entre a aplicação e o postgre estão em .propoerties.
### Segurança
Para a segurança da aplicação, foi utilizado o AntMatchers do Spring Security. Todas as URL's com 'protected' só podem ser acessadas pelo admin. O usuário admin deve estar cadastrado na tabela 'user' do banco de dados, e sua senha estar criptografada pelo BCrypt.
![dbExample](https://github.com/Rotciv18/PortadorAPI/blob/master/dbExample.jpeg)
### AMQP
RabbitMQ foi utilizado para resolver o problema de protocolo de envio de mensagens para o cadastro do Portador.
### FrontEnd
Uma [aplicação simples FrontEnd](https://github.com/Rotciv18/PortadorFront) foi implementada utilizando Angular 6+, com componentes básicos para facilitar algumas requisições POST. 

## Dificuldades Encontradas
Quase tudo foi muito novo para mim então tive algumas dificuldades várias vezes enquanto aprendia as funcionalidades. Embora Spring Boot seja fácil e simples, cometi alguns erros bobos que me custaram um tempo para descobri-los (esquecimento de colocar anotações como @CrossOrigin, por exemplo).</br>
Embora hoje eu compreenda bem e ache até simples seu funcionamento, também tive problemas com o RabbitMQ a princípio.

## Possíveis Melhorias
#### Melhora no FrontEnd
Por ser minha primeira aplicação com Angular, ela não ficou muito sofisticada e com toda certeza poderia receber melhorias, inclusive para adicionar novas requisições, autenticação de usuário e algumas outras interações.
#### Melhoria no Código
Me incomodo muito com linhas de código desnecessárias, e existem algumas em PortadorAPI. Não tive tempo para revisar o código por ter mantido o foco em todas as funcionalidades enquanto aprendia.
#### Envio de E-mail
A aplicação irá iniciar uma sessão sempre que for enviar algum e-mail de confirmação, e isto retarda o processo. O ideal seria já manter uma sessão logada e assim realizar o envio de e-mails mais rapidamente.
#### Testes
É. :/
