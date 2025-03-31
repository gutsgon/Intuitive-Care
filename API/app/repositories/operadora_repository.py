import pandas as pd
from app.models.operadora_model import Operadora
from app.database.connection import db
from app.main import create_app
from datetime import datetime

def import_csv(file_path):
    try:
        df = pd.read_csv(
            file_path,
            encoding='utf-8-sig',  # Tente garantir a codificação UTF-8 com BOM
            delimiter=';',  # Usando tabulação como delimitador
            on_bad_lines='skip'  # Ignora linhas problemáticas
        )

        # Instância do Flask usando create_app
        app = create_app()

        # Certifique-se de usar o contexto do Flask
        with app.app_context():
            for _, row in df.iterrows():
                # Dentro do loop de inserção
                data_registro_ans = row['Data_Registro_ANS']
                if isinstance(data_registro_ans, str):
                    # Se for string, converte para formato DATE
                    data_registro_ans = datetime.strptime(data_registro_ans, '%Y-%m-%d').date()
                operadora = Operadora(
                    registro_ans=row['Registro_ANS'],
                    cnpj=row['CNPJ'],
                    razao_social=row['Razao_Social'],
                    nome_fantasia=row['Nome_Fantasia'],
                    modalidade=row['Modalidade'],
                    logradouro=row['Logradouro'],
                    numero=row['Numero'],
                    complemento=row['Complemento'],
                    bairro=row['Bairro'],
                    cidade=row['Cidade'],
                    uf=row['UF'],
                    cep=row['CEP'],
                    ddd=row['DDD'],
                    telefone=row['Telefone'],
                    fax=row['Fax'],
                    endereco_eletronico=row['Endereco_eletronico'],
                    representante=row['Representante'],
                    cargo_representante=row['Cargo_Representante'],
                    regiao_de_comercializacao=row['Regiao_de_Comercializacao'],
                    data_registro_ans=data_registro_ans
                )
                db.session.add(operadora)
                db.session.commit()
    except Exception as e:
        print(f"Erro ao ler o CSV: {e}")
