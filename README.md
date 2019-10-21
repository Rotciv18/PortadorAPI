# PortadorAPI

## O que ele faz
A aplicação em questão é uma API que satisfaz o diagrama a seguir: </br>
![diagramaAPI](https://github.com/Rotciv18/PortadorAPI/blob/master/diagramaAPI.png)
</br>
## Funcionalidades
A princípio, um usuário pode fazer, a partir de uma requisição POST, o cadatro de um Portador enviando uma Proposta. Com pelo menos um portador cadastrado, a aplicação pode ser usada para requisições GET, POST, PUT, DEL das entidades Portador, Cartão e Lançamento (apenas GET para Fatura). Algumas requisições será necessária autenticação do usuário.</br>
Ao tentar cadastrar um Portador, a proposta é enviada para uma fila e será eventualmente processada. No momento que um portador é cadastrado com sucesso, um e-mail será enviado confirmando o cadastro.</br>
O Portador será vinculado a um cartão no momento em que for cadastrado, mas há a possibilidade de ter mais de um cartão. <br>
Por fim, um portador pode realizar uma compra utilizando um de seus cartões, havendo uma lista de Lançamentos vinculadas a cada cartão.</br>
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
Como banco de dados para armazenar todos os dados passados pelas requisições, foi utilizado o PostgreSQL. Nele haverão 5 tabelas: Portador, Cartao, Lancamento, Fatura e User. Cada tabela terá todas as chaves equivalente aos atributos de suas respectivas entidades, com algumas exceções.
