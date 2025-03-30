"""
main.py
=======
Ponto de entrada da aplicação Data Transformation.

Responsabilidades:
    - Determinar o diretório raiz do projeto.
    - Definir os caminhos dos arquivos de entrada (PDF) e saída (CSV e ZIP).
    - Coordenar a execução da transformação de dados.
    - Lidar com erros básicos na execução.

Fluxo:
    1. Extrai as linhas de texto do PDF.
    2. Processa as linhas (substituição de siglas).
    3. Salva os dados processados em um arquivo CSV.
    4. Compacta o CSV gerado em um arquivo ZIP.
"""

import os
from pathlib import Path
from src.controllers.transformation_controller import TransformationController
from src.utils.get_project_root import get_project_root

def main():
    # Obter o diretório raiz do projeto
    base_dir = Path(get_project_root())

    #Filtrar caminho raiz
    if base_dir.name == "src":
        base_dir = base_dir.parent

    # Define o caminho completo do arquivo PDF (entrada)
    pdf_path = os.path.join(base_dir, "tests", "input", "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf")

    if not os.path.exists(pdf_path):
        print(f"Arquivo PDF não encontrado: {pdf_path}")
        return

    # Define o diretório de saída (para CSV e ZIP)
    output_dir = os.path.join(base_dir, "tests", "output")

    # Nomes dos arquivos de saída
    csv_filename = "dados_extraidos.csv"
    zip_filename = "Teste_Gustavo_Vinicius.zip"

    # Cria e executa o controlador de transformação
    controller = TransformationController(pdf_path, os.path.join(output_dir, csv_filename), output_dir, zip_filename)
    controller.transform()

if __name__ == "__main__":
    main()
