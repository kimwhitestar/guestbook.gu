show tables;
create table guest (
	idx 	int not null auto_increment primary key,
	name 	varchar(20) not null,
	email 	varchar(60),
	homepage varchar(60),
	vDate	datetime default now(),
	hostIp	varchar(50) not null,
	content text not null
);
desc guest;
insert into guest values (default, '관리자', 'aaa@a.kr', 'cafe.daum.javaspringframework', default, '192.168.0.80', '방명록 서비스 시작');
insert into guest values (default, '홍길동', 'hkd@x.f', 'hompage://none', default, '192.168.0.18', '내용없음');
insert into guest values (default, '김말숙', 'kms@b.b.b.kr', 'bbq.chicken.kr', default, '192.168.0.120', '방명록 접속');
