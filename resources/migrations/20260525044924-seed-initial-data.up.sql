INSERT INTO users (id, email, password, first_name, last_name, role, is_active)
VALUES
    ('a0000000-0000-0000-0000-000000000001',
     'eva@tempusfugit.com',
     'bcrypt+sha512$de9f5f2d1c907c43c0ecde2c1266af84$12$0c3dfd06e4d2a73d339fac52ae994a0af02b26ceb1b810be',
     'Eva', 'Admin', 'admin', TRUE),

    ('a0000000-0000-0000-0000-000000000002',
     'philip@tempusfugit.com',
     'bcrypt+sha512$de9f5f2d1c907c43c0ecde2c1266af84$12$0c3dfd06e4d2a73d339fac52ae994a0af02b26ceb1b810be',
     'Philip', 'Manager', 'manager', TRUE),

    ('a0000000-0000-0000-0000-000000000003',
     'matthew@tempusfugit.com',
     'bcrypt+sha512$de9f5f2d1c907c43c0ecde2c1266af84$12$0c3dfd06e4d2a73d339fac52ae994a0af02b26ceb1b810be',
     'Matthew', 'Employee', 'employee', TRUE),

    ('a0000000-0000-0000-0000-000000000004',
     'bruna@tempusfugit.com',
     'bcrypt+sha512$de9f5f2d1c907c43c0ecde2c1266af84$12$0c3dfd06e4d2a73d339fac52ae994a0af02b26ceb1b810be',
     'Bruna', 'Employee', 'employee', TRUE);

--;;

UPDATE users SET manager_id = 'a0000000-0000-0000-0000-000000000002'
WHERE id IN (
    'a0000000-0000-0000-0000-000000000003',
    'a0000000-0000-0000-0000-000000000004'
);