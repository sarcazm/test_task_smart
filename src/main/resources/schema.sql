CREATE TABLE IF NOT EXISTS Valute
(
    id SERIAL PRIMARY KEY,
    num_code CHARACTER VARYING(3) NOT NULL,
    char_code CHARACTER VARYING(3) NOT NULL,
    nominal INTEGER NOT NULL,
    name CHARACTER VARYING(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Value
(
    id SERIAL PRIMARY KEY,
    valute_id INTEGER NOT NULL,
    value DECIMAL NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (valute_id) REFERENCES Valute(id)
);

CREATE TABLE IF NOT EXISTS Usr(
                                     id SERIAL PRIMARY KEY,
                                     username CHARACTER VARYING(50) NOT NULL,
                                     password CHARACTER VARYING(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS Record

(

    id             SERIAL PRIMARY KEY,
    user_id      INTEGER NOT NULL,
    from_valute_id INTEGER NOT NULL,
    to_valute_id   INTEGER NOT NULL,
    qty            INTEGER NOT NULL,
    sum            DECIMAL NOT NULL,
    date           DATE    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Usr (id),
    FOREIGN KEY (from_valute_id) REFERENCES Valute (id),
    FOREIGN KEY (to_valute_id) REFERENCES Valute (id)

);

