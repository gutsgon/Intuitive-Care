import os
import pandas as pd
import pytest
from src.views.csv_writer import CSVWriter
from src.exceptions.csv_exceptions import CSVWriteError


@pytest.fixture
def test_output_dir(tmp_path):
    # Usa um diretório temporário para testes
    return tmp_path / "output"


@pytest.fixture
def test_csv_path(test_output_dir):
    return str(test_output_dir / "test_output.csv")


@pytest.fixture
def test_data():
    # Dados de exemplo
    return {"Grupo": ["PROCEDIMENTOS GERAIS", " PROCEDIMENTOS CLÍNICOS AMBULATORIAIS E HOSPITALARES"], "Diretriz de Utilização ": [ 103,  86 ], "SubGrupo": ["CONSULTAS, VISITAS HOSPITALARES", "Y"]}


def test_csv_writer_creates_file(test_csv_path, test_output_dir, test_data):
    writer = CSVWriter(test_csv_path, str(test_output_dir), "test.zip")
    writer.write(test_data)

    assert os.path.exists(test_csv_path), "Arquivo CSV não foi criado."
    # Verifica se o arquivo ZIP também foi criado
    zip_path = os.path.join(str(test_output_dir), "test.zip")
    assert os.path.exists(zip_path), "Arquivo ZIP não foi criado."


def test_csv_writer_content(test_csv_path, test_output_dir, test_data):
    writer = CSVWriter(test_csv_path, str(test_output_dir), "csv_test.zip")
    writer.write(test_data)

    df = pd.read_csv(test_csv_path, sep=";")
    # Verifica se as colunas estão corretas
    expected_columns = list(test_data.keys())
    assert list(df.columns) == expected_columns
    # Verifica se os dados não estão vazios
    assert not df.empty
