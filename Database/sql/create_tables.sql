-- ============================================================================
-- SCRIPT DE CRIAÇÃO DA TABELA "operadoras"
--
-- Descrição:
--   - Este script cria a tabela "operadoras" no banco de dados, responsável por armazenar
--     os dados cadastrais das operadoras de planos de saúde regulamentadas pela ANS.
--   - Os dados incluem informações de identificação, localização, contato e representantes.
--
-- Observações:
--   - A tabela utiliza "registro_ans" como chave primária, garantindo unicidade para cada operadora.
--   - Todos os campos foram definidos como VARCHAR para maior flexibilidade, exceto "data_registro_ans", 
--     que armazena datas no formato DATE.
--   - Os tamanhos dos campos foram definidos com base na estrutura esperada dos dados.
--
-- Estrutura da Tabela:
--   - registro_ans          → Identificador único da operadora (Registro ANS) (PK)
--   - cnpj                 → Número do Cadastro Nacional da Pessoa Jurídica (CNPJ)
--   - razao_social         → Nome oficial da operadora
--   - nome_fantasia        → Nome fantasia da operadora
--   - modalidade          → Tipo da operadora (ex: Administradora de Benefícios, Autogestão)
--   - logradouro          → Nome da rua/avenida
--   - numero              → Número do endereço
--   - complemento         → Informações adicionais do endereço
--   - bairro              → Bairro da operadora
--   - cidade              → Cidade onde está localizada
--   - uf                 → Unidade Federativa (sigla do estado)
--   - cep                → Código de Endereçamento Postal (CEP)
--   - ddd                → Código de área telefônica (DDD)
--   - telefone           → Número de telefone para contato
--   - fax                → Número do fax (se aplicável)
--   - endereco_eletronico → Endereço eletrônico (e-mail)
--   - representante       → Nome do representante legal
--   - cargo_representante → Cargo do representante dentro da operadora
--   - regiao_comercializacao → Área geográfica de atuação da operadora
--   - data_registro_ans   → Data de registro da operadora na ANS
--
-- Requisitos:
--   - Deve ser executado em um banco de dados PostgreSQL.
--   - A tabela deve estar em um esquema apropriado antes da execução.
--   - Certifique-se de que não existe outra tabela com o mesmo nome para evitar conflitos.
--
-- ============================================================================

CREATE TABLE operadoras (
    registro_ans VARCHAR(50) PRIMARY KEY,  -- Identificador único da operadora (Registro ANS)
    cnpj VARCHAR(20),                      -- CNPJ da operadora
    razao_social VARCHAR(255),             -- Razão Social
    nome_fantasia VARCHAR(255),            -- Nome Fantasia
    modalidade VARCHAR(50),                -- Modalidade da operadora
    logradouro VARCHAR(255),               -- Logradouro (rua, avenida, etc.)
    numero VARCHAR(20),                    -- Número do endereço
    complemento VARCHAR(255),               -- Informações adicionais do endereço
    bairro VARCHAR(255),                    -- Bairro
    cidade VARCHAR(255),                    -- Cidade
    uf VARCHAR(2),                          -- Unidade Federativa (UF)
    cep VARCHAR(10),                        -- Código de Endereçamento Postal (CEP)
    ddd VARCHAR(5),                         -- Código de área telefônica (DDD)
    telefone VARCHAR(20),                   -- Número de telefone
    fax VARCHAR(20),                        -- Número do fax (se aplicável)
    endereco_eletronico VARCHAR(255),       -- Endereço eletrônico (e-mail)
    representante VARCHAR(255),              -- Nome do representante legal
    cargo_representante VARCHAR(255),       -- Cargo do representante
    regiao_de_comercializacao VARCHAR(255), -- Área geográfica de atuação
    data_registro_ans DATE                  -- Data de registro da operadora na ANS
);






-- ============================================
-- 📄 CRIAÇÃO DA TABELA "demonstracoes_contabeis"
-- ============================================
-- Este script cria a tabela "demonstracoes_contabeis" para armazenar
-- dados financeiros das operadoras de planos de saúde, abrangendo
-- os últimos dois anos. A estrutura permite rastrear movimentações
-- contábeis ao longo do tempo.

-- ============================================
-- 🛠 CRIAÇÃO DA TABELA
-- ============================================
CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,              -- Identificador único da demonstração contábil (autoincrementado)
    data DATE,                          -- Data correspondente à demonstração contábil registrada
    reg_ans VARCHAR(50),                -- Registro da operadora junto à ANS (chave de referência)
    cd_conta_contabil VARCHAR(50),      -- Código da conta contábil, categorizando a movimentação financeira
    descricao VARCHAR(255),             -- Descrição detalhada da conta contábil (ex: categoria de despesa/receita)
    vl_saldo_inicial NUMERIC(20,2),     -- Valor do saldo inicial no período contabilístico
    vl_saldo_final NUMERIC(20,2)        -- Valor do saldo final após movimentações financeiras
);

-- ============================================
-- 🔍 DETALHES DOS CAMPOS
-- ============================================
-- id: Identificador único da entrada na tabela, gerado automaticamente.
-- data: Indica a data da demonstração contábil.
-- reg_ans: Identificador da operadora junto à ANS, possibilitando associação com a tabela "operadoras".
-- cd_conta_contabil: Código categorizador das contas contábeis.
-- descricao: Contém a descrição detalhada da movimentação financeira.
-- vl_saldo_inicial: Representa o saldo inicial registrado antes das movimentações.
-- vl_saldo_final: Representa o saldo final atualizado após as movimentações.

-- ============================================
-- 🚀 OBSERVAÇÕES
-- ============================================
-- - A tabela pode ser utilizada para análises financeiras detalhadas.
-- - Os valores de saldo permitem calcular variações ao longo do tempo.
-- - Possíveis expansões incluem novas métricas financeiras e índices de variação.

