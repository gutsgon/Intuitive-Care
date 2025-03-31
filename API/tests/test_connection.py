from app.database.connection import LocalSession, CloudSession
from app.models.operadora_model import OperadoraModel

def test_connection():
    # Testando conexão com banco local
    local_db = LocalSession()
    local_count = local_db.query(OperadoraModel).count()
    local_db.close()

    # Testando conexão com banco na nuvem
    cloud_db = CloudSession()
    cloud_count = cloud_db.query(OperadoraModel).count()
    cloud_db.close()

    assert isinstance(local_count, int), "Erro: Contagem do banco local não retornou um número"
    assert isinstance(cloud_count, int), "Erro: Contagem do banco na nuvem não retornou um número"
    print(f"Banco Local: {local_count} registros")
    print(f"Banco Nuvem: {cloud_count} registros")
