SELECT 
    o.registro_ans,
    o.razao_social,
   'R$' || TO_CHAR(SUM(dc.vl_saldo_final), 'FM999,999,999,999.00') AS total_despesas
FROM demonstracoes_contabeis dc
JOIN operadoras o ON dc.reg_ans = o.registro_ans
WHERE dc.descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
  AND EXTRACT(YEAR FROM dc.data) = 2024
GROUP BY o.registro_ans, o.razao_social
ORDER BY SUM(dc.vl_saldo_final) DESC
LIMIT 10;


WITH ultimo_trimestre AS (
    SELECT MAX(EXTRACT(QUARTER FROM data)) AS trimestre
    FROM demonstracoes_contabeis
    WHERE EXTRACT(YEAR FROM data) = 2024
)
SELECT 
    o.registro_ans,
    o.razao_social,
    'R$ ' || TO_CHAR(SUM(dc.vl_saldo_final), 'FM999,999,999,999.00') AS total_despesas
FROM demonstracoes_contabeis dc
JOIN operadoras o ON dc.reg_ans = o.registro_ans
JOIN ultimo_trimestre ut ON EXTRACT(QUARTER FROM dc.data) = ut.trimestre
WHERE EXTRACT(YEAR FROM dc.data) = 2024
GROUP BY o.registro_ans, o.razao_social
ORDER BY SUM(dc.vl_saldo_final) DESC
LIMIT 10;
