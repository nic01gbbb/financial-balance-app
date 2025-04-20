CREATE TABLE transactions
CREATE TYPE transaction_type_enum AS ENUM ('INCOME', 'EXPENSE');

CREATE TABLE transaction (
                             id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                             account_id UUID NOT NULL,
                             amount DECIMAL(10, 2) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             transaction_type transaction_type_enum NOT NULL,
                             description TEXT,
                             FOREIGN KEY (account_id) REFERENCES account (id)
);

