from app.repositories.operadora_repository import import_csv
from app.models.operadora_model import Operadora
from flask import jsonify, request
from app.database.connection import db, cloud_engine
import os


def migrate_operadoras():
    try:
        # **Reset da estrutura do banco de nuvem:**
        # Atenção: Isso apagará todos os dados existentes!
        db.Model.metadata.drop_all(bind=cloud_engine)
        db.Model.metadata.create_all(bind=cloud_engine)

        # Obter o diretório base, que é a raiz do projeto
        base_dir = os.path.dirname(os.path.abspath(__file__))
        base_dir_1 = os.path.dirname(base_dir)
        filtred_dir = os.path.dirname(base_dir_1)

        # Caminho correto para o arquivo CSV
        file_path = os.path.join(filtred_dir, 'data', 'Relatorio_cadop.csv')

        import_csv(file_path)
        return jsonify({"message": "Dados migrados com sucesso!"}), 200
    except Exception as e:
        return jsonify({"message": f"Erro ao migrar os dados: {str(e)}"}), 500


def search_operadoras():
    query = request.args.get("query")

    if not query:
        return jsonify({"error": "Consulta não fornecida."}), 400

    operadoras = Operadora.query.filter(
        Operadora.razao_social.ilike(f"%{query}%") |
        Operadora.nome_fantasia.ilike(f"%{query}%") |
        Operadora.cnpj.ilike(f"%{query}%")
    ).all()

    result = [{"razao_social": op.razao_social, "nome_fantasia": op.nome_fantasia, "cnpj": op.cnpj} for op in
              operadoras]

    return jsonify(result), 200
