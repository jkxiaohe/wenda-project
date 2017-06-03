DROP TABLE IF EXISTS `user`;
CREATE TABLE `wenda`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `salt` VARCHAR(32) NOT NULL,
  `head_url` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS `question`;
CREATE TABLE `wenda`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `created_date` DATETIME NULL,
  `comment_count` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
