CREATE TABLE expenses
(
    id           UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    expense_type VARCHAR(255),
    description  VARCHAR(255),
    amount       DECIMAL(10, 2),
    due_date     TIMESTAMP,
    user_id      UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
