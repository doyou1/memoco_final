drop sequence Recipe_SEQ;
drop table Recipe_content;
drop table Recipe_ingrd;
drop table Recipe_likes;
drop sequence Review_SEQ;
drop table Review_likes;
drop table Review_reply;
drop sequence Review_reply_SEQ;
drop table mongo_recipe_clone;
drop table Review;
drop table recipe;
drop table member;
-- Meber Table
create table Member
(
    Member_id varchar2(200) primary key,                
    Member_pw varchar2(50) Not null,                     
    Member_nickname varchar2(50) Not null,            
    Member_email varchar2(200) Not null,                
    Member_favorite varchar2(200)                           
);



create table Recipe
(
    Recipe_num number primary key,                      
    Member_id varchar2(200) not null,                     
    Recipe_title varchar2(200) not null,                     
    Recipe_indate date default sysdate,                    
    Recipe_hits number default 0,                            
    Recipe_likes number default 0,                           
     CONSTRAINTS Recipe_fk FOREIGN KEY (Member_id)
    REFERENCES Member(Member_id) on delete cascade
);

create sequence Recipe_SEQ;

create table Recipe_content
(
    content_num number not null,               
    Member_id varchar2(200) not null,          
    Recipe_num number not null,                
    Recipe_content varchar2(4000),             
    Recipe_image varchar2(500),                
     CONSTRAINTS Recipe_content_fk1 FOREIGN KEY (Recipe_num)
    REFERENCES Recipe(Recipe_num) on delete cascade
);


create table Recipe_ingrd
(
    Recipe_num number not null,                
    Member_id varchar2(200) not null,          
    ingrd_num number not null,                 
    ingrd_name varchar2(50) not null,          
    ingrd_amount varchar2(50) not null,        
    CONSTRAINTS Recipe_ingrd_fk1 FOREIGN KEY (Recipe_num)
    REFERENCES Recipe(Recipe_num) on delete cascade
);


create table Recipe_likes
(
    Recipe_num number not null,
    Member_id varchar2(200) not null
);

create table Review
(
    Review_num number primary key,                      --�����ȣ
    Member_id varchar2(200) not null,                      --�ۼ���ID
    Review_title varchar2(200) not null,                     --��������
    Review_content varchar2(2000) not null,              --���䳻��
    Review_image varchar2(500),                               --�����̹���(1��)
    Review_indate date default sysdate,                    --�����ϳ�¥
    Review_likes number default 0,
    recipe_num number default 0,
    recipe_name varchar2(200),
    recipe_photo varchar2(2000),
    CONSTRAINTS Review_fk FOREIGN KEY (Member_id)
    REFERENCES Member(Member_id) on delete cascade
);

create sequence Review_SEQ;

create table Review_likes
(
    Review_num number not null,                          
    Member_id varchar2(200) not null
);

create table Review_reply
(
    Reply_num number primary key,                   
    Review_num number not null,                      
    Member_id varchar2(200) not null,                
    Reply_content varchar2(500) not null,            
    Reply_indate date default sysdate,                
     CONSTRAINTS Review_reply_fk1 FOREIGN KEY (Review_num)
    REFERENCES Review(Review_num) on delete cascade
);

create sequence Review_reply_SEQ;

create table mongo_recipe_clone(
    recipe_num number primary key,
    recipe_hits number default 0,
    recipe_likes number default 0	
);

alter sequence recipe_seq increment by 10000; 
select recipe_seq.nextval from dual; 
alter sequence recipe_seq increment by 1;

insert into member(
    member_id,              
    Member_pw,                     
    Member_nickname,            
    Member_email,                
    Member_favorite    
)values(
    'test',
    'test',
    '테스트',
    'test@test.com',
    '제육볶음'
);

insert into member(
    member_id,              
    Member_pw,                     
    Member_nickname,            
    Member_email,                
    Member_favorite    
)values(
    'admin',
    'admin',
    '관리자',
    'admin@admin.com',
    '샐러드'
);

insert into Recipe(
    Recipe_num,                      
    Member_id,                     
    Recipe_title                                                
)values(
    recipe_seq.nextval,
    'test',
    '백종원 제육볶음 레시피 쉬워요'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    0,
    'test',
    recipe_seq.currval,
 '고기는 500g을 사용했고 야채들은 당근과 양파 그리고 고추를 넣었어요
원레시피에는 애호박과 양배추가 들어갔지만 저는 생략했습니다
추가해주셔도 좋아요!',
    'a1.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    1,
    'test',
    recipe_seq.currval,
       '들어갈 야채들은 먹기 좋은 크기로 잘라줍니다
당근은 먹기 좋은 크기로
대파도 큼직하게 썰어주고 파기름을 내기 위해서 잘게 썰어서 준비합니다
고추도 한개 잘라서 넣어주는데요 매콤한 것을 좋아한다면 청양고추를 넣어줘도 좋아요
양파도 역시 잘게 썰어줍니다',
    'a2.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    2,
    'test',
    recipe_seq.currval,
    '고기는 이렇게 먹기 좋은 크기로 듬성듬성 잘라서 준비합니다
저는 앞다리살을 사용했는데 기름기도 적당하니 좋아요.
고기는 뭉칠 수 있으니 커다란 볼에 이렇게 풀어서 준비합니다.
그래야지 양념들이 잘 베어서 맛있게 먹을 수 있어요',
    'a3.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    3,
    'test',
    recipe_seq.currval,
    '백종원 제육볶음 레시피 이제 재료가 준비되었으니 맛있게 만들어야죠?
커다란 궁중팬을 준비한 후 가장 먼저 고기부터 볶아줍니다
고기부터 볶으니 매번 요리를 할때마다 고기가 익었는지 안익었는지 잘 모르는데
고기를 이렇게 먼저 다 익히고 양념들을 넣으니 항상 고기가 안익었을까? 불안에 떨지 않고 만들 수 있어서 좋아요',
    'a4.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    4,
    'test',
    recipe_seq.currval,
    '고기가 다 익었다면 잘게 썰어준 대파를 넣어줍니다
잘게 썬 대파를 넣어줌으로써 파기름을 내주는데요
우와~ 냄새가 어찌나 좋던지 고기에서 나온 기름과 파와의 조화~ 정말 좋아요',
    'a5.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    5,
    'test',
    recipe_seq.currval,
    '백주부님의 팁은 바로 설탕을 넣는것

 설탕을 가장 먼저 1.5스푼 넣어줍니다
단맛을 가장 먼저 넣어야 고기에 맛이 잘 베인다고 해요
저도 그래서 이제는 볶음요리할때 설탕을 가장 먼저 넣어 고기에 코팅을 해준답니다',
    'a6.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    6,
    'test',
    recipe_seq.currval,
    '그 다음 간마늘 한 스푼을 넣어줍니다',
    'a7.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    7,
    'test',
    recipe_seq.currval,
    '그 다음 이제 매콤함을 살려줄 고춧가루를 넣어주는데요
고춧가루는 3스푼을 넣어주었어요 원레시피는 2스푼인데 개인적으로 3스푼이 딱 떨어지더라구요!!
그 다음 고추장 1스푼을 넣어줍니다',
    'a8.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                  
    Recipe_image
)values(
    8,
    'test',
    recipe_seq.currval,
    'a9.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    9,
    'test',
    recipe_seq.currval,
    '간을 해줄 것은 간장인데요 진간장을 3스푼 넣어줍니다',
    'a10.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    10,
    'test',
    recipe_seq.currval,
    '이렇게 양념들을 넣은 후 아까 준비해둔 야채들을 넣고 볶아줍니다
야채가 익으면 완성인데요',
    'a11.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    11,
    'test',
    recipe_seq.currval,
    '거의 다 익었다면 참기름 1스푼과 그리고 후추를 넣어줍니다
후추는 톡톡 3번만 넣어주면 좋아요!',
    'a12.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    12,
    'test',
    recipe_seq.currval,
    '드디어 완성~
복잡한것 같기도 하지만 한 번 만들면 절대 복잡하지 않고
누구나 쉽게 만들 수 있는 레시피인 것 같아요

맛도 정말 좋아요~~
고추장보다 고춧가루를 더 많이 넣어서 그런지 고추장의 텁텁한 맛이 없어서 맛있게 먹을 수 있는 것 같은데요
상추와 그리고 깻잎에 싸 먹으면 정말 맛있어요!',
    'a13.jpg'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    0,
    '돼지고기',
    '500g'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    1,
    '당근',
    '약간'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    2,
    '양파',
    '1개'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    3,
    '고추',
    '1개'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    4,
    '대파',
    '1대'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    5,
    '설탕',
    '1.5스푼'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    6,
    '고추가루',
    '3스푼'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    7,
    '간장',
    '3스푼'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    8,
    '간마늘',
    '한스푼'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    9,
    '고추장',
    '1스푼'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    10,
    '참기름',
    '1스푼'
);

insert into Recipe(
    Recipe_num,                      
    Member_id,                     
    Recipe_title                                                
)values(
    recipe_seq.nextval,
    'test',
    '로제크림치킨 l 귀차니즘이 폭팔하는 오늘같은 날! 편의점 재료로 떼우쟈!'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    0,
    'test',
    recipe_seq.currval,
 '편의점 치킨은 뼈를 발라내어 손으로 찢는다.',
    'b0.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    1,
    'test',
    recipe_seq.currval,
       '그릇에 우유, 토마토소스, 소금, 후추를 섞은 후 전자레인지에 2분간 조리하여 데운다.',
    'b1.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    2,
    'test',
    recipe_seq.currval,
    '데운 소스에 찢은 치킨, 방울토마토를 넣고 섞는다.',
    'b2.jpg'
);

insert into Recipe_content
(
    content_num,               
    Member_id,          
    Recipe_num,                
    Recipe_content,             
    Recipe_image
)values(
    3,
    'test',
    recipe_seq.currval,
    '모짜렐라치즈를 뿌린 후 전자레인지에 3분간 조리하여 완성한다.
바게트빵이나 나쵸를 곁들여 드세요.',
    'b3.jpg'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    0,
    '편의점 치킨',
    '2개'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    1,
    '토마토소스',
    '1팩'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    2,
    '우유',
    '1종이컵'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    3,
    '방울토마토',
    '5개'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    4,
    '모짜렐라치즈',
    '적당량'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    5,
    '소금',
    '약간'
);

insert into Recipe_ingrd
(
    Recipe_num ,                
    Member_id ,          
    ingrd_num ,                 
    ingrd_name ,          
    ingrd_amount
)values(
    recipe_seq.currval,
    'test',
    6,
    '후추',
    '약간'
);

insert into Review
(
    Review_num,
    Member_id, 
    Review_title,
    Review_content,
    Review_image,
    recipe_num
)values(
    review_seq.nextval,
    'admin',
    '정말 맛있네요!',
    '레시피 보자마자 편의점가서 치킨 사왔네요ㅎㅎ밥대용으로도 좋고
    술안주로도 짱일거같아요!! 잘먹었습니다~',
    'c1.jpg',
    recipe_seq.currval
);

insert into Review_reply
(
    Reply_num ,                   
    Review_num ,                      
    Member_id ,                
    Reply_content
)values(
    review_reply_seq.nextval,
    review_seq.currval,
    'test',
    '맛있게 드셨다니 정말 기분이 좋습니다! 감사합니다~'
);

insert into Review
(
    Review_num,
    Member_id, 
    Review_title,
    Review_content,
    recipe_num
)values(
    review_seq.nextval,
    'admin',
    '제육도 맛있네요!',
    '역시 백종원 레시피네요. 보기 좋게 설명해주신 선생님도 감사합니다~',
    10001
);

commit;