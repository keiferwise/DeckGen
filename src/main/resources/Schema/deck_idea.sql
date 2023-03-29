use deckgendb;

drop table deck_idea;

CREATE TABLE `deck_idea` (
  `deck_idea_id` varchar(36) NOT NULL,
  `legends` varchar(255) DEFAULT NULL,
  `theme` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `black` boolean NOT NULL,
  `blue` boolean NOT NULL,
  `green` boolean NOT NULL,
  `red` boolean NOT NULL,
  `white` boolean NOT NULL,
  `deck_id` varchar(36),
  PRIMARY KEY (`deck_idea_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
