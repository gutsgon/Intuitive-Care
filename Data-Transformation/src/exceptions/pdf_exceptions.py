"""
pdf_exceptions.py
=================
Define exceções específicas para erros relacionados ao processamento de PDFs.
"""

class PDFNotFoundError(Exception):
    """Exceção lançada quando o arquivo PDF não é encontrado."""
    pass

class PDFExtractionError(Exception):
    """Exceção lançada quando ocorre um erro durante a extração dos dados do PDF."""
    pass
