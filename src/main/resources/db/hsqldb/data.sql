-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(id,username,password,enabled) VALUES (1,'admin1','4dm1n',TRUE);
INSERT INTO authorities(id,user_id,authority) VALUES (1,1,'admin');

INSERT INTO game_types(id,name) VALUES (1, 'PARCHIS');
INSERT INTO game_types(id,name) VALUES (2, 'OCA');

INSERT INTO achievements(id,name,description,threshold,badge_image) VALUES (1,'Viciado','Si juegas <THRESHOLD> partidas o más, consideramos que ya estás enganchado.',10.0,'https://bit.ly/certifiedGamer');
INSERT INTO achievements(id,name,description,threshold,badge_image) VALUES (2,'Triunfador','Si ganas <THRESHOLD> o  más partidas es que eres todo un triunfador.',20.0,'https://bit.ly/proGamer');

INSERT INTO users(id,username,password,enabled) VALUES (2,'pajaro','1234',TRUE);
INSERT INTO players(id,email,first_name,last_name,user_id) VALUES (2,'javi@gmail.com','Javier','Ruiz',2);
INSERT INTO authorities(id,user_id,authority) VALUES (5,2,'player');

INSERT INTO users(id,username,password,enabled) VALUES (3,'alvaro1','1234',TRUE);
INSERT INTO players(id,email,first_name,last_name,user_id) VALUES (1,'alvaro@gmail.com','Alvaro','Carrera',3);
INSERT INTO authorities(id,user_id,authority) VALUES (4,3,'player');

INSERT INTO users(id,username,password,enabled) VALUES (4,'TheKroth','1234',TRUE);
INSERT INTO players(id,email,first_name,last_name,user_id) VALUES (3,'thekroth@gmail.com','John','Rodriguez',4);
INSERT INTO authorities(id,user_id,authority) VALUES (6,4,'player');

INSERT INTO users(id,username,password,enabled) VALUES (5,'TheGrefg','1234',TRUE);
INSERT INTO players(id,email,first_name,last_name,user_id) VALUES (4,'thegrefg@gmail.com','David','Canovas',5);
INSERT INTO authorities(id,user_id,authority) VALUES (7,5,'player');

INSERT INTO players_achievements(player_id,achievements_id) VALUES (1,1);
INSERT INTO players_achievements(player_id,achievements_id) VALUES (1,2);
INSERT INTO players_achievements(player_id,achievements_id) VALUES (2,2);

INSERT INTO players_friends(player_id,friends_id) VALUES (1,2);
INSERT INTO players_friends(player_id,friends_id) VALUES (3,2);

INSERT INTO games(id,creator_id,winner_id,name,jugadores,code,privacity,game_type_id, in_progress) VALUES (1,1,1,'prueba',4,'ASDFG','PUBLIC',1,FALSE);
INSERT INTO games(id,creator_id,winner_id,name,jugadores,code,privacity,game_type_id, in_progress) VALUES (2,2,2,'prueba',4,'FMNJF','PRIVATE',1,FALSE);
INSERT INTO games(id,creator_id,name,code,privacity,game_type_id, in_progress,started) VALUES (3,2,'partida vacia parchis','JDSKL','PUBLIC',2,TRUE,FALSE);
INSERT INTO games(id,creator_id,name,code,privacity,game_type_id, in_progress,started) VALUES (4,2,'partida vacia oca','KFLSO','PRIVATE',2,TRUE,FALSE);


INSERT INTO games_players (game_id, players_id) VALUES (1,1);
INSERT INTO games_players (game_id, players_id) VALUES (1,2);
INSERT INTO games_players (game_id, players_id) VALUES (2,2);
INSERT INTO games_players (game_id, players_id) VALUES (3,2);


INSERT INTO oca_boards (id, background, height, width) VALUES (1,'resources/images/tablero-oca.png', 800,800);


INSERT INTO parchis_boards (id,background,height,width) VALUES (1,'resources/images/ParchisBoard.png',800,800);

INSERT INTO oca_pieces(id,colour,x_position,y_position,oca_board_id) VALUES (1,'RED',1,7,1);

INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (1,'RED',0,0,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (2,'RED',0,1,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (3,'RED',1,0,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (4,'RED',1,1,1);

INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (5,'BLUE',7,0,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (6,'BLUE',7,1,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (7,'BLUE',6,0,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (8,'BLUE',6,1,1);

INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (9,'GREEN',0,6,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (10,'GREEN',0,7,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (11,'GREEN',1,6,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (12,'GREEN',1,7,1);

INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (13,'YELLOW',7,6,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (14,'YELLOW',7,7,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (15,'YELLOW',6,6,1);
INSERT INTO parchis_pieces(id,colour,x_position,y_position,parchis_board_id) VALUES (16,'YELLOW',6,7,1);

