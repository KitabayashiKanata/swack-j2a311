-- workspaceで必要なテーブルを作成するSQL
CREATE TABLE WORKSPACE(
  WORKSPACEID CHAR(5) PRIMARY KEY,
  WORKSPACENAME VARCHAR(40) NOT NULL,
  CREATEDUSERID VARCHAR(5)NOT NULL
);
CREATE TABLE JOINWORKSPACE(
  WORKSPACEID VARCHAR(5),
  USERID CHAR(5),
  PRIMARY KEY(WORKSPACEID, USERID)
);
-- workspaceテーブルのデータ
INSERT INTO WORKSPACE VALUES('W0001','J2A3','U0001');
INSERT INTO JOINWORKSPACE VALUES('W0001','U0001');
