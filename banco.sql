CREATE TABLE IF NOT EXISTS tb_usuarios (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_nome VARCHAR(100) NOT NULL,
    ds_cpf VARCHAR(14) UNIQUE NOT NULL,
    ds_email VARCHAR(100) UNIQUE NOT NULL,
    ds_senha VARCHAR(255) NOT NULL,
    bo_status BOOLEAN NOT NULL DEFAULT TRUE,
    ds_grupo VARCHAR(20) 
);

CREATE TABLE tb_produtos (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_nome VARCHAR(100) NOT NULL,
    ds_descricao TEXT,
    nu_preco DECIMAL(10, 2) NOT NULL,
    nu_quantidade_em_estoque INT NOT NULL,
    ds_categoria VARCHAR(50),
    ds_imagem VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS tb_categorias (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_nome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_pedidos (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_usuario_id BIGINT NOT NULL,
    dt_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nu_valor_total DECIMAL(10, 2) NOT NULL,
    ds_status VARCHAR(20) CHECK (ds_status IN ('Pendente', 'Pago', 'Cancelado')) NOT NULL,
    FOREIGN KEY (fk_usuario_id) REFERENCES tb_usuarios(pk_id)
);

CREATE TABLE IF NOT EXISTS tb_itens_pedido (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_pedido_id BIGINT NOT NULL,
    fk_produto_id BIGINT NOT NULL,
    nu_quantidade INT NOT NULL,
    nu_preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (fk_pedido_id) REFERENCES tb_pedidos(pk_id),
    FOREIGN KEY (fk_produto_id) REFERENCES tb_produtos(pk_id)
);

CREATE TABLE IF NOT EXISTS tb_enderecos (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_usuario_id BIGINT NOT NULL,
    ds_logradouro VARCHAR(255) NOT NULL,
    ds_numero VARCHAR(10) NOT NULL,
    ds_complemento VARCHAR(50),
    ds_bairro VARCHAR(100) NOT NULL,
    ds_cidade VARCHAR(100) NOT NULL,
    ds_estado VARCHAR(2) NOT NULL,
    ds_cep VARCHAR(9) NOT NULL,
    FOREIGN KEY (fk_usuario_id) REFERENCES tb_usuarios(pk_id)
);


CREATE TABLE IF NOT EXISTS tb_pagamentos (
    pk_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_pedido_id BIGINT NOT NULL,
    ds_tipo_pagamento VARCHAR(20) CHECK (ds_tipo_pagamento IN ('Cartão de Crédito', 'Boleto', 'Pix')) NOT NULL,
    nu_valor DECIMAL(10, 2) NOT NULL,
    dt_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fk_pedido_id) REFERENCES tb_pedidos(pk_id)
);

