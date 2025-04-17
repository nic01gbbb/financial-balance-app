CREATE TABLE account
(
    id           UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    balance      DECIMAL(15, 2) NOT NULL,
    last_updated TIMESTAMP,
    user_id      UUID NOT NULL,                          -- Coluna de referência ao usuário
    FOREIGN KEY (user_id) REFERENCES users (id) -- Relacionamento com a tabela user
);
