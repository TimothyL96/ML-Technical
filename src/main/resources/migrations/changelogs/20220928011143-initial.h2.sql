-- liquibase formatted sql

-- changeset CasemTimothyLam:1664298705790-1
CREATE TABLE feature (id INT UNSIGNED NOT NULL AUTO_INCREMENT, name VARCHAR(255) DEFAULT '0' NOT NULL, CONSTRAINT PK_FEATURE PRIMARY KEY (id), UNIQUE (name));

-- changeset CasemTimothyLam:1664298705790-2
CREATE TABLE user (id INT UNSIGNED NOT NULL AUTO_INCREMENT, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, date_created DATETIME NOT NULL, CONSTRAINT PK_USER PRIMARY KEY (id), UNIQUE (email));

-- changeset CasemTimothyLam:1664298705790-3
CREATE TABLE user_feature (user_id INT UNSIGNED, feature_id INT UNSIGNED);

-- changeset CasemTimothyLam:1664298705790-4
ALTER TABLE user_feature ADD CONSTRAINT user_id_feature_id UNIQUE (user_id, feature_id);

-- changeset CasemTimothyLam:1664298705790-5
CREATE INDEX FK_user_feature_feature ON user_feature(feature_id);

-- changeset CasemTimothyLam:1664298705790-6
ALTER TABLE user_feature ADD CONSTRAINT FK_user_feature_feature FOREIGN KEY (feature_id) REFERENCES feature (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset CasemTimothyLam:1664298705790-7
ALTER TABLE user_feature ADD CONSTRAINT FK_user_feature_user FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE CASCADE;

