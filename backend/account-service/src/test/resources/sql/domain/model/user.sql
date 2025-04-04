INSERT INTO users (user_id, username, email, password, role, created_at, modified_at)
VALUES (1, 'username', 'user@email.com', 'XXX', 'USER',
        now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC');