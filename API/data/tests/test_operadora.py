import pytest
from app.main import app

@pytest.fixture
def client():
    with app.test_client() as client:
        yield client

def test_migrate_operadoras(client):
    response = client.get("/migrate/operadoras")
    assert response.status_code == 200
    assert b"Dados migrados com sucesso!" in response.data

def test_search_operadoras(client):
    response = client.get("/search/operadoras?query=Operadora")
    assert response.status_code == 200
    assert b"razao_social" in response.data
