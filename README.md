
# API

Este projeto tem como objetivo buscar dados de operadoras de planos de saúde de um CSV para um banco de dados em nuvem, utilizando uma API construída com Flask, SQLAlchemy e Python. Além disso, a aplicação permite a importação dos dados a partir de um arquivo CSV e disponibiliza uma rota para realizar buscas textuais nos registros.

---

## 📌 Funcionalidades

- **Migração de Dados**:  
  - Importa os dados cadastrais das operadoras de um arquivo CSV localizado na pasta `data` e insere esses registros no banco de dados em nuvem.
  
- **Consulta de Dados**:  
  - Disponibiliza uma rota para buscar registros de operadoras com base em critérios textuais, filtrando por campos como razão social, nome fantasia e CNPJ.

- **Testes Unitários e de Performance**:  
  - Possui testes para validar a funcionalidade dos endpoints e medir a performance da aplicação.

- **Infraestrutura em Docker**:  
  - O projeto inclui um `Dockerfile` e um `docker-compose.yml` para facilitar o deploy da API e do banco de dados PostgreSQL.

---

## 🛠 Tecnologias Utilizadas

- **[Python 3.12](https://www.python.org/downloads/)** – Linguagem principal.
- **[Flask](https://flask.palletsprojects.com/)** – Framework web para construir a API.
- **[Flask-SQLAlchemy](https://flask-sqlalchemy.palletsprojects.com/)** – Extensão para integração com SQLAlchemy.
- **[SQLAlchemy](https://www.sqlalchemy.org/)** – ORM para interação com o banco de dados.
- **[Pandas](https://pandas.pydata.org/)** – Leitura e manipulação de dados CSV.
- **[python-dotenv](https://pypi.org/project/python-dotenv/)** – Gerenciamento de variáveis de ambiente.
- **[PostgreSQL](https://www.postgresql.org/)** – Banco de dados.
- **[Docker](https://www.docker.com/)** – Containerização da aplicação.
- **[Pytest](https://docs.pytest.org/)** – Framework para testes unitários.

---

## 📋 Pré-requisitos

- [Python 3.12](https://www.python.org/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Docker](https://www.docker.com/) (opcional, para containerização)
- [Git](https://git-scm.com/)

---

## 🚀 Como Executar

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio/API
```

### 2️⃣ Configurar Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto (não versionado no Git) com o seguinte conteúdo, ajustando conforme suas credenciais:

```dotenv
# Configuração do Banco de Dados Local
LOCAL_DATABASE_URL=postgresql://postgres:123@localhost:5432/teste_gustavo_vinicius

# Configuração do Banco de Dados em Nuvem
CLOUD_DATABASE_URL=postgresql://neondb_owner:senha@host:5432/neondb?sslmode=require

# Configuração da Aplicação
APP_ENV=development
SECRET_KEY=sua_secret_key_muito_segura
DEBUG=False
```

### 3️⃣ Instalar as Dependências

Utilize o comando abaixo para instalar as dependências:

```bash
pip install -r requirements.txt
```

### 4️⃣ Executar a API

Para rodar a API localmente, execute:

```bash
python -m app.main
```

A aplicação estará disponível em `http://127.0.0.1:5000`.

### 5️⃣ Executar via Docker (opcional)

Para construir e rodar a aplicação em um container, utilize:

```bash
docker-compose up --build
```

---

## 🏗 Estrutura do Projeto

```
📦 API
 ┣ 📂 app
 ┃ ┣ 📂 controllers           # Lógica de rotas e chamadas aos serviços
 ┃ ┣ 📂 database              # Conexão e inicialização do banco de dados
 ┃ ┃ ┣ 📜 connection.py
 ┃ ┣ 📂 exceptions            # Exceções personalizadas da aplicação
 ┃ ┃ ┣ 📜 app_exceptions.py
 ┃ ┣ 📂 models                # Modelos ORM (SQLAlchemy)
 ┃ ┃ ┣ 📜 operadora_model.py
 ┃ ┣ 📂 repositories          # Camada de acesso a dados (importação CSV, etc.)
 ┃ ┃ ┣ 📜 operadora_repository.py
 ┃ ┣ 📂 schemas               # Esquemas de resposta (para validação e serialização, se necessário)
 ┃ ┃ ┣ 📜 operadora_schema.py
 ┃ ┣ 📂 services              # Regras de negócio e lógica de aplicação
 ┃ ┃ ┣ 📜 operadora_service.py
 ┃ ┣ 📂 routes                # Endpoints da API
 ┃ ┃ ┣ 📜 operadora_routes.py
 ┃ ┣ 📜 main.py                # Arquivo principal da API
 ┣ 📂 tests
 ┃ ┣ 📜 test_operadora.py     # Testes unitários e de performance
 ┣ 📜 Dockerfile               # Dockerfile para construir a imagem da API
 ┣ 📜 docker-compose.yml       # Define serviços da API e PostgreSQL
 ┣ 📜 requirements.txt         # Dependências Python
 ┣ 📜 .env                     # Variáveis de ambiente (não versionadas)
 ┣ 📜 README.md                # Documentação do projeto
```

---

## 🔍 Testes Unitários

Os testes unitários e de performance estão localizados em `tests/test_operadora.py`.

Para rodar os testes, use:

```bash
pytest
```

---

## 🔒 Variáveis Sensíveis e Secrets

O arquivo `.env` é utilizado para armazenar informações sensíveis, como URLs de conexão e a `SECRET_KEY`. Esse arquivo não é versionado, garantindo que as informações sensíveis não sejam expostas no GitHub. Ao clonar o repositório, o usuário deverá criar seu próprio arquivo `.env` com as variáveis necessárias.

---

## 📜 Notas Adicionais

- **Execução das Queries SQL:**  
  Para executar scripts SQL que realizam importações (usando o comando `\copy`), use o CLI do PostgreSQL (`psql`) ou ferramentas que suportem esse comando.  
  Exemplo:
  ```sql
  \copy operadoras FROM './data/Relatorio_cadop.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
  ```

- **Docker:**  
  O `docker-compose.yml` inclui os serviços necessários, como a API e o PostgreSQL. Certifique-se de configurar as variáveis de ambiente no Docker para que a aplicação se conecte corretamente ao banco.

---

## 🚀 Conclusão

Este projeto integra uma API Flask com SQLAlchemy para migrar e consultar dados de operadoras a partir de um CSV e armazená-los em um banco de dados em nuvem. Siga as instruções acima para configurar, rodar e testar a aplicação.
