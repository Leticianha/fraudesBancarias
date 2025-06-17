CREATE DATABASE fraude_bancaria;
USE fraude_bancaria;

CREATE TABLE Cliente (
    cliente_id INT NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(14) NOT NULL UNIQUE
);

/*INSERT INTO Cliente (cliente_id, nome, cpf)
VALUES 
    (5467, 'Eduarda Silva', '582.654.896-00'),
    (8759, 'Gabriel Bastos', '145.658.321-01');*/
    
ALTER TABLE Cliente
ADD COLUMN email VARCHAR(100);

/*UPDATE Cliente
SET email = 'maluferrazm@gmail.com'
WHERE cliente_id = 96;*/

CREATE TABLE Empresa (
    empresa_id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj CHAR(14) NOT NULL UNIQUE,
    cnpj_valido BOOLEAN NOT NULL
);

/*INSERT INTO Empresa (nome, cnpj, cnpj_valido)
VALUES 
    ('Feijãozinho', '00623904000173', TRUE),
    ('Delu Meias', '00623904000556', FALSE);*/

CREATE TABLE Debito_Automatico (
    debito_id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    empresa_id INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_cadastro DATE NOT NULL,
    tipo_recorrencia ENUM('Mensal', 'Semanal', 'Anual', 'Único') NOT NULL,
    status_suspeita ENUM('Normal', 'Suspeito', 'Confirmado como fraude') DEFAULT 'Normal',
    status_ativo ENUM('Ativo', 'Inativo') DEFAULT 'Ativo',
    FOREIGN KEY (cliente_id) REFERENCES Cliente(cliente_id),
    FOREIGN KEY (empresa_id) REFERENCES Empresa(empresa_id)
);

ALTER TABLE Debito_Automatico
ADD COLUMN status_acao ENUM('Pendente', 'Ignorado', 'Bloqueado', 'Denunciado') DEFAULT 'Pendente';

/*INSERT INTO Debito_Automatico (cliente_id, empresa_id, valor, data_cadastro, tipo_recorrencia, status_suspeita, status_ativo)
VALUES 
    (8759, 2, 500.00, '2011-07-28', 'Mensal', 'Suspeito', 'Ativo'),
    (5467, 1, 30.00, '2011-06-05', 'Mensal', 'Normal', 'Ativo');
    
INSERT INTO Debito_Automatico (cliente_id, empresa_id, valor, data_cadastro, tipo_recorrencia, status_suspeita, status_ativo)
VALUES (96, 1, 99.99, CURDATE(), 'Mensal', 'Suspeito', 'Ativo');

INSERT INTO Debito_Automatico (cliente_id, empresa_id, valor, data_cadastro, tipo_recorrencia, status_suspeita, status_ativo)
VALUES (96, 2, 99.99, CURDATE(), 'Mensal', 'Normal', 'Ativo');*/

CREATE TABLE Alerta (
    alerta_id INT AUTO_INCREMENT PRIMARY KEY,
    debito_id INT NOT NULL,
    data_alerta DATETIME NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    status_alerta ENUM('Pendente', 'Resolvido') DEFAULT 'Pendente',
    FOREIGN KEY (debito_id) REFERENCES Debito_Automatico(debito_id)
);

/*INSERT INTO Alerta (debito_id, data_alerta, mensagem, status_alerta)
VALUES (1, NOW(), 'Débito suspeito detectado', 'Pendente');*/

/*INSERT INTO Alerta (debito_id, data_alerta, mensagem, status_alerta)
VALUES (3, NOW(), 'Débito suspeito detectado para cliente 96', 'Pendente');*/

/*INSERT INTO Alerta (debito_id, data_alerta, mensagem, status_alerta)
VALUES (3, NOW(), 'DTudo ok', 'Resolvido');*/

CREATE TABLE Acao_Cliente (
    acao_id INT AUTO_INCREMENT PRIMARY KEY,
    alerta_id INT NOT NULL,
    cliente_id INT NOT NULL,
    acao ENUM('Bloquear', 'Denunciar', 'Ignorar') NOT NULL,
    data_acao DATETIME NOT NULL,
    FOREIGN KEY (alerta_id) REFERENCES Alerta(alerta_id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(cliente_id)
);

/*INSERT INTO Acao_Cliente (alerta_id, cliente_id, acao, data_acao)
VALUES (1, 8759, 'Denunciar', NOW());*/

CREATE TABLE Log_Historico (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    descricao_evento VARCHAR(255) NOT NULL,
    data_evento DATETIME NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(cliente_id)
);

ALTER TABLE Log_Historico
ADD COLUMN status_acao ENUM('Pendente', 'Ignorado', 'Bloqueado', 'Denunciado') DEFAULT 'Pendente';

/*INSERT INTO Log_Historico (cliente_id, descricao_evento, data_evento, status_acao)
VALUES (96, 'cliente bloqueou', NOW(), 'Bloqueado');*/

-- Apague tudo antes, se quiser (conforme fizemos)
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Acao_Cliente;
DELETE FROM Alerta;
DELETE FROM Debito_Automatico;
DELETE FROM Log_Historico;
DELETE FROM Empresa;
DELETE FROM Cliente;
SET SQL_SAFE_UPDATES = 1;

INSERT INTO Cliente (cliente_id, nome, cpf, email)
VALUES 
    (1, 'Amanda Oliveira', '159.753.456-82', 'amandaoli@gmail.com'),
    (2, 'Gabriel Torres', '887.365.545-25', 'gabtorres@gmail.com'),
    (3, 'Cristina Basto', '333.856.894-62', 'bastocris@gmail.com');
    
-- Insira empresas (empresa_id será auto_increment, mas para garantir ids exatos, podemos usar INSERT com explicit id)
INSERT INTO Empresa (empresa_id, nome, cnpj, cnpj_valido) VALUES
    (1, 'Amazon', '15436940000103', TRUE),
    (2, 'Netflix', '13590585000270', TRUE),
    (3, 'Bradesco', '60746948000112', TRUE),
    (4, 'NetProX', '42345678000193', FALSE),
    (5, 'EiComida', '52345678000194', FALSE),
    (6, 'ClimaTempo', '62345678000195', FALSE);
    
INSERT INTO Empresa (empresa_id, nome, cnpj, cnpj_valido) VALUES
	(13, 'CasaBaia', '78945612336524', FALSE);
-- Agora sim insira os débitos com as ids corretas:

INSERT INTO Debito_Automatico (cliente_id, empresa_id, valor, data_cadastro, tipo_recorrencia, status_suspeita, status_ativo, status_acao)
VALUES  
    (1, 4, 45.50, CURDATE(), 'Mensal', 'Suspeito', 'Ativo', 'Denunciado'), -- Empresa falsa
    (2, 5, 39.99, CURDATE(), 'Semanal', 'Normal', 'Ativo', 'Pendente'), -- Empresa falsa
    (3, 6, 99.99, CURDATE(), 'Anual', 'Suspeito', 'Ativo', 'Bloqueado'), -- Empresa falsa
    (1, 1, 79.90, CURDATE(), 'Mensal', 'Normal', 'Ativo', 'Pendente'), -- Empresa verdadeira
    (2, 2, 150.00, CURDATE(), 'Semanal', 'Normal', 'Ativo', 'Pendente'), -- Empresa verdadeira
    (3, 3, 200.00, CURDATE(), 'Anual', 'Normal', 'Ativo', 'Pendente'); -- Empresa verdadeira
    
INSERT INTO Debito_Automatico (cliente_id, empresa_id, valor, data_cadastro, tipo_recorrencia, status_suspeita, status_ativo, status_acao)
VALUES  
    (1, 13, 50.50, CURDATE(), 'Mensal', 'Suspeito', 'Ativo', 'Pendente'); 

    
ALTER TABLE Empresa AUTO_INCREMENT = 13;

SELECT debito_id, cliente_id, empresa_id, valor FROM Debito_Automatico;

-- Inserir alertas relacionados a débitos falsos
INSERT INTO Alerta (debito_id, data_alerta, mensagem, status_alerta)
VALUES 
    (59, NOW(), 'Débito normal com recorrência semanal', 'Pendente'),
    (60, NOW(), 'Débito legítimo com empresa confiável', 'Resolvido'),
    (61, NOW(), 'Monitoramento preventivo ativado', 'Pendente'),
    (62, NOW(), 'Débito anual sem suspeitas', 'Resolvido');

-- Inserir ações do cliente
INSERT INTO Acao_Cliente (alerta_id, cliente_id, acao, data_acao)
VALUES 
    (49, 2, 'Denunciar', NOW()),  
    (50, 3, 'Bloquear', NOW());   

SELECT alerta_id, debito_id, mensagem FROM Alerta;

-- Inserir logs
INSERT INTO Log_Historico (cliente_id, descricao_evento, data_evento, status_acao)
VALUES 
    (1, 'Cliente denunciou empresa falsa NetProX', NOW(), 'Denunciado'),
    (3, 'Cliente bloqueou empresa falsa CliemaTempo', NOW(), 'Bloqueado');
    
SELECT * FROM Cliente;
SELECT * FROM Empresa;
SELECT * FROM Debito_Automatico;
SELECT * FROM Alerta;
SELECT * FROM Acao_Cliente;
SELECT * FROM Log_Historico;

SELECT debito_id, cliente_id, empresa_id, valor FROM Debito_Automatico;

