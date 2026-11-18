
-- ==========================================
-- BARBEARIA MANAGER
-- Banco de Dados: crud_manager
-- Baseado no CRUD Manager do professor
-- ==========================================

-- ATENCAO:
-- Descomente a linha abaixo somente se quiser
-- apagar completamente o banco existente.
-- DROP DATABASE IF EXISTS crud_manager;

CREATE DATABASE IF NOT EXISTS crud_manager;

USE crud_manager;


-- ==========================================
-- 1. TABELA DE USUARIOS / CLIENTES
-- ==========================================

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sexo ENUM('M', 'F'),
    email VARCHAR(150) NOT NULL
);


-- ==========================================
-- 2. TABELA DE POSTS
-- ==========================================

CREATE TABLE IF NOT EXISTS posts (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    post_date DATE NOT NULL,
    user_id INT NOT NULL,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- ==========================================
-- 3. TABELA DE EMPRESAS
-- ==========================================

CREATE TABLE IF NOT EXISTS companies (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(128) NOT NULL,
    `role` VARCHAR(128) NOT NULL,
    `start` DATE NOT NULL,
    `end` DATE,
    user_id INT NOT NULL,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- ==========================================
-- 4. TABELA DE BARBEIROS
-- ==========================================

CREATE TABLE IF NOT EXISTS barbers (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    user_id INT NOT NULL,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- ==========================================
-- 5. TABELA DE AGENDAMENTOS
-- ==========================================

CREATE TABLE IF NOT EXISTS appointments (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    barber_id INT NOT NULL,
    service VARCHAR(100) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Agendado',
    notes TEXT,

    FOREIGN KEY (user_id)
        REFERENCES users(id),

    FOREIGN KEY (barber_id)
        REFERENCES barbers(id)
);


-- ==========================================
-- 6. TABELA DE CONTAS DE LOGIN
-- ==========================================

CREATE TABLE IF NOT EXISTS login_accounts (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    user_id INT NOT NULL UNIQUE,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- ==========================================
-- 7. DADOS DE EXEMPLO DO PROFESSOR
-- Executar somente na instalacao inicial.
-- ==========================================

INSERT INTO users VALUES
(DEFAULT, 'Emerson Carvalho', 'M', 'emerson@mail.com'),
(DEFAULT, 'Luiza Carvalho', 'F', 'lu@mail.com'),
(DEFAULT, 'Elenice Carvalho', 'F', 'le@mail.com'),
(DEFAULT, 'Noé Carvalho', 'M', 'noe@mail.com'),
(DEFAULT, 'Rosânia Carvalho', 'F', 'ro@mail.com');


INSERT INTO posts VALUES
(DEFAULT, 'Olá do Emerson', CURDATE(), 1),
(DEFAULT, 'Olá da Luiza', CURDATE(), 2),
(DEFAULT, 'Olá da Denise', CURDATE(), 3),
(DEFAULT, 'Olá do Noé', CURDATE(), 4),
(DEFAULT, 'Olá da Rosânia', CURDATE(), 5),
(DEFAULT, 'Olá da Rosânia 1', CURDATE(), 5),
(DEFAULT, 'Olá da Rosânia 2', CURDATE(), 5),
(DEFAULT, 'Olá da Rosânia 3', CURDATE(), 5);
