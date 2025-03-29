# Dockerfile
FROM python:3.9-slim

WORKDIR /app

# Copia os arquivos de requisitos e instala as dependências
COPY requirements.txt .
RUN pip install --upgrade pip && pip install -r requirements.txt

# Copia o código da aplicação
COPY src/ ./src/ tests/ ./tests/

# Define o PYTHONPATH para incluir o diretório src
ENV PYTHONPATH=/app/src:$PYTHONPATH

# Define o comando para executar a aplicação
CMD ["python", "src/main.py"]
