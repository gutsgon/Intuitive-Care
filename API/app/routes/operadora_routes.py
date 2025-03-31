from flask import Blueprint
from app.controllers.operadora_controller import migrate_operadoras, search_operadoras

operadora_routes = Blueprint('operadora_routes', __name__)

# Registre as rotas aqui
operadora_routes.route('/migrate/operadoras', methods=['GET'])(migrate_operadoras)
operadora_routes.route('/search/operadoras', methods=['GET'])(search_operadoras)
