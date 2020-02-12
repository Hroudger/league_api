SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE league_api;

CREATE USER 'league_api'@'%' IDENTIFIED WITH mysql_native_password AS '***'; GRANT USAGE ON *.* TO 'league_api'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0; GRANT ALL PRIVILEGES ON `league_api`.* TO 'league_api'@'%';

USE league_api;

CREATE TABLE `ranking` (
  `summonerid` char(64) NOT NULL,
  `solotier` char(16) DEFAULT NULL,
  `solorank` char(8) DEFAULT NULL,
  `sololp` int(4) DEFAULT NULL,
  `flextier` char(16) DEFAULT NULL,
  `flexrank` char(8) DEFAULT NULL,
  `flexlp` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `summonermatches` (
  `matchid` char(64) NOT NULL DEFAULT '',
  `region` char(4) NOT NULL DEFAULT '',
  `summonerid` char(64) NOT NULL DEFAULT '',
  `championid` int(4) DEFAULT NULL,
  `queueid` int(8) DEFAULT NULL,
  `timestamp` bigint(128) DEFAULT NULL,
  `win` tinyint(1) DEFAULT NULL,
  `duration` int(64) DEFAULT NULL,
  `cs` int(10) DEFAULT NULL,
  `kills` int(4) DEFAULT NULL,
  `deaths` int(4) DEFAULT NULL,
  `assists` int(4) DEFAULT NULL,
  `level` int(4) DEFAULT NULL,
  `visionscore` int(16) DEFAULT NULL,
  `spell1` int(5) DEFAULT NULL,
  `spell2` int(5) DEFAULT NULL,
  `item0` int(11) DEFAULT NULL,
  `item1` int(11) DEFAULT NULL,
  `item2` int(11) DEFAULT NULL,
  `item3` int(11) DEFAULT NULL,
  `item4` int(11) DEFAULT NULL,
  `item5` int(11) DEFAULT NULL,
  `item6` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `summoners` (
  `id` char(64) NOT NULL,
  `REGION` char(4) NOT NULL DEFAULT '',
  `NAME` char(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `ranking`
  ADD KEY `elo` (`summonerid`);

ALTER TABLE `summonermatches`
  ADD PRIMARY KEY (`summonerid`,`matchid`),
  ADD KEY `matchdetails - matchid` (`matchid`);

ALTER TABLE `summoners`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `ranking`
  ADD CONSTRAINT `ranking_ibfk_1` FOREIGN KEY (`summonerid`) REFERENCES `summoners` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `summonermatches`
  ADD CONSTRAINT `summoners - matches` FOREIGN KEY (`summonerid`) REFERENCES `summoners` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
