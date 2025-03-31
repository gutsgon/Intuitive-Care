from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine
from dotenv import load_dotenv
import os

load_dotenv()

db = SQLAlchemy()

def get_database_url():
    return os.getenv("CLOUD_DATABASE_URL")  # Use a variável de ambiente para o URI do banco

def init_db(app):
    app.config['SQLALCHEMY_DATABASE_URI'] = get_database_url()
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
    db.init_app(app)

# Configurações adicionais, como conexão direta via engine:
cloud_engine = create_engine(get_database_url())
