"""
transformation_controller.py
============================
Controlador que coordena o processo de transformação:
    - Extrai dados do PDF.
    - Estrutura os dados com PDFData.
    - Aplica substituição de siglas.
    - Salva os dados em CSV e compacta em ZIP.
"""

import pandas as pd
import re
from src.services.pdf_service import PDFService
from src.models.pdf_data import PDFData
from src.views.csv_writer import CSVWriter
from src.exceptions.transformation_exceptions import TransformationError

class TransformationController:
    def __init__(self, pdf_path: str, csv_output_path: str, output_dir: str, zip_filename: str):
        """
        Inicializa o controlador de transformação.

        Args:
            pdf_path (str): Caminho do arquivo PDF.
            csv_output_path (str): Caminho do arquivo CSV de saída.
            output_dir (str): Diretório para os arquivos de saída.
            zip_filename (str): Nome do arquivo ZIP.
        """
        self.pdf_path = pdf_path
        self.csv_output_path = csv_output_path
        self.output_dir = output_dir
        self.zip_filename = zip_filename

    def substitute_abbreviations(self, text: str) -> str:
        """
        Substitui siglas por seus significados completos no texto.

        Args:
            text (str): Texto original.

        Returns:
            str: Texto com siglas substituídas.
        """
        substitutions = {
            r"\bOD\b": "Procedimento Odontológico",
            r"\bAMB\b": "Procedimento Ambulatorial",
            # Adicione outras substituições conforme necessário
        }
        for pattern, replacement in substitutions.items():
            text = re.sub(pattern, replacement, text)
        return text

    def transform(self):
        """
        Executa o fluxo de transformação: extrai, estrutura, transforma e salva os dados.

        Raises:
            TransformationError: Se ocorrer algum problema durante o processo.
        """
        try:
            # Extrai dados do PDF
            pdf_service = PDFService(self.pdf_path)
            raw_data = pdf_service.extract_data()
        except Exception as e:
            raise TransformationError(f"Erro na extração do PDF: {e}\n")

        # Cria o modelo de dados
        pdf_data = PDFData(raw_data.get("lines", []))
        data_dict = pdf_data.to_dict()

        # Converte para DataFrame para facilitar transformações
        df = pd.DataFrame(data_dict)

        # Aplica substituições de siglas (ex.: OD, AMB)
        if "lines" in df.columns:
            df["lines"] = df["lines"].apply(self.substitute_abbreviations)

        try:
            # Salva os dados em CSV e compacta em ZIP
            writer = CSVWriter(self.csv_output_path, self.output_dir, self.zip_filename)
            writer.write(df)
        except Exception as e:
            raise TransformationError(f"Erro na escrita do CSV/ZIP: {e}\n")
