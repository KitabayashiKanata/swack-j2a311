insert into users values('U0001', '佐藤明美', 'akemi@swack.com', 'swack0001');
insert into users values('U0002', '伊藤裕太', 'yuta@swack.com', 'swack0002');
insert into users values('U0003', '高橋真由子', 'mayuko@swack.com', 'swack0003');
insert into users values('U0004', '田中一郎', 'itiro@swack.com', 'swack0004');
insert into users values('U0005', '渡辺美咲', 'misaki@swack.com', 'swack0005');

insert into workspace values('W0001', 'hcs-j2', 'U0001');
insert into workspace values('W0002', 'hcs-s2', 'U0001');

insert into rooms values('R0001', 'everyone', 'U0001', false, false, 'W0001');
insert into rooms values('R0002', 'techTalks', 'U0001', false, false, 'W0001');
insert into rooms values('R0003', 'coffeeBreak', 'U0001', false, false, 'W0001');
insert into rooms values('R0004', 'bookLovers', 'U0001', false, false, 'W0001');
insert into rooms values('R0005', 'fitnessFreaks', 'U0001', false, false, 'W0001');
insert into rooms values('R0006', 'gameZone', 'U0001', false, false, 'W0001');
insert into rooms values('R0007', 'PU0001,U0002', 'U0001', true, true, 'W0001');
insert into rooms values('R0008', 'PU0001,U0003', 'U0001', true, true, 'W0001');

INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0001', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0001', 'U0002');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0001', 'U0003');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0001', 'U0004');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0001', 'U0005');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0002', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0002', 'U0002');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0002', 'U0004');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0003', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0003', 'U0002');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0004', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0004', 'U0003');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0004', 'U0004');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0004', 'U0005');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0005', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0005', 'U0004');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0005', 'U0005');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0006', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0006', 'U0002');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES ('R0006', 'U0005');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES('R0007', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES('R0007', 'U0002');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES('R0008', 'U0001');
INSERT INTO JOINROOM (ROOMID, USERID)
	VALUES('R0008', 'U0003');

INSERT INTO JOINWORKSPACE VALUES('W0001','U0001');
INSERT INTO JOINWORKSPACE VALUES('W0002','U0001');
INSERT INTO JOINWORKSPACE VALUES('W0001','U0002');
INSERT INTO JOINWORKSPACE VALUES('W0002','U0002');
INSERT INTO JOINWORKSPACE VALUES('W0001','U0003');
INSERT INTO JOINWORKSPACE VALUES('W0002','U0003');
INSERT INTO JOINWORKSPACE VALUES('W0001','U0004');
INSERT INTO JOINWORKSPACE VALUES('W0002','U0004');
INSERT INTO JOINWORKSPACE VALUES('W0001','U0005');
INSERT INTO JOINWORKSPACE VALUES('W0002','U0005');

INSERT INTO WORKSPACEADMIN VALUES('W0001','U0001');
INSERT INTO WORKSPACEADMIN VALUES('W0002','U0001');

INSERT INTO ROOMADMIN VALUES('R0001','U0001');
INSERT INTO ROOMADMIN VALUES('R0002','U0001');
INSERT INTO ROOMADMIN VALUES('R0003','U0001');
INSERT INTO ROOMADMIN VALUES('R0004','U0001');
INSERT INTO ROOMADMIN VALUES('R0005','U0001');

insert into users values('ADMIN', '管理用アカウント', 'administrator@swack.com', 'admin');

insert into chatlog values(1, 'R0001', 'U0001', 'ここでは学年連絡を行います。確認を忘れないようお願いします。', '2023-11-29 10:30:16');
insert into chatlog values(2, 'R0002', 'U0001', 'ここでは技術的な話題や最新のITトレンドについて意見を交換し合いましょう！', '2023-11-29 10:30:16');
insert into chatlog values(3, 'R0003', 'U0001', 'カジュアルな雑談や日常の出来事について話し、交流を楽しんでください！', '2023-11-29 10:30:16');
insert into chatlog values(4, 'R0004', 'U0001', '本や文学に関するディスカッションを行うためのルームです。', '2023-11-29 10:30:16');
insert into chatlog values(5, 'R0005', 'U0001', 'フィットネスや健康に関する情報交換を行うためのルームです！', '2023-11-29 10:30:16');
insert into chatlog values(6, 'R0006', 'U0001', 'ゲームに関する話題、新作情報、プレイのコツなどを交換し合い、実力を上げましょう！', '2023-11-29 10:30:16');