
CREATE TABLE users (
    id VARCHAR(100) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    type VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE drivers (
    id VARCHAR(100) PRIMARY KEY,
    identification_number VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    license_number VARCHAR(255) NOT NULL,
    user_id VARCHAR(100),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_driver_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE vehicles (
    id VARCHAR(100) PRIMARY KEY,
    plate VARCHAR(255),
    brand VARCHAR(255),
    color VARCHAR(255),
    seats VARCHAR(255),
    status VARCHAR(50),
    driver_id VARCHAR(100),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_vehicle_driver FOREIGN KEY (driver_id) REFERENCES drivers(id)
);