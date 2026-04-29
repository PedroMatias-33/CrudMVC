DROP DATABASE IF EXISTS crudmvc;
CREATE DATABASE crudmvc;
USE crudmvc;

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE chamado_tecnico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    solicitante VARCHAR(100) NOT NULL,
    patrimonio VARCHAR(100) NOT NULL,
    local_sala VARCHAR(100) NOT NULL,
    data_chamado VARCHAR(20),
    prioridade VARCHAR(50),
    problema VARCHAR(255) NOT NULL,
    diagnostico VARCHAR(255),
    status VARCHAR(50)
);

-- Usuário inicial para teste. Senha: admin
INSERT INTO Usuarios (nome, email, senha)
VALUES ('Administrador', 'admin@admin.com', 'admin');
