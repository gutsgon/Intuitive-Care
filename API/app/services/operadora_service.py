from sqlalchemy.orm import Session
from app.repositories.operadora_repository import import_csv
from app.exceptions.app_exceptions import LogicException

class OperadoraService:
    @staticmethod
    def buscar_operadoras(db: Session, query: str):
        try:
            return OperadoraRepository.buscar_operadoras(db, query)
        except Exception as e:
            raise LogicException("Erro na lógica de busca de operadoras", e)

    @staticmethod
    def migrar_operadoras(db: Session, query: str):
        try:
            return OperadoraRepository.buscar_operadoras(db, query)
        except Exception as e:
            raise LogicException("Erro na lógica de busca de operadoras", e)
