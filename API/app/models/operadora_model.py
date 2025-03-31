from app.database.connection import db


class Operadora(db.Model):
    __tablename__ = 'operadoras'

    id = db.Column(db.Integer, primary_key=True)
    registro_ans = db.Column(db.String(50), nullable=False)
    cnpj = db.Column(db.String(20), nullable=True)
    razao_social = db.Column(db.String(255), nullable=True)
    nome_fantasia = db.Column(db.String(255), nullable=True)
    modalidade = db.Column(db.String(50), nullable=True)
    logradouro = db.Column(db.String(255), nullable=True)
    numero = db.Column(db.String(20), nullable=True)
    complemento = db.Column(db.String(255), nullable=True)
    bairro = db.Column(db.String(255), nullable=True)
    cidade = db.Column(db.String(255), nullable=True)
    uf = db.Column(db.String(2), nullable=True)
    cep = db.Column(db.String(10), nullable=True)
    ddd = db.Column(db.String(5), nullable=True)
    telefone = db.Column(db.String(20), nullable=True)
    fax = db.Column(db.String(20), nullable=True)
    endereco_eletronico = db.Column(db.String(255), nullable=True)
    representante = db.Column(db.String(255), nullable=True)
    cargo_representante = db.Column(db.String(255), nullable=True)
    regiao_de_comercializacao = db.Column(db.String(255), nullable=True)
    data_registro_ans = db.Column(db.Date, nullable=True)
