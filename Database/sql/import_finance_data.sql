
-- ============================================================================
-- Script de Importação e Conversão dos Dados das Demonstrações Contábeis
-- 
-- Este script realiza as seguintes etapas:
--
-- 1. Cria uma tabela de staging (demonstracoes_staging) para armazenar temporariamente
--    os dados do CSV com todas as colunas como TEXT. Isso permite que os dados sejam
--    importados sem problemas de conversão, especialmente devido ao uso da vírgula como
--    separador decimal.
--
-- 2. Importa os dados do arquivo CSV para a tabela de staging.
--    O comando \copy utiliza um caminho relativo, assumindo que o arquivo CSV esteja
--    localizado no diretório corrente do psql.
--
-- 3. Insere os dados na tabela final (demonstracoes_contabeis), convertendo:
--    - A coluna 'data' de TEXT para DATE.
--    - As colunas 'vl_saldo_inicial' e 'vl_saldo_final' de TEXT para NUMERIC,
--      substituindo a vírgula (,) por ponto (.) para atender ao formato numérico do PostgreSQL.
--
-- 4. Após a importação, a tabela de staging é descartada 
-- 
-- 5. Observação: executar sql com CLI do PostgreSQL no diretório raiz do projeto "/Database"
--
-- ============================================================================
 
-- 1. Criação da tabela de staging
CREATE TABLE demonstracoes_staging (
    data TEXT,
    reg_ans TEXT,
    cd_conta_contabil TEXT,
    descricao TEXT,
    vl_saldo_inicial TEXT,
    vl_saldo_final TEXT
);

-- 2. Importação dos dados do CSV para a tabela de staging (OBS: apenas no CLI do PostgreSQL)
\copy demonstracoes_staging FROM './1T2023.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/2t2023.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/3T2023.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/4T2023.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/1T2024.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/2T2024.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/3T2024.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');
\copy demonstracoes_staging(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) FROM './data/4T2024.csv' WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');

-- 3. Inserção dos dados na tabela final, com conversões apropriadas
INSERT INTO demonstracoes_contabeis(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
SELECT 
  CAST(TRIM(data) AS DATE),
  TRIM(reg_ans),
  TRIM(cd_conta_contabil),
  TRIM(descricao),
  REPLACE(TRIM(vl_saldo_inicial), ',', '.')::NUMERIC,
  REPLACE(TRIM(vl_saldo_final), ',', '.')::NUMERIC
FROM demonstracoes_staging;

-- 4. Remover a tabela de staging por não ser mais necessária
DROP TABLE demonstracoes_staging;

