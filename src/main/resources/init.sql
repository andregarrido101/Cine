CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    synopsis TEXT,
    release_year INT,
    genre VARCHAR(100),
    director VARCHAR(255),
    duration_minutes INT NOT NULL,
    active_session VARCHAR(50) DEFAULT 'UNAVAILABLE_MOVIE'
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    room_number INT UNIQUE NOT NULL,
    capacity INT NOT NULL,
    is_available VARCHAR(50) DEFAULT 'AVAILABLE_ROOM'
);

CREATE TABLE sessions (
    id SERIAL PRIMARY KEY,
    movie_id INT,
    room_id INT,
    price_per_seat DECIMAL(6, 2) NOT NULL,
    start_session_time TIMESTAMP NOT NULL,
    end_session_time TIMESTAMP,
    available_seats INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    session_id INT NOT NULL,
    seat_code VARCHAR(10) NOT NULL,
    purchase_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE
);

CREATE TABLE seats (
    id SERIAL PRIMARY KEY,
    seat_code VARCHAR(10) NOT NULL,
    session_id INT NOT NULL,
    ticket_id INT,
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE SET NULL,
    CONSTRAINT unique_seat_per_session UNIQUE (seat_code, session_id)
);





INSERT INTO movies (id, title, synopsis, release_year, genre, director, duration_minutes)
VALUES (1, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 2010, 'Science Fiction', 'Christopher Nolan', 148),
       (2, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 1972, 'Crime', 'Francis Ford Coppola', 175),
       (3, 'Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster''s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 1994, 'Crime', 'Quentin Tarantino', 154),
       (4, 'The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 2008, 'Action', 'Christopher Nolan', 152),
       (5, 'Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 1994, 'Drama', 'Robert Zemeckis', 142),
       (6, 'The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 1999, 'Science Fiction', 'The Wachowskis', 136);

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
