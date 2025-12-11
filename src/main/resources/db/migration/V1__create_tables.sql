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
