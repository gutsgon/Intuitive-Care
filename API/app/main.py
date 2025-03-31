from flask import Flask
from app.database.connection import init_db
app = Flask(__name__)

def create_app():
    from app.routes.operadora_routes import operadora_routes
    app.register_blueprint(operadora_routes)
    init_db(app)

    return app

if __name__ == "__main__":
    app = create_app()
    app.run(debug=True)
