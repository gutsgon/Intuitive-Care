# Web Scraper ANS

Este projeto realiza web scraping no site da **ANS** para baixar e compactar PDFs automaticamente.

## 📌 Funcionalidades

- Acessa o site da ANS e encontra os links para os PDFs.
- Faz o download dos arquivos automaticamente.
- Compacta todos os PDFs em um único arquivo ZIP.

## 🛠 Tecnologias Utilizadas

- **[Java 17+](https://jdk.java.net/)** (linguagem principal)
- **[Maven 3+](https://maven.apache.org/download.cgi)** (gerenciamento de dependências)
- **[Docker](https://www.docker.com/)** (opcional, para containerizar a aplicação)

## 📋 Pré-requisitos

Antes de começar, verifique se você tem instalado:

- [Java 17+](https://jdk.java.net/)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/) (caso queira rodar via container)

Recomendação de ferramenta de desenvolvimento:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/)** (IDE recomendada para desenvolvimento em Java com suporte nativo para Maven).

## 🚀 Como Executar

### 1️⃣ Clonar o repositório

```sh
git clone https://github.com/gutsgonIintuitive-Care.git
cd Intuitive-Care
```

### 2️⃣ Empacotando a aplicação

```sh
mvn clean package
```

### 3️⃣ Executar o projeto

```sh
java -jar target/webscraping-1.0.jar
```

### 4️⃣ Executar via Docker (opcional)

```sh
docker-compose up --build
```

## 🏗 Estrutura do Projeto

```
📦 Web Scraper ANS
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┣ 📂 com.intuitive.care.webscraping
 ┃ ┃ ┃ ┃ ┣ 📜 Main.java  # Classe principal que executa todas as funcionalidades
 ┃ ┃ ┃ ┃ ┣ 📂 compressor
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 Compressor.java
 ┃ ┃ ┃ ┃ ┣ 📂 downloader
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 FileDownloader.java
 ┃ ┃ ┃ ┃ ┣ 📂 exception
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 CompressException.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 DownloadException.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 ScraperException.java
 ┃ ┃ ┃ ┃ ┣ 📂 scraper
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 WebScraper.java
 ┃ ┣ 📂 test
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┣ 📂 com.intuitive.care.webscraping
 ┃ ┃ ┃ ┃ ┣ 📜 ApplicationTest.java  # Testes unitários principais
 ┃ ┃ ┃ ┃ ┣ 📂 downloads # Arquivos baixados nos testes
 ┣ 📜 pom.xml
 ┣ 📜 Dockerfile
 ┣ 📜 docker-compose.yml
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
git checkout data-transformation
```

## 🔍 Testes Unitários

Os testes são fundamentais para garantir a integridade do código. O principal arquivo de testes é:

- **`ApplicationTest.java`** → Contém os testes unitários principais para validar o comportamento da aplicação.

Para rodar os testes, utilize:

```sh
mvn test
```

## 📜 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Caso tenha dúvidas ou sugestões, fique à vontade para contribuir! 🚀

