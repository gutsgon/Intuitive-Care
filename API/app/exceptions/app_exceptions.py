class ApplicationException(Exception):
    """Exceção base para erros na aplicação."""
    pass

class LogicException(ApplicationException):
    """Exceção para erros de lógica na aplicação."""
    def __init__(self, message: str, errors: Exception = None):
        super().__init__(message)
        self.errors = errors

class QueryException(ApplicationException):
    """Exceção para erros nas consultas ao banco de dados."""
    def __init__(self, message: str, errors: Exception = None):
        super().__init__(message)
        self.errors = errors

class HttpRequestException(ApplicationException):
    """Exceção para erros em requisições HTTP."""
    def __init__(self, message: str, errors: Exception = None):
        super().__init__(message)
        self.errors = errors
