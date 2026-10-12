CREATE TABLE category (
                          id   INTEGER PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE film (
                      id           INTEGER PRIMARY KEY AUTO_INCREMENT,
                      title        VARCHAR(100) NOT NULL,
                      release_year INTEGER,
                      description  VARCHAR(500),
                      release_date DATE,
                      duration     INTEGER,
                      category_id  INTEGER,
                      FOREIGN KEY (category_id) REFERENCES category(id),
                      CONSTRAINT uc_film UNIQUE (title, release_year)
);

CREATE TABLE actor (
                       id   INTEGER PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL,
                       bio  VARCHAR(255)
);

CREATE TABLE film_actor (
                            film_id  INTEGER,
                            actor_id INTEGER,
                            PRIMARY KEY (film_id, actor_id),
                            FOREIGN KEY (film_id)  REFERENCES film(id),
                            FOREIGN KEY (actor_id) REFERENCES actor(id)
);