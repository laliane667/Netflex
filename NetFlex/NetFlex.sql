-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : mar. 11 avr. 2023 à 07:49
-- Version du serveur :  5.7.34
-- Version de PHP : 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `NetFlex`
--

-- --------------------------------------------------------

--
-- Structure de la table `AccountSelections`
--

CREATE TABLE `AccountSelections` (
  `accountSelectionsId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `categoriesId` int(11) NOT NULL,
  `categoriesCounter` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Categories`
--

CREATE TABLE `Categories` (
  `categoriesId` int(11) NOT NULL,
  `categoriesName` varchar(256) NOT NULL,
  `categoriesDescription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Categories`
--

INSERT INTO `Categories` (`categoriesId`, `categoriesName`, `categoriesDescription`) VALUES
(1, 'Science-Fiction', 'Science fiction takes you beyond possibility, exploring worlds with imagination and science. It challenges us to push boundaries and imagine a future beyond our wildest dreams. It\'s a thrilling journey that leaves us in awe of the universe and our potential within it.'),
(3, 'Thriller', 'Thriller is a genre of literature, film, and television that\'s designed to elicit intense feelings of excitement, suspense, and anxiety in the viewer or reader. It\'s a high-stakes, fast-paced form of storytelling that often features unpredictable plot twists and thrilling action sequences. Whether you\'re watching a psychological thriller or reading a crime novel, the genre is designed to keep you on the edge of your seat and leave you guessing until the very end.'),
(4, 'Romance', 'Romance is a genre of fiction that focuses on the emotional and romantic relationships between characters. It\'s all about love, passion, and the exhilaration of a grand romantic gesture. Whether it\'s a tale of star-crossed lovers or a slow burn of passion, romance is sure to set your heart aflutter.'),
(5, 'Horror', 'Horror is a genre of storytelling that explores our deepest fears and darkest nightmares. It aims to scare, shock, and unsettle its audience by depicting supernatural, monstrous, or psychologically disturbing elements in a gripping and suspenseful way. From vampires and ghosts to serial killers and demonic possession, horror is a chilling journey into the unknown that will keep you on the edge of your seat.'),
(6, 'Documentary', 'Documentary is a genre of film that aims to educate, inform, and create awareness about real-life events, issues, and people. It uses real footage and interviews to provide insight and knowledge on a subject, and can be both informative and engaging.'),
(7, 'Comedy', 'Comedy is a genre of entertainment that aims to make the audience laugh through humorous and amusing situations or dialogue. It can range from lighthearted and silly to satirical and dark, and often features relatable characters and everyday situations with a comedic twist.'),
(8, 'Animation', 'Animation is a genre that brings characters and stories to life through the use of vibrant visuals and motion graphics. With endless possibilities for creative expression, animation appeals to both children and adults alike, making it a versatile and captivating form of entertainment.'),
(9, 'Action', 'Action movies are fast-paced, exciting films that often feature intense physical feats and exhilarating stunts. With a focus on high energy and adrenaline, action films are designed to thrill and entertain audiences with thrilling fight scenes, explosive car chases, and heart-pumping suspense. Whether it\'s a classic martial arts flick or a modern blockbuster, action movies are sure to keep you on the edge of your seat.'),
(10, 'Fantastic', 'Fantastic is a genre of fiction that often involves supernatural or magical elements, as well as imaginative and otherworldly settings. It can include stories about mythical creatures, alternate universes, and futuristic technology, among other things. The genre often explores themes of wonder, awe, and the unknown, and can appeal to those who enjoy exploring the limitless possibilities of the imagination.'),
(11, 'Stoner comedy', 'Stoner comedies are a delightful subgenre of films that celebrate the lighthearted, whimsical side of cannabis culture. These movies weave intoxicating tales filled with eccentric characters, absurd situations, and witty humor, ensuring a laid-back, uproarious viewing experience. Kick back, relax, and get ready to giggle as stoner comedies take you on a laughter-filled ride through a haze of unforgettable adventures.');

-- --------------------------------------------------------

--
-- Structure de la table `Comments`
--

CREATE TABLE `Comments` (
  `commentsId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL,
  `commentsText` varchar(256) NOT NULL,
  `commentsDatePosted` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Dislikes`
--

CREATE TABLE `Dislikes` (
  `dislikesId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Likes`
--

CREATE TABLE `Likes` (
  `likesId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Replies`
--

CREATE TABLE `Replies` (
  `repiesId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `commentsId` int(11) NOT NULL,
  `repliesText` varchar(256) NOT NULL,
  `repliesDatePosted` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Subscriptions`
--

CREATE TABLE `Subscriptions` (
  `subscriptionsId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `subscribersId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Users`
--

CREATE TABLE `Users` (
  `usersId` int(11) NOT NULL,
  `usersFirstName` varchar(256) NOT NULL,
  `usersLastName` varchar(256) NOT NULL,
  `usersEmail` varchar(256) NOT NULL,
  `usersUID` varchar(256) NOT NULL,
  `usersPassword` varchar(256) NOT NULL,
  `usersDateOfBirth` datetime NOT NULL,
  `usersPrivilege` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Users`
--

INSERT INTO `Users` (`usersId`, `usersFirstName`, `usersLastName`, `usersEmail`, `usersUID`, `usersPassword`, `usersDateOfBirth`, `usersPrivilege`) VALUES
(6, 'null', 'null', 'admin', 'null', '123', '2023-04-04 00:00:00', 1),
(7, 'Elian', 'Lamouroux', 'elian.lamouroux@gmail.com', 'Laliane', 'fancy', '2002-11-18 00:00:00', 0);

-- --------------------------------------------------------

--
-- Structure de la table `VideoCategorizations`
--

CREATE TABLE `VideoCategorizations` (
  `categorizationsId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL,
  `categoriesId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `VideoCategorizations`
--

INSERT INTO `VideoCategorizations` (`categorizationsId`, `videosId`, `categoriesId`) VALUES
(1, 3, 1),
(2, 3, 3),
(3, 3, 9),
(4, 4, 11),
(5, 5, 1),
(6, 5, 7),
(7, 5, 9),
(8, 6, 3),
(9, 6, 7),
(10, 6, 9),
(11, 7, 7),
(12, 7, 9),
(13, 8, 1),
(14, 8, 7),
(15, 8, 9),
(16, 9, 7),
(17, 9, 9),
(18, 10, 7),
(19, 10, 9),
(20, 11, 1),
(21, 11, 7),
(22, 11, 9),
(23, 12, 1),
(24, 12, 3),
(25, 12, 9),
(26, 13, 3),
(27, 13, 7),
(28, 13, 9);

-- --------------------------------------------------------

--
-- Structure de la table `Videos`
--

CREATE TABLE `Videos` (
  `videosId` int(11) NOT NULL,
  `videosTitle` varchar(256) NOT NULL,
  `videosDescription` text NOT NULL,
  `videosDuration` int(11) NOT NULL,
  `videosYear` datetime NOT NULL,
  `videosStreamPath` varchar(256) DEFAULT NULL,
  `videosThumbnailPath` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Videos`
--

INSERT INTO `Videos` (`videosId`, `videosTitle`, `videosDescription`, `videosDuration`, `videosYear`, `videosStreamPath`, `videosThumbnailPath`) VALUES
(3, 'Tenet', 'CIA agent known only as \"The Protagonist\" discovers a secret organization called Tenet that can manipulate time. With the help of a Tenet agent named Neil and a scientist named Kat, they set out to prevent a Russian oligarch named Sator from obtaining a dangerous device that could lead to the end of the world. The team faces thrilling and mind-bending challenges as they travel through time to save the future. With visually stunning effects and a talented cast, Tenet is a must-see for anyone who loves action-packed sci-fi thrillers.', 189, '2020-08-19 00:00:00', '/Movies/Tenet', '/Movies/Tenet/thumbnail.png'),
(5, 'Men in Black', '\"Men in Black\" is a science fiction action-comedy film from 1997 that follows the story of two secret agents who work for a covert government agency that monitors extraterrestrial activity on Earth. Will Smith and Tommy Lee Jones star as the unlikely duo who must stop a cockroach-like alien from destroying the planet while keeping their identities hidden from the public. Packed with humor, exciting action sequences, and memorable characters, \"Men in Black\" is a classic sci-fi adventure that will keep you entertained from start to finish.', 145, '1997-04-16 00:00:00', '/Movies/Men_In_Black', '/Movies/Men_In_Black/thumbnail.png'),
(6, 'Bullet Train', '\"Bullet Train\" is an upcoming action-thriller film that follows a group of deadly assassins who board a high-speed train from Tokyo to Morioka with conflicting missions. Starring Brad Pitt, Joey King, and Zazie Beetz, this pulse-pounding film is based on the Japanese novel \"Maria Beetle\" by Kotaro Isaka and promises to be a non-stop ride full of twists, turns, and surprises. With a talented cast and a suspenseful plot, \"Bullet Train\" is sure to keep audiences on the edge of their seats from start to finish.', 163, '2022-04-20 00:00:00', '/Movies/Bullet_Train', '/Movies/Bullet_Train/thumbnail.jpeg'),
(7, 'Kingsman: The Secret Service', 'Kingsman: A thrilling blend of espionage, action, and style. Join a dapper secret agent and his street-smart protégé as they defend the world from a sinister plot. Get ready for a high-octane, humor-filled roller coaster ride that redefines the spy genre!', 168, '2014-04-23 00:00:00', '/Movies/Kingsman', '/Movies/Kingsman/thumbnail.png'),
(11, 'Back to the Future ', '\"Back to the Future\" is a thrilling time-travel adventure where teenager Marty McFly accidentally journeys 30 years into the past. With the help of eccentric inventor Doc Brown and a souped-up DeLorean, Marty must fix the timeline, reunite his parents, and race against time to return to the future. Packed with humor, heart, and mind-bending twists, this iconic 1985 classic will take you on an unforgettable ride!', 103, '1985-07-03 00:00:00', '/Movies/Back_To_The_Future', '/Movies/Back_To_The_Future/thumbnail.png'),
(12, 'The Terminator ', '\"The Terminator\" is a pulse-pounding, sci-fi action thriller that takes you on a high-stakes chase through time. In a desperate bid to save humanity\'s future, a fierce and relentless cyborg assassin is sent back to 1984 to eliminate Sarah Connor, the mother of a future resistance leader. With the unstoppable Terminator hot on her heels, Sarah teams up with a battle-hardened soldier from the future to change the course of history. Gripping, intense, and groundbreaking, this 1984 classic will keep you on the edge of your seat!', 119, '1984-10-26 00:00:00', '/Movies/The_Terminator', '/Movies/The_Terminator/thumbnail.jpeg'),
(13, 'War Dogs', '\"War Dogs\" is a thrilling, darkly comedic ride that takes you deep into the high-stakes world of international arms dealing. Based on a true story, this 2016 film follows two childhood friends who exploit a government loophole to become unlikely weapons suppliers for the U.S. military. Embarking on a wild adventure filled with danger, greed, and deception, the duo soon finds themselves in over their heads as they navigate the murky waters of global conflict. With sharp wit, nail-biting tension, and unforgettable performances, \"War Dogs\" is an exhilarating journey you won\'t want to miss!', 141, '2016-08-19 00:00:00', '/Movies/War_Dogs', '/Movies/War_Dogs/thumbnail.jpeg');

-- --------------------------------------------------------

--
-- Structure de la table `VideoViewings`
--

CREATE TABLE `VideoViewings` (
  `viewingsId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL,
  `videosStartTime` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `VideoViewings`
--

INSERT INTO `VideoViewings` (`viewingsId`, `usersId`, `videosId`, `videosStartTime`) VALUES
(6, 0, 6, 190),
(7, 0, 11, 0),
(8, 0, 13, 0),
(9, 0, 12, 1228);

-- --------------------------------------------------------

--
-- Structure de la table `ViewMarkings`
--

CREATE TABLE `ViewMarkings` (
  `viewMarkingsId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `ViewMarkings`
--

INSERT INTO `ViewMarkings` (`viewMarkingsId`, `usersId`, `videosId`) VALUES
(5, 0, 6),
(6, 0, 11),
(7, 0, 13);

-- --------------------------------------------------------

--
-- Structure de la table `WatchLists`
--

CREATE TABLE `WatchLists` (
  `watchLatersId` int(11) NOT NULL,
  `usersId` int(11) NOT NULL,
  `videosId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `WatchLists`
--

INSERT INTO `WatchLists` (`watchLatersId`, `usersId`, `videosId`) VALUES
(40, 0, 3),
(41, 0, 6);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `AccountSelections`
--
ALTER TABLE `AccountSelections`
  ADD PRIMARY KEY (`accountSelectionsId`);

--
-- Index pour la table `Categories`
--
ALTER TABLE `Categories`
  ADD PRIMARY KEY (`categoriesId`);

--
-- Index pour la table `Comments`
--
ALTER TABLE `Comments`
  ADD PRIMARY KEY (`commentsId`);

--
-- Index pour la table `Dislikes`
--
ALTER TABLE `Dislikes`
  ADD PRIMARY KEY (`dislikesId`);

--
-- Index pour la table `Likes`
--
ALTER TABLE `Likes`
  ADD PRIMARY KEY (`likesId`);

--
-- Index pour la table `Replies`
--
ALTER TABLE `Replies`
  ADD PRIMARY KEY (`repiesId`);

--
-- Index pour la table `Subscriptions`
--
ALTER TABLE `Subscriptions`
  ADD PRIMARY KEY (`subscriptionsId`);

--
-- Index pour la table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`usersId`),
  ADD KEY `usersUID` (`usersUID`);

--
-- Index pour la table `VideoCategorizations`
--
ALTER TABLE `VideoCategorizations`
  ADD PRIMARY KEY (`categorizationsId`);

--
-- Index pour la table `Videos`
--
ALTER TABLE `Videos`
  ADD PRIMARY KEY (`videosId`);

--
-- Index pour la table `VideoViewings`
--
ALTER TABLE `VideoViewings`
  ADD PRIMARY KEY (`viewingsId`);

--
-- Index pour la table `ViewMarkings`
--
ALTER TABLE `ViewMarkings`
  ADD PRIMARY KEY (`viewMarkingsId`);

--
-- Index pour la table `WatchLists`
--
ALTER TABLE `WatchLists`
  ADD PRIMARY KEY (`watchLatersId`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `AccountSelections`
--
ALTER TABLE `AccountSelections`
  MODIFY `accountSelectionsId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Categories`
--
ALTER TABLE `Categories`
  MODIFY `categoriesId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `Comments`
--
ALTER TABLE `Comments`
  MODIFY `commentsId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Dislikes`
--
ALTER TABLE `Dislikes`
  MODIFY `dislikesId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Likes`
--
ALTER TABLE `Likes`
  MODIFY `likesId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Replies`
--
ALTER TABLE `Replies`
  MODIFY `repiesId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Subscriptions`
--
ALTER TABLE `Subscriptions`
  MODIFY `subscriptionsId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Users`
--
ALTER TABLE `Users`
  MODIFY `usersId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `VideoCategorizations`
--
ALTER TABLE `VideoCategorizations`
  MODIFY `categorizationsId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT pour la table `Videos`
--
ALTER TABLE `Videos`
  MODIFY `videosId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `VideoViewings`
--
ALTER TABLE `VideoViewings`
  MODIFY `viewingsId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `ViewMarkings`
--
ALTER TABLE `ViewMarkings`
  MODIFY `viewMarkingsId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `WatchLists`
--
ALTER TABLE `WatchLists`
  MODIFY `watchLatersId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
