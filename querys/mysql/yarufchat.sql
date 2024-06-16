DROP DATABASE IF EXISTS yarufzap;
CREATE DATABASE IF NOT EXISTS yarufzap;
USE yarufzap;

CREATE TABLE IF NOT EXISTS TB_USER(
	email VARCHAR(75) PRIMARY KEY,
    login_name VARCHAR(50),
    tag VARCHAR(10) DEFAULT 'BR1',
    nickname VARCHAR(50),
    user_status VARCHAR(300) DEFAULT "hey there! I'm using yarufchat",
    user_password VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS TB_INVITE(
	invite_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    status_invite ENUM('accepted','declined','pending') NULL DEFAULT 'pending',
    invite_date DATETIME NULL DEFAULT NOW(),
    user_id_inviting VARCHAR(75),
    user_id_invited VARCHAR(75),
    FOREIGN KEY (user_id_inviting) REFERENCES TB_USER(email),
    FOREIGN KEY (user_id_invited) REFERENCES TB_USER(email)
);

CREATE TABLE IF NOT EXISTS TB_CHAT(
	chat_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    chat_name VARCHAR(100) NULL,
    chat_type ENUM ('contact','group') NOT NULL,
    chat_desc VARCHAR(2048) NULL,
    chat_crtn_date DATETIME NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS TB_CHAT_MEMBER(
	user_id VARCHAR(75),
    chat_id BIGINT,
    permissions ENUM ('member','admin') DEFAULT 'member',
    join_date DATETIME DEFAULT NOW(),
    leave_date DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES TB_USER(email),
    FOREIGN KEY (chat_id) REFERENCES TB_CHAT(chat_id)
);

CREATE TABLE IF NOT EXISTS TB_MESSAGE(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    message_datetime DATETIME NOT NULL DEFAULT NOW(),
    reply_message_id BIGINT NULL,
    message_value VARCHAR(50.000) NOT NULL,
    user_id VARCHAR(75),
    chat_id BIGINT,
    FOREIGN KEY (reply_message_id) REFERENCES TB_MESSAGE(id),
    FOREIGN KEY (user_id) REFERENCES TB_USER(email),
    FOREIGN KEY (chat_id) REFERENCES TB_CHAT(chat_id)
);
