--Need to esnure incomes table comes first and then Budget table second due to order of the sql statement
INSERT INTO incomes (id, income_Year, income_Month, salary) VALUES (nextval('income_id_seq'), '2023', 'August', 1500);
INSERT INTO incomes (id, income_Year, income_Month, salary) VALUES (nextval('income_id_seq'), '2023', 'July', 1500);
INSERT INTO incomes (id, income_Year, income_Month, salary) VALUES (nextval('income_id_seq'), '2023', 'June', 1500);

INSERT INTO budgets (fk_income_id, budget_id, budget_Name, budget_Amount) VALUES (1,nextval('budget_id_seq'), 'Travel', 140);
INSERT INTO budgets (fk_income_id, budget_id, budget_Name, budget_Amount) VALUES (2,nextval('budget_id_seq'), 'Food', 100);
INSERT INTO budgets (fk_income_id, budget_id, budget_Name, budget_Amount) VALUES (3,nextval('budget_id_seq'), 'Beer', 200);

INSERT INTO Expense (fk_income_id, fk_budget_id, id, expense_Name, amount) VALUES (1, 1, nextval('expense_id_seq'), 'Train', 20);
INSERT INTO Expense (fk_income_id, fk_budget_id, id, expense_Name, amount) VALUES (2, 3, nextval('expense_id_seq'), 'Pints', 40)
INSERT INTO Expense (fk_income_id, fk_budget_id, id, expense_Name, amount) VALUES (3, 2, nextval('expense_id_seq'), 'Burger', 15)
INSERT INTO Expense (fk_income_id, fk_budget_id, id, expense_Name, amount) VALUES (1, 2, nextval('expense_id_seq'), 'Maccies', 20)
INSERT INTO Expense (fk_income_id, fk_budget_id, id, expense_Name, amount) VALUES (2, 1, nextval('expense_id_seq'), 'Fuel', 50)
INSERT INTO Expense (fk_income_id, fk_budget_id, id, expense_Name, amount) VALUES (3, 1, nextval('expense_id_seq'), 'Football', 500)

INSERT INTO Customer (id, userName, password) VALUES (nextval('customer_id_seq'), 'JD98', 'password');



