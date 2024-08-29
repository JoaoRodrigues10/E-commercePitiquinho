
-- Tabela para Usuários
CREATE TABLE IF NOT EXISTS tb_usuarios (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_nome VARCHAR(100) NOT NULL,
    ds_cpf VARCHAR(14) UNIQUE NOT NULL,
    ds_email VARCHAR(100) UNIQUE NOT NULL,
    ds_senha VARCHAR(255) NOT NULL,
    bo_status BOOLEAN NOT NULL DEFAULT TRUE,
    ds_grupo VARCHAR(20) CHECK (ds_grupo IN ('Administrador', 'Estoquista')) NOT NULL
);

-- Tabela para Produtos
CREATE TABLE IF NOT EXISTS tb_produtos (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_nome VARCHAR(100) NOT NULL,
    ds_descricao TEXT,
    nu_preco DECIMAL(10, 2) NOT NULL,
    nu_quantidade_em_estoque INT NOT NULL,
    ds_categoria VARCHAR(50)
);

