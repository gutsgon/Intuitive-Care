import os
import time
import psutil
import pytest
from src.services.pdf_service import PDFService


@pytest.fixture
def test_pdf_path():
    # Caminho relativo a partir da raiz do projeto
    return os.path.join("input", "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf")


def test_pdf_extraction(test_pdf_path):
    pdf_service = PDFService(test_pdf_path)
    data = pdf_service.extract_data()
    assert isinstance(data, dict)
    assert "lines" in data
    # Verifica que pelo menos uma linha foi extraída
    assert len(data["lines"]) > 0


def test_pdf_extraction_performance(test_pdf_path):
    pdf_service = PDFService(test_pdf_path)
    process = psutil.Process(os.getpid())

    start_time = time.perf_counter()
    data = pdf_service.extract_data()
    duration = time.perf_counter() - start_time

    cpu_usage = process.cpu_percent(interval=1)
    mem_usage = process.memory_info().rss / (1024 * 1024)  # MB

    print(f"Extração demorou: {duration:.2f} s, CPU: {cpu_usage}%, RAM: {mem_usage:.2f} MB")

    # Exemplo: a extração deve levar menos de 5 segundos
    assert duration < 5
    # Exemplo: uso de RAM menor que 200 MB (ajuste conforme necessário)
    assert mem_usage < 200
