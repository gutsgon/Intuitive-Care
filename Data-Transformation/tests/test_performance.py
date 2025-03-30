import os
import time
import psutil
import pytest
from src.controllers.transformation_controller import TransformationController


@pytest.fixture
def performance_paths(tmp_path):
    # Configura caminhos temporários para testes de performance
    base_dir = tmp_path / "project"
    input_dir = base_dir / "tests" / "input"
    output_dir = base_dir / "tests" / "output"
    input_dir.mkdir(parents=True)
    output_dir.mkdir(parents=True)

    # Para teste de performance, um PDF com conteúdo razoável é necessário.
    # Neste exemplo, criaremos um arquivo dummy, mas em testes reais, utilize um PDF representativo.
    pdf_file = input_dir / "dummy.pdf"
    pdf_file.write_bytes(b"%PDF-1.4\n%Dummy PDF content\n1 0 obj\n<<>>\nendobj\n")

    return str(pdf_file), str(output_dir)


def test_transformation_performance(performance_paths):
    pdf_path, output_dir = performance_paths
    csv_output_path = os.path.join(output_dir, "test_output.csv")
    zip_filename = "Test_Performance.zip"

    controller = TransformationController(pdf_path, csv_output_path, output_dir, zip_filename)

    process = psutil.Process(os.getpid())
    start_time = time.perf_counter()
    controller.transform()
    duration = time.perf_counter() - start_time
    cpu_usage = process.cpu_percent(interval=1)
    mem_usage = process.memory_info().rss / (1024 * 1024)

    print(f"Transformação: {duration:.2f} s, CPU: {cpu_usage}%, RAM: {mem_usage:.2f} MB")

    # Exemplos de limites (ajuste conforme necessário)
    assert duration < 5, f"Transformação demorou muito: {duration:.2f} s"
    assert mem_usage < 200, f"Uso de memória alto: {mem_usage:.2f} MB"
