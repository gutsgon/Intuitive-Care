-- ============================================================================
-- SCRIPT DE IMPORTAÇÃO DOS DADOS CADASTRAIS DAS OPERADORAS
--
-- Descrição:
--   Este script importa os dados cadastrais das operadoras para a tabela 'operadoras'
--   a partir de um arquivo CSV.
--
-- Observações:
--   - O caminho do arquivo deve ser ajustado conforme o local onde ele está armazenado.
--   - Este script deve ser executado no CLI do PostgreSQL, pois o comando \copy 
--     é específico para o cliente psql e não funciona em ferramentas gráficas como PgAdmin.
--
-- Parâmetros do comando:
--   - FORMAT csv       → Especifica que o arquivo de entrada está no formato CSV.
--   - HEADER true      → Indica que a primeira linha do arquivo contém os nomes das colunas.
--   - DELIMITER ';'    → Define o ponto e vírgula (;) como delimitador de colunas.
--   - ENCODING 'UTF8'  → Garante que caracteres especiais sejam interpretados corretamente.
--
-- Uso:
--   - Execute este script diretamente no terminal do PostgreSQL (psql).
--   - Caso haja erros de importação, verifique:
--     1. O caminho do arquivo e sua permissão de leitura.
--     2. Se os dados no CSV estão compatíveis com a estrutura da tabela 'operadoras'.
--     3. Se há necessidade de conversões para tipos de dados específicos.
--
-- ============================================================================
  
\copy operadoras FROM './data/Relatorio_cadop.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ';', ENCODING 'UTF8');

