CREATE DATABASE jeu;
USE jeu;

CREATE TABLE joueur (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(255) NOT NULL,
                        score INT DEFAULT 0,
                        nbSoldats INT DEFAULT 0,
                        nbVilles INT DEFAULT 0,
                        nbForets INT DEFAULT 0,
                        nbTuiles INT DEFAULT 0,
                        pointProduction INT DEFAULT 0,
                        password VARCHAR(255) NOT NULL,
);
select * from joueur;
DROP TABLE joueur;
ALTER TABLE joueur MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
DESC joueur;