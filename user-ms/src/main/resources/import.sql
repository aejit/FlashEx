INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users(email, name, password, username) VALUES('admin@admin.com', 'admin','$2a$10$rz9L3HNq4i3m3/U63er9..AtqT7tRy6BcrD6Zr4kbA2W4z8PEg69O','Darshan');
INSERT INTO users(email, name, password, username) VALUES('parth@admin.com', 'Parth','$2a$10$g6aoEFyGUD/GVq9ItmIOwuYTwXGnLLCs.peHKt1WUTZjUeMpXv5wS','parth123');
INSERT INTO users(email, name, password, username) VALUES('anup@admin.com', 'Anup','$2a$10$zEtMEhTWcBBiBVid/MIIwuOa8p1/Z14pb.Wn50q0q.f0NLyaJJJGm','anup123');
INSERT INTO users(email, name, password, username) VALUES('anurag@admin.com', 'Anurag','$2a$10$7Xlx5sW8VxwPPSwP4R7/xe80OGUhHKaWCGT9Gkm.c8o89LYaaqgGu','anurag123');

INSERT INTO user_roles(user_id, role_id) VALUES('1', '2');
INSERT INTO user_roles(user_id, role_id) VALUES('2', '1');
INSERT INTO user_roles(user_id, role_id) VALUES('3', '1');
INSERT INTO user_roles(user_id, role_id) VALUES('4', '1');





