-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, email, password, first_name, last_name, role, manager_id, is_active)
VALUES (:id, :email, :password, :first_name, :last_name, :role::user_role, :manager_id, :is_active)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name,
    last_name  = :last_name,
    email      = :email,
    role       = :role::user_role,
    is_active  = :is_active,
    updated_at = NOW()
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the email
SELECT * FROM users
WHERE email = :email

-- :name get-user-by-id :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name get-users-by-manager :? :*
-- :doc retrieves all users for a given manager
SELECT * FROM users
WHERE manager_id = :manager_id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id

-- :name get-user-by-email :? :1
-- :doc retrieves a user record given the email
SELECT * FROM users
WHERE email = :email
AND is_active = TRUE

-- :name delete-user-by-email! :! :n
-- :doc deletes a user record given the email
DELETE FROM users
WHERE email = :email