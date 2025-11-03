CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    synopsis TEXT,
    release_year INT,
    genre VARCHAR(100),
    director VARCHAR(255),
    price DECIMAL(5, 2) NOT NULL,
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
    is_available VARCHAR(50) DEFAULT 'AVAILABLE_ROOM',
    capacity INT NOT NULL
);

CREATE TABLE sessions (
    id SERIAL PRIMARY KEY,
    movie_name VARCHAR(255) NOT NULL,
    price_per_seat DECIMAL(5, 2) NOT NULL,
    session_time VARCHAR(100) NOT NULL,
    room_number INT NOT NULL,
    available_seats INT NOT NULL
);

CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    session_id INT REFERENCES sessions(id),
    seat_code INT NOT NULL,
    purchase_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE seats (
    id SERIAL PRIMARY KEY,
    seat_code INT NOT NULL,
    session_id INT REFERENCES sessions(id),
    room_id INT REFERENCES rooms(id),
    ticket_id INT REFERENCES tickets(id)
);