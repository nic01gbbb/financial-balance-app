# 💰 Financial Balance

**Financial Balance** é uma aplicação web para controle de finanças pessoais.  
Ela permite que o usuário registre receitas e despesas, visualize o saldo atual, acompanhe um histórico de transações e analise os gastos por categoria em gráficos intuitivos.

Este repositório contém o **frontend (React)** e o **backend (Java com Spring Boot)** da aplicação.

---

## 🚀 Funcionalidades Principais

- ✅ Adicionar e remover transações (receitas e despesas)
- 📊 Visualizar saldo atual e total de entradas/saídas
- 🔎 Filtrar transações por data ou categoria
- 📈 Exibir gráficos de despesas por categoria
- 💾 Dados armazenados em banco de dados relacional (ex: PostgreSQL ou H2)

---

## 🧰 Tecnologias Utilizadas

### 🔙 Backend
- Java 17+
- Spring Boot
- Maven
- JPA / Hibernate
- Banco de dados: PostgreSQL ou H2

### 🔜 Frontend
- React
- JavaScript
- Axios
- CSS (ou alguma biblioteca como Tailwind/Bootstrap)

---

## 📂 Estrutura do Projeto

📁 financial-balance/ ├── backend/ # Código do backend (Spring Boot) └── frontend/ # Código do frontend (React)

yaml
Copiar
Editar

---

## ▶️ Como Executar o Projeto

### 📌 Backend

1. Navegue até a pasta do backend:
```bash
cd backend
Instale as dependências e execute o projeto:

bash
Copiar
Editar
mvn clean install
mvn spring-boot:run

📌 Frontend
Navegue até a pasta do frontend:

bash
Copiar
Editar
cd frontend
Instale as dependências e execute o projeto:

bash
Copiar
Editar
npm install
npm start
📝 Observações
Certifique-se de que o backend esteja rodando antes de iniciar o frontend.

Verifique o arquivo .env (ou application.properties) para configurar URLs e variáveis de ambiente conforme necessário.

