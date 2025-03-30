
# Database 

Este projeto tem como objetivo executar um teste técnico de banco de dados utilizando PostgreSQL. O teste envolve a criação de tabelas, a importação de dados a partir de arquivos CSV (obtidos dos repositórios públicos da ANS) e a execução de queries analíticas para identificar as operadoras com maiores despesas na categoria de "EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MÉDICO HOSPITALAR".

> **Atenção:**  
> Os comandos de importação (usando `COPY` ou `\copy`) devem ser executados via CLI do PostgreSQL (psql), pois o comando `\copy` lê os arquivos CSV a partir do cliente e evita restrições de permissões no servidor.

---

## 📌 Funcionalidades

- **Criação de Tabelas:**  
  Cria as tabelas `operadoras` e `demonstracoes_contabeis` para armazenar os dados cadastrais e financeiros, respectivamente.

- **Importação dos Dados:**  
  Utiliza o comando `\copy` para importar os arquivos CSV para as tabelas.  
  - Dados cadastrais das operadoras (arquivo CSV do item 3.2).  
  - Demonstrações contábeis dos últimos dois anos (arquivos CSV do item 3.1).  
  > **Nota:** Utilize caminhos relativos ou absolutos conforme necessário, lembrando que os comandos devem ser executados no psql.

- **Queries Analíticas:**  
  Executa queries que:
  - Identificam as 10 operadoras com maiores despesas na categoria específica no último trimestre do ano passado.
  - Identificam as 10 operadoras com maiores despesas na mesma categoria no último ano (separadamente).

---

## 🛠 Tecnologias Utilizadas

- **[PostgreSQL](https://www.postgresql.org/download/) (versão 10 ou superior):** Banco de dados relacional utilizado para armazenar e manipular os dados.
- **[SQL](https://www.w3schools.com/sql/):** Linguagem utilizada para criação de tabelas, importação de dados e execução das queries.
- **[psql](https://www.postgresql.org/docs/current/app-psql.html) (CLI do PostgreSQL):** Interface de linha de comando para execução dos comandos SQL.

---

## 📋 Pré-Requisitos

Antes de executar os scripts, verifique se você possui:
- PostgreSQL instalado (versão 10 ou superior).
- Acesso ao CLI do PostgreSQL (`psql`) e que o `psql` está configurado no PATH do sistema.
- Os arquivos CSV com os dados das operadoras e das demonstrações contábeis disponíveis no diretório correto.

---

## 🚀 Como Executar

### 1️⃣ Criação das Tabelas

Execute o script `create_tables.sql` no CLI do PostgreSQL para criar as tabelas necessárias:

```sh
psql -U seu_usuario -d seu_banco -f create_tables.sql
```

### 2️⃣ Importação dos Dados

#### Importação dos Dados Cadastrais

Utilize o comando `\i ./sql/importar_operadoras.sql` no CLI do PostgreSQL para importar os dados para a tabela `operadoras`.

#### Importação dos Dados Financeiros

Utilize o comando `\i ./sql/importar_finance_data.sql` no CLI do PostgreSQL para importar os dados para a tabela `demonstracoes_contabeis`.

> **Observação:**  
> Todos os comandos `\copy` devem ser executados no CLI do PostgreSQL, pois eles leem os arquivos CSV do sistema cliente.

### 3️⃣ Executar Queries Analíticas

Após a importação dos dados, execute o comando abaixo para inicilar as queries identificar as operadoras com maiores despesas na categoria de interesse.

Utilize o comando `\i ./sql/analitics_queries.sql`

---

## 🏗 Estrutura do Projeto

```
📦 Database 
 ┣ 📂 sql
 ┃ ┣ 📜 create_tables.sql         # Script para criação das tabelas (operadoras e demonstracoes_contabeis)
 ┃ ┣ 📜 import_operadoras.sql     # Script com o comando \copy para importação dos dados das operadoras
 ┃ ┣ 📜 import_finance_data.sql   # Script para importação dos dados financeiros (tabela de staging e inserção)
 ┃ ┣ 📜 analitics_queries.sql    # Script com as queries analíticas (último trimestre, último ano, etc.)
 ┣ 📂 data
 ┃ ┣ 📜 Relatorio_cadop.csv       # Arquivo CSV com os dados cadastrais das operadoras
 ┃ ┣ 📜 1T2023.csv                # Arquivo CSV com dados financeiros do 1º trimestre de 2023
 ┃ ┣ 📜 2t2023.csv                # Arquivo CSV com dados financeiros do 2º trimestre de 2023
 ┃ ┣ 📜 3T2023.csv                # Arquivo CSV com dados financeiros do 3º trimestre de 2023
 ┃ ┣ 📜 4T2023.csv                # Arquivo CSV com dados financeiros do 4º trimestre de 2023
 ┃ ┣ 📜 1T2024.csv                # Arquivo CSV com dados financeiros do 1º trimestre de 2024
 ┃ ┣ 📜 2T2024.csv                # Arquivo CSV com dados financeiros do 2º trimestre de 2024
 ┃ ┣ 📜 3T2024.csv                # Arquivo CSV com dados financeiros do 3º trimestre de 2024
 ┃ ┣ 📜 4T2024.csv                # Arquivo CSV com dados financeiros do 4º trimestre de 2024
 ┣ 📜 README.md                   # Este arquivo
```
---

## 📜 Licença

Este projeto é licenciado sob a [MIT License](LICENSE).

---

## 🔍 Observações Finais

- **Execução via CLI:**  
  Os comandos `\copy` devem ser executados no psql (CLI do PostgreSQL) para garantir que os arquivos CSV sejam lidos do seu sistema de arquivos local.
