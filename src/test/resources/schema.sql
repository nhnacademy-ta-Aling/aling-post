DROP TABLE IF EXISTS `normal_post`;
DROP TABLE IF EXISTS `post`;

CREATE TABLE `post`
(
    `post_no`            bigint   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `post_content`       nvarchar(10000) NOT NULL,
    `create_at`          datetime NOT NULL,
    `post_modify_at`     datetime NULL,
    `post_is_delete`     tinyint NOT NULL DEFAULT 0,
    `post_delete_reason` nvarchar(30) NULL,
    `post_is_open`       tinyint NOT NULL DEFAULT 1
);

CREATE TABLE `normal_post`
(
    `post_no` bigint NOT NULL,
    `normal_user_no` bigint NOT NULL
);

ALTER TABLE `normal_post`
    ADD CONSTRAINT `FK_post_TO_normal_post_1` FOREIGN KEY (`post_no`)REFERENCES `post` (`post_no`);