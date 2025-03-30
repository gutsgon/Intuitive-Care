"""
pdf_service.py
==============
Lógica para abrir e extrair dados do arquivo PDF.
Utiliza PyMuPDF (fitz) para extrair todas as linhas de texto.
"""

import fitz
import os
from src.exceptions.pdf_exceptions import PDFNotFoundError, PDFExtractionError

class PDFService:
    def __init__(self, pdf_path: str):
        # Converte o caminho recebido em absoluto
        self.pdf_path = os.path.abspath(pdf_path)
        print(f"Extraindo dados do PDF: {self.pdf_path}\n")

    def extract_data(self) -> dict:
        """
        Extrai todas as linhas de texto do PDF, página por página.

        Returns:
            dict: Um dicionário com a chave 'lines' contendo a lista de linhas extraídas.

        Raises:
            PDFNotFoundError: Se o arquivo PDF não for encontrado.
            PDFExtractionError: Se ocorrer algum erro durante a extração.
        """
        if not os.path.isfile(self.pdf_path):
            raise PDFNotFoundError(f"Arquivo PDF não encontrado: {self.pdf_path}")

        lines = []
        try:
            # Abre o documento PDF usando fitz (PyMuPDF)
            doc = fitz.open(self.pdf_path)
            # Itera sobre cada página do PDF
            for page in doc:
                text = page.get_text("text")
                if text:
                    lines.extend(text.splitlines())
            doc.close()
        except Exception as e:
            raise PDFExtractionError(f"Erro ao extrair dados do PDF: {e}")

        return {"lines": lines}
