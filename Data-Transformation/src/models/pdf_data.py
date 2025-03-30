"""
pdf_data.py
===========
Modelo para representar os dados extraídos do PDF.
"""

class PDFData:
    def __init__(self, lines: list):
        """
        Inicializa o modelo com as linhas extraídas.

        Args:
            lines (list): Lista de strings extraídas do PDF.
        """
        self.lines = lines

    def to_dict(self) -> dict:
        """
        Converte os dados extraídos em um dicionário para uso em DataFrame.

        Returns:
            dict: Dicionário com os dados.
        """
        return {"lines": self.lines}
