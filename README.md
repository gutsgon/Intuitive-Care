
# Data Transformation

Este projeto realiza a transformação de dados extraídos de arquivos PDF para o formato CSV, permitindo a análise e a organização dos dados de maneira estruturada.

## 📌 Funcionalidades

- Extrai dados de PDFs baixados e processados.
- Converte os dados extraídos em um formato CSV.
- Organiza os dados para fácil análise e consulta.

## 🛠 Tecnologias Utilizadas

- **[Python 3.x](https://www.python.org/)** (linguagem principal)
- **[PyCharm](https://www.jetbrains.com/pycharm/)** (IDE recomendada para desenvolvimento em Python)
- **[Docker](https://www.docker.com/)** (opcional, para containerizar a aplicação)
- **[Pandas](https://pandas.pydata.org/)** (para manipulação de dados)
- **[PyPDF2](https://pythonhosted.org/PyPDF2/)** (para extração de texto de PDFs)
- **[pytest](https://pytest.org/)** (para testes automatizados)

## 📋 Pré-requisitos

Antes de começar, verifique se você tem instalado:

- [Python 3.x](https://www.python.org/)
- **pip** (gerenciador de pacotes do Python)

Recomendação de ferramenta de desenvolvimento:

- **[PyCharm](https://www.jetbrains.com/pycharm/)** (IDE recomendada para desenvolvimento em Python).

## 🚀 Como Executar

### 1️⃣ Clonar o repositório

```sh
git clone https://github.com/gutsgon/Intuitive-Care.git
cd Intuitive-Care
```

### 2️⃣ Instalar as dependências

```sh
pip install -r requirements.txt
```

### 3️⃣ Executar o projeto

```sh
python src/main.py
```

### 4️⃣ Executar via Docker (opcional)

```sh
docker-compose up --build
```

## 🏗 Estrutura do Projeto (MVC)

```
📦 Data Transformation
 ┣ 📂 src
 ┃ ┣ 📂 controllers
 ┃ ┃ ┣ 📜 transformation_controller.py  # Coordena o fluxo de transformação
 ┃ ┣ 📂 exceptions
 ┃ ┃ ┣ 📜 pdf_excceptions.py
 ┃ ┃ ┣ 📜 csv_exceptions.py
 ┃ ┃ ┣ 📜 transformation_exceptions.py
 ┃ ┣ 📂 models
 ┃ ┃ ┣ 📜 pdf_data.py                  # Representa os dados extraídos do PDF
 ┃ ┣ 📂 services
 ┃ ┃ ┣ 📜 pdf_service.py               # Lógica para ler o PDF e extrair dados
 ┃ ┣ 📂 utils
 ┃ ┃ ┣ 📜 get_project_root.py
 ┃ ┣ 📂 views
 ┃ ┃ ┣ 📜 csv_writer.py                # Responsável por gerar o CSV a partir dos dados
 ┃ ┣ 📜 main.py                        # Ponto de entrada da aplicação
 ┣ 📂 tests
 ┃ ┣ 📜 test_pdf_service.py            # Testes para a extração de dados
 ┃ ┣ 📜 test_csv_writer.py             # Testes para geração do CSV
 ┃ ┣ 📜 test_transformation_controller.py # Testes para transformação dos arquivos
 ┃ ┣ 📜 test_performance.py # Testes de perfomance
 ┣ 📜 Dockerfile
 ┣ 📜 requirements.txt
 ┣ 📜 README.md
```

## 🌿 Estrutura de Branches

Este repositório conterá diferentes projetos, organizados por branches:

- `webscraping` → Web Scraper ANS
- `api` → API
- `database` → Database Query
- `data-transformation` → Data Transformation
- `documentation` → Documentation

Para alternar entre os projetos:

```sh
git checkout data-base
```

## 🔍 Testes Unitários

Os testes são fundamentais para garantir a integridade do código. Os principais arquivos de testes são:

- **`test_pdf_service.py`** →  Contém os testes unitários principais para validar a extração de dados do PDF.
- **`test_csv_writer.py`** →  Testes para garantir que a geração do CSV ocorra corretamente.
- **`test_transformation_controller.py`** →  Testes para garantir a transformação do PDF ocorra corretamente.
- **`test_performance.py`** →  Testes para avaliar performance total da aplicação.


Para rodar os testes, utilize:

```sh
pytest tests/ -v
```

## 📜 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
