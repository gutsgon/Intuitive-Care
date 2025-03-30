"""
get_project_root.py
===================
Função utilitária para retornar o diretório raiz do projeto.
"""

import os

def get_project_root() -> str:
    """
    Retorna o diretório raiz do projeto, assumindo que este arquivo está em um subdiretório.

    Returns:
        str: Caminho absoluto para a raiz do projeto.
    """
    # Supondo que este arquivo está em src/utils/, suba dois níveis
    return os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
