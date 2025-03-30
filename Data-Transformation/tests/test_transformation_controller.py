import os
import pytest
from src.controllers.transformation_controller import TransformationController
from src.exceptions.transformation_exceptions import TransformationError


@pytest.fixture
def test_pdf_file(tmp_path):
    """
    Cria um arquivo PDF dummy mínimo válido para os testes.
    Retorna o caminho absoluto para o arquivo criado.
    """
    pdf_content = b"""%PDF-1.4
1 0 obj
<< /Type /Catalog /Pages 2 0 R >>
endobj
2 0 obj
<< /Type /Pages /Kids [3 0 R] /Count 1 >>
endobj
3 0 obj
<< /Type /Page /Parent 2 0 R /MediaBox [0 0 300 144] /Contents 4 0 R >>
endobj
4 0 obj
<< /Length 55 >>
stream
BT
/F1 24 Tf
100 100 Td
(Hello, world!) Tj
ET
endstream
endobj
xref
0 5
0000000000 65535 f 
0000000010 00000 n 
0000000053 00000 n 
0000000100 00000 n 
0000000178 00000 n 
trailer
<< /Size 5 /Root 1 0 R >>
startxref
249
%%EOF
"""
    pdf_file = tmp_path / "test.pdf"
    pdf_file.write_bytes(pdf_content)
    return str(pdf_file)


@pytest.fixture
def test_output_dir(tmp_path):
    """
    Cria um diretório temporário para os arquivos de saída.
    """
    output_dir = tmp_path / "output"
    output_dir.mkdir()
    return str(output_dir)


@pytest.fixture
def test_paths(test_pdf_file, test_output_dir):
    """
    Retorna os caminhos de entrada e saída para os testes.
    """
    pdf_path = test_pdf_file
    output_dir = test_output_dir
    csv_output_path = os.path.join(output_dir, "test_output.csv")
    zip_filename = "Test_PDF.zip"
    return pdf_path, output_dir, csv_output_path, zip_filename


def test_transformation_controller(test_paths):
    """
    Testa a transformação completa garantindo que:
    - O PDF seja lido corretamente.
    - O CSV e ZIP sejam gerados corretamente.
    - Não ocorram exceções inesperadas.
    """
    pdf_path, output_dir, csv_output_path, zip_filename = test_paths

    controller = TransformationController(pdf_path, csv_output_path, output_dir, zip_filename)

    try:
        controller.transform()
        # Verifica se os arquivos foram criados
        assert os.path.exists(csv_output_path), "CSV não foi gerado."
        zip_path = os.path.join(output_dir, zip_filename)
        assert os.path.exists(zip_path), "ZIP não foi gerado."
    except TransformationError as e:
        pytest.fail(f"Erro na transformação: {e}")
