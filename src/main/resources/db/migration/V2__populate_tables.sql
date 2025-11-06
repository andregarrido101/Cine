INSERT INTO movies (id, title, synopsis, release_year, genre, director, duration_minutes)
VALUES (1, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 2010, 'Science Fiction', 'Christopher Nolan', 148),
       (2, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 1972, 'Crime', 'Francis Ford Coppola', 175),
       (3, 'Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster''s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 1994, 'Crime', 'Quentin Tarantino', 154),
       (4, 'The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 2008, 'Action', 'Christopher Nolan', 152),
       (5, 'Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 1994, 'Drama', 'Robert Zemeckis', 142),
       (6, 'The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 1999, 'Science Fiction', 'The Wachowskis', 136);

INSERT INTO users (id, username, password_hash, email, created_at)
VALUES (1, 'john_doe', 'hashed_password_1', 'john@email.com', CURRENT_TIMESTAMP),
       (2, 'jane_smith', 'hashed_password_2', 'jane@email.com', CURRENT_TIMESTAMP),
       (3, 'alice_jones', 'hashed_password_3', 'alice@email.com', CURRENT_TIMESTAMP);

INSERT INTO rooms (id, room_number, is_available, capacity)
VALUES (1, 101, 'AVAILABLE_ROOM', 50),
       (2, 102, 'AVAILABLE_ROOM', 75),
       (3, 103, 'OCCUPIED_ROOM', 100),
       (4, 104, 'AVAILABLE_ROOM', 60),
       (5, 105, 'AVAILABLE_ROOM', 80),
       (6, 106, 'OCCUPIED_ROOM', 120);

