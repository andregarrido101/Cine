INSERT INTO movies (id, title, synopsis, release_year, genre, director, duration_minutes)
VALUES (1, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 2010, 'Science Fiction', 'Christopher Nolan', 148),
       (2, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 1972, 'Crime', 'Francis Ford Coppola', 175),
       (3, 'Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster''s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 1994, 'Crime', 'Quentin Tarantino', 154),
       (4, 'The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 2008, 'Action', 'Christopher Nolan', 152),
       (5, 'Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 1994, 'Drama', 'Robert Zemeckis', 142),
       (6, 'The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 1999, 'Science Fiction', 'The Wachowskis', 136);

INSERT INTO users (id, username, password_hash, email, created_at)
VALUES (1, 'admin', '$2a$10$yOCQ1SZLmucYIURFLQdkJ.4OV8KhWUeI8Iv1GaCYdZTqfSXK9DxnS', 'admin@email.com', CURRENT_TIMESTAMP);

INSERT INTO rooms (id, room_number, is_available, capacity)
VALUES (1, 101, 'AVAILABLE_ROOM', 50),
       (2, 102, 'AVAILABLE_ROOM', 75),
       (3, 103, 'OCCUPIED_ROOM', 100),
       (4, 104, 'AVAILABLE_ROOM', 60),
       (5, 105, 'AVAILABLE_ROOM', 80),
       (6, 106, 'OCCUPIED_ROOM', 120);

INSERT INTO sessions (room_id, movie_id, price_per_seat, start_session_time, end_session_time, available_seats)
VALUES (1, 5,  9.50, '2025-11-30 08:00:00', '2025-11-30 10:22:00', 50),
       (1, 3, 11.00, '2025-11-30 10:52:00', '2025-11-30 13:26:00', 50),
       (1, 1, 12.50, '2025-11-30 16:26:00', '2025-11-30 18:54:00', 50),
       (1, 6, 14.00, '2025-11-30 19:24:00', '2025-11-30 21:40:00', 50),
       (1, 4, 13.00, '2025-11-30 22:10:00', '2025-11-30 00:32:00', 50),
       (1, 2, 10.00, '2025-11-30 01:02:00', '2025-11-30 03:37:00', 50),
       (1, 2, 10.00, '2025-11-30 00:01:00', '2025-11-30 00:10:00', 50),
       (1, 2, 10.00, '2025-11-30 00:27:00', '2025-11-30 00:30:00', 50);
