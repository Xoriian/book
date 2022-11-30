DROP DATABASE IF EXISTS crtv_sauvetage;
CREATE DATABASE crtv_sauvetage;
USE crtv_sauvetage;

CREATE TABLE Personnes (
    id_personne MEDIUMINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(40),
    prenom VARCHAR(40),
    lien_personne TEXT
)
ENGINE=InnoDB;

CREATE TABLE Bateaux (
    id_bateau MEDIUMINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom_bateau varchar(40) NOT NULL,
    lien_bateau TEXT
)
ENGINE=INNODB;

CREATE TABLE Missions (
	id_mission MEDIUMINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_bateau MEDIUMINT UNSIGNED NOT NULL,
    id_personne MEDIUMINT UNSIGNED NOT NULL,
    est_sauveur BOOLEAN NOT NULL,
    CONSTRAINT fk_id_bateau FOREIGN KEY (id_bateau) REFERENCES Bateaux(id_bateau),
    CONSTRAINT fk_id_personne FOREIGN KEY (id_personne) REFERENCES Personnes(id_personne)
    
)
ENGINE=INNODB;

CREATE TABLE Infos_missions (
	id_mission MEDIUMINT UNSIGNED NOT NULL,
    date_mission DATE NOT NULL,
    lieu varchar(40) NOT NULL,
    lien_mission TEXT,
    PRIMARY KEY(date_mission, lieu)
)
ENGINE=INNODB;

#SHOW TABLES;

INSERT INTO Personnes 
VALUES(NULL,"BRUNET","Auguste","https://sauveteurdudunkerquois.fr/bru-3245h/"),
(NULL,"BOSSU","Louis","https://sauveteurdudunkerquois.fr/BOSL-3248H/"),
(NULL,"LAVIE","Charles","https://sauveteurdudunkerquois.fr/LAV-1666H/"),
(NULL,"GOSSIN","Charles","https://sauveteurdudunkerquois.fr/gos-1261h/"),
(NULL,"CHARET","Eugène","https://sauveteurdudunkerquois.fr/char-0466h/"),
(NULL,"DELUGNY","Pierre-Louis",",https://sauveteurdudunkerquois.fr/del-0752h/"),
(NULL,"CRETON","Julien","https://sauveteurdudunkerquois.fr/pat012/"),
(NULL,"GASPARD MILO","Guillaume","https://sauveteurdudunkerquois.fr/pat105/"),

(NULL,"PESQUET","Thomas","https://www.instagram.com/thom_astro/");


INSERT INTO Bateaux
VALUES(NULL,"Canot SH","https://sauveteurdudunkerquois.fr/canot-sh1/"),
(NULL,"Nouveau Dunkerque","https://sauveteurdudunkerquois.fr/nouveau-dunkerque/"),
(NULL,"Susan Gray","https://sauveteurdudunkerquois.fr/susan-gray/"),
(NULL,"Marie Bouteneff","https://sauveteurdudunkerquois.fr/marie-bouteneff/"),
(NULL,"Sainte Aline","https://sauveteurdudunkerquois.fr/sainte-aline/"),
(NULL,"Clermont-Tonnerre","https://sauveteurdudunkerquois.fr/clermont-tonnerre/"),
(NULL,"Sainte-Sophie","https://sauveteurdudunkerquois.fr/sainte-sophie/"),
(NULL,"Amicia","https://sauveteurdudunkerquois.fr/amicia/"),
(NULL,"Maurice Pinel","https://sauveteurdudunkerquois.fr/6837-2/"),
(NULL,"Patron Lepretre et Brunel","https://sauveteurdudunkerquois.fr/patrons-lepretre-et-brunel/"),
(NULL,"Francois Tixier","https://sauveteurdudunkerquois.fr/canot-de-malo-les-bains/"),
(NULL,"Corvettes","https://sauveteurdudunkerquois.fr/bateaux-pilotes/"),
(NULL,"Remorqueurs Dunkerquois","https://sauveteurdudunkerquois.fr/remorqueurs-dunkerquois/"),
(NULL,"La Victoire",NULL);

INSERT INTO Missions
VALUES(NULL,14,8,TRUE);

INSERT INTO Infos_missions
VALUES(1,"1810-12-28","Dunkerque","https://sauveteurdudunkerquois.fr/10que-0213b");


SELECT nom, prenom, lien_personne FROM Personnes WHERE prenom="Auguste" OR nom="Auguste";
SELECT nom_bateau, lien_bateau FROM Bateaux WHERE nom_bateau="Corvette";
SELECT lien_mission, date_mission, lieu, nom, prenom, nom_bateau
    FROM Missions INNER JOIN Infos_missions INNER JOIN Bateaux INNER JOIN Personnes
    ON Missions.id_mission = Infos_missions.id_mission 
		AND Missions.id_bateau = Bateaux.id_bateau
        AND Missions.id_personne = Personnes.id_personne
    WHERE lieu="Dunkerque";