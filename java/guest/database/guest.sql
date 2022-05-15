show tables;
create table guest (
	idx 	int not null auto_increment primary key, /*방명록 번호(pk)*/
	name 	varchar(20) not null,	/*방문자 이름*/
	email 	varchar(60),			/*방문자 이메일*/
	homepage varchar(60),			/*방문자 홈페이지*/
	vDate	datetime default now(), /*방문일(작성일)*/
	hostIp	varchar(50) not null,	/*방문자 ip*/
	content text not null			/*방명록 내용*/
);
desc guest;
insert into guest values (default, '관리자', 'aaa@a.kr', 'cafe.daum.javaspringframework', default, '192.168.0.80', '방명록 서비스 시작');
insert into guest values (default, '홍길동', 'hkd@x.f', 'hompage://none', default, '192.168.0.18', '내용없음');
insert into guest values (default, '김말숙', 'kms@b.b.b.kr', 'bbq.chicken.kr', default, '192.168.0.120', '방명록 접속');
