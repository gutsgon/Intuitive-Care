"""
csv_writer.py
=============
Responsável por salvar os dados em CSV e compactar o arquivo em ZIP.
"""

import os
import pandas as pd
import zipfile
from src.exceptions.csv_exceptions import CSVWriteError

class CSVWriter:
    def __init__(self, output_csv_path: str, output_dir: str, zip_filename: str):
        """
        Inicializa o CSVWriter.

        Args:
            output_csv_path (str): Caminho completo para o arquivo CSV de saída.
            output_dir (str): Diretório onde os arquivos serão salvos.
            zip_filename (str): Nome do arquivo ZIP a ser gerado.
        """
        self.output_csv_path = os.path.abspath(output_csv_path)
        self.output_dir = os.path.abspath(output_dir)
        self.zip_filename = zip_filename
        print(f"CSV será salvo em: {self.output_csv_path}\n")

    def write(self, data):
        """
        Salva os dados (DataFrame ou dicionário) em CSV e compacta-o em ZIP.

        Args:
            data: Dados a serem salvos (pandas DataFrame ou conversível para DataFrame).

        Raises:
            CSVWriteError: Se ocorrer algum erro na escrita do CSV ou na criação do ZIP.
        """
        # Converter dados para DataFrame, se necessário
        if not isinstance(data, pd.DataFrame):
            data = pd.DataFrame(data)

        try:
            os.makedirs(self.output_dir, exist_ok=True)
            data.to_csv(self.output_csv_path, index=False, encoding="utf-8", sep=";")
            print(f"CSV salvo com sucesso: {self.output_csv_path}\n")
        except Exception as e:
            raise CSVWriteError(f"Erro ao salvar o CSV: {e}\n")

        # Compacta o CSV em ZIP
        zip_path = os.path.join(self.output_dir, self.zip_filename)
        try:
            with zipfile.ZipFile(zip_path, "w", zipfile.ZIP_DEFLATED) as zipf:
                zipf.write(self.output_csv_path, arcname=os.path.basename(self.output_csv_path))
            print(f"Arquivo ZIP criado: {zip_path}\n")
        except Exception as e:
            raise CSVWriteError(f"Erro ao criar o arquivo ZIP: {e}\n")
