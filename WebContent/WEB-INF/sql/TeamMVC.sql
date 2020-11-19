-- 김민아 시작 --

show user;

create user semi_user IDENTIFIED BY cclass;

grant connect, RESOURCE, create view, UNLIMITED TABLESPACE to semi_user;

SELECT * FROM ALL_TABLES where owner = 'SEMI_USER';

select * 
from TBL_MAIN_IMAGE;

select * 
from TBL_CAROUSEL_IMAGE;

select *
from TBL_PRODUCT;

select *
from TBL_CATEGORY;

select *
from TBL_PRODUCT_INFO;


insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 1, 'long handmade coat', 1, '1_1.PNG', '1_2.PNG', 100, 200000, 200000, 'long handmade coat 롱 핸드메이드 코트', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 2, 'H trench coat', 1, '2_1.PNG', '2_2.PNG', 100, 200000, 200000, 'H trench coat 초겨울까지든든하게 :) 탄탄트렌치', 100, 'cotton', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 3, 'BEFORE TRENCH COAT', 1, '3_1.PNG', '3_2.PNG', 100, 200000, 200000, 'BEFORE TRENCH COAT', 100, 'cotton', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 4, 'Powder pur short coat', 1, '4_1.PNG', '4_2.PNG', 100, 200000, 200000, 'Powder pur short coat 파우더 퍼 숏 코트', 100, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 5, 'lemer jacket', 1, '5_1.PNG', '5_2.PNG', 100, 200000, 200000, 'lemer jacket', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 6, 'The Note Jacket', 2, '6_1.PNG', '6_2.PNG', 100, 200000, 200000, 'The Note Jacket', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 7, 'lowe check jacket', 2, '7_1.PNG', '7_2.PNG', 100, 200000, 200000, 'lowe check jacket', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 8, 'praha short jacket', 2, '8_1.PNG', '8_2.PNG', 100, 200000, 200000, 'praha short jacket', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 9, 'classic corduroy jumper', 3, '9_1.PNG', '9_2.PNG', 100, 200000, 200000, 'classic corduroy jumper', 100, 'cotton', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 10, 'pastel padding jumper ', 3, '10_1.PNG', '10_2.PNG', 100, 200000, 200000, 'pastel padding jumper ', 100, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 11, 'onion quiltin jumper', 3, '11_1.PNG', '11_2.PNG', 100, 200000, 200000, 'onion quiltin jumper', 100, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 12, 'my favorite jumper', 3, '12_1.PNG', '12_2.PNG', 100, 200000, 200000, 'my favorite jumper', 100, 'cotton', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 13, 'meishu mustang', 4, '13_1.PNG', '13_2.PNG', 100, 200000, 200000, 'meishu mustang 메이슈 무스탕', 100, 'leather', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 14, 'Creans mustang', 4, '14_1.PNG', '14_2.PNG', 100, 200000, 200000, 'Creans mustang', 100, 'leather', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 15, 'winter mink pur mustang', 4, '15_1.PNG', '15_2.PNG', 100, 200000, 200000, 'winter mink pur mustang', 100, 'leather', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 16, 'mush double mustang', 4, '16_1.PNG', '16_2.PNG', 100, 200000, 200000, 'mush double mustang', 100, 'leather', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 17, 'From lambswool cardigan', 5, '17_1.PNG', '17_2.PNG', 100, 200000, 200000, 'From lambswool cardigan 프롬 울 가디건', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 18, 'Midnight long knit cardigan', 5, '18_1.PNG', '18_2.PNG', 100, 200000, 200000, 'Midnight long knit cardigan 미드나잇 롱 니트 가디건', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 19, 'Angora Twist Cardigan', 5, '19_1.PNG', '19_2.PNG', 100, 200000, 200000, 'Angora Twist Cardigan 앙고라 꽈배기 가디건', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 20, 'Argyle loosefit knit cardigan', 5, '20_1.PNG', '20_2.PNG', 100, 200000, 200000, 'Argyle loosefit knit cardigan 아가일 루즈핏 니트 가디건', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 21, '캐시미어 핸드메이드 코트', 1, '21_1.jpg', '21_2.jpg', 100, 200000, 200000, '전 라인 하이브랜드 공정의 고도의 숙련된 분들이 1cm의 4땀수 이상을 준수하며 , 명품 수준의 퀄리티로 제작됩니다. 
텍스처부터 컬러감까지 깐깐하게 초이스된 
최고급 품질과 공정으로 만든 울,캐시 소재만을 사용하며 
디자인부터 퀄리티까지 명품 부럽지 않은 소장 가치를 자랑합니다.', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 22, '울앙고라 핸드메이드 자켓', 2, '22_1.jpg', '22_2.jpg', 100, 200000, 200000, '우아하고 화사하면서도 고급스럽고 세련된 컬러와 헤링본 패턴이 더해진 울90% 앙고라10% 퓨어한 프리미엄 소재의 핸드메이드 자켓. 리브 헤링울 자켓을 소개해드립니다.', 100, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 23, '롱타입 세련 버튼 패딩', 3, '23_1.jpg', '23_2.jpg', 100, 200000, 200000, '퀼팅부터 전체적인 디자인과 퀄리티가 훌륭하며 
우아한듯 세련된 고급스러운 아방한 오버핏으로 
어떤 명품 옷이 부럽지 않은 세련되고 멋진 무드를 완성해주는 
따뜻하고 가벼운 3온스 패딩의 고퀄리티 퀼팅 코트 입니다.', 100, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 24, '루즈핏 박시 가죽 라이더 자켓', 2, '24_1.jpg', '24_2.jpg', 100, 200000, 200000, '시크하고 멋스러운 매력의 박시 라이더자켓 소개해드려요:)', 100, 'leather', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 25, '목도리 세트 따뜻 패딩', 3, '25_1.jpg', '25_2.jpg', 50, 300000, 270000, '목도리 세트로 따뜻하고 세련된 롱기장의 패딩입니다.', 3000, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 26, '또또 포켓후드롱패딩', 3, '26_1.jpg', '26_2.jpg', 50, 300000, 270000, '캐주얼하게 입기 좋은 롱패딩 무릎까지 오는 롱한 기장감 
으로 따뜻하게 착용할 수 있는데요, 목 끝까지 올라오는 지퍼 디테일로 완전히 바람을 막아줘요.', 3000, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 27, '허그 윈터포켓롱가디건', 5, '27_1.jpg', '27_2.jpg', 50, 150000, 135000, '무릎까지 오는 기장감으로 체형커버는 물론 여리한 분위기를 연출해주는데요, 포켓 디테일로 실용성을 높였습니다. 
가볍게 걸쳐도 멋스러운 제품이에요.', 1500, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 28, '라이닝 양털무스탕', 4, '28_1.jpg', '28_2.jpg', 50, 500000, 450000, '캐주얼한 무드가 느껴지고, 하나만 입어도 스타일리시한 무스탕이에요. 
양털 소재라 부드럽고 따뜻하면서 라인 배색이 포인트 되어 더욱 멋스러워요.', 5000, 'leather', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 29, '케이프 커버핸드메이드코트', 1, '29_1.jpg', '29_2.jpg', 50, 300000, 270000, '클래식한 핸드메이드 롱코트. 울 100%로 보온성을 높여줬는데요, 
적당한 두께감으로 추워지는 날씨에 입기좋은 제품입니다. 
시크한 블랙 컬러 준비했어요. 
놓치지말고 초이스하세요! ', 3000, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 30, '오버핏 야상점퍼', 3, '30_1.jpg', '30_2.jpg', 50, 300000, 270000, '이런 야상 점퍼는 오버핏으로 멋스럽게 입어야 스타일을 살릴 수 있어요. 오래도록 입기 좋은 톡톡한 소재감과 완성도 높은 퀄리티로 만나보세요.', 3000, 'cotton', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 31, '롱 야상스타일 점퍼', 3, '31_1.jpg', '31_2.jpg', 50, 300000, 270000, '시크하면서도 스타일리시한 롱 야상스타일 점퍼입니다.', 3000, 'polyester', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 32, '다이아 패턴진주가디건', 5, '32_1.jpg', '32_2.jpg', 50, 150000, 135000, '클래식한 패턴으로 세련된 분위기 연출하는 다이아패턴 진주가디건입니다.', 1500, 'wool', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 33, '촉감좋은 싱글트렌치코트', 1, '33_1.jpg', '33_2.jpg', 50, 300000, 270000, '하나만 입어도 캐주얼하고, 스타일리시한 코디를 완성시켜줄 싱글 트렌치코트에요. 
싱글 카라 디자인으로 멋스럽게 코디되어 더욱 좋고, 살짝 박시한 느낌으로 더 트렌디하면서 스타일리시한 핏을 연출할 수 있어요.', 3000, 'cotton', 2 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 34, '댄디 더블 코트', 1, '34_1.jpg', '34_2.jpg', 50, 300000, 270000, '댄디한 스타일의 다양한 스타일로 입기 좋은 더블코트 입니다.', 3000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 35, '글리그 더블 코트', 1, '35_1.jpg', '35_2.jpg', 50, 300000, 270000, '더블버튼으로 클래식한 무드가 있고 포근함을 주는 모직으로 따뜻하게 입기 좋은 더블코트 입니다.', 3000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 36, '트렌디한 오버핏의 깔끔한 가디건', 5, '36_1.jpg', '36_2.jpg', 50, 200000, 180000, 'F/W 시즌 간절기에 꼭 필요한 아이템인 가디건으로 트렌디한 오버핏감의 활용도 높은 매력적인 아이템을 소개드립니다.', 2000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 37, '댄디한 스웨이드 자켓스타일 무스탕', 4, '37_1.jpg', '37_2.jpg', 50, 500000, 450000, '깔끔한 디자인에 소매끝 시보리로 디테일을 살리고 오랫동안 착용이 가능하면서 활용도 또한 높습니다.', 5000, 'leather', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 38, '머스탱 롱 무스탕', 4, '38_1.jpg', '38_2.jpg', 50, 600000, 540000, '원단부터 부자재까지 신경써서 제작된 프리미엄 롱 무스탕으로 기본 디자인으로 양털 안감과 꼼꼼한 봉제로 퀄리티 있게 제작되었습니다.', 6000, 'leather', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 39, '카센즈 오버 자켓', 2, '39_1.jpg', '39_2.jpg', 50, 300000, 270000, '고급스러운 느낌을 줄 수 있으며 예쁜 핏감으로 니트나 셔츠 어느 코디에든 잘 어울리는 자켓입니다.', 3000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 40, '데일리 블링 오버숄 가디건', 5, '40_1.jpg', '40_2.jpg', 50, 200000, 180000, '오버숄 가디건입니다. 오버하게 떨어지는 핏으로 슬랙스와 매치하기 좋습니다.', 2000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 41, 'Classic OverFit Coat', 1, '41_1.jpg', '41_2.jpg', 80, 400000, 360000, 'Classic OverFit Coat', 4000, 'cotton', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 42, 'Time Blen Single Coat', 1, '42_1.jpg', '42_2.jpg', 80, 400000, 360000, 'Time Blen Single Coat', 4000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 43, 'field Jacket', 2, '43_1.jpg', '43_2.jpg', 80, 400000, 360000, '봄/가을시즌 편안하면서도 멋스럽게 착용하기 좋은 오버핏 숏야상 !!', 4000, 'cotton', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 44, 'Glenn jacket', 2, '44_1.jpg', '44_2.jpg', 80, 400000, 360000, 'Glenn jacket', 4000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 45, 'Camping jumper', 3, '45_1.jpg', '45_2.jpg', 80, 400000, 360000, 'Camping jumper', 4000, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 46, 'wool tri-coloured collectivization jumper', 3, '46_1.jpg', '46_2.jpg', 80, 400000, 360000, 'wool tri-coloured collectivization jumper', 4000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 47, 'Suede Mustang', 4, '47_1.jpg', '47_2.jpg', 80, 600000, 540000, '털이 쉽게 빠지지 않고 너무나 부드럽게 되어있어 입어보시면 겉과 속의 촉감에 놀라시고 보온성에 있어서 매우 뛰어나 한겨울에 입기 너무나 좋은 무스탕 입니다. 적극 추천드립니다.', 6000, 'leather', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 48, 'padding vest', 3, '48_1.jpg', '48_2.jpg', 80, 400000, 360000, 'padding vest', 4000, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 49, 'short Padding', 3, '49_1.jpg', '49_2.jpg', 80, 250000, 225000, '웰론 충전재로 제작된 패딩, 하이넥으로 제작되었으며, 겉감은 바스락 거리는 원단으로 제작되어 고급스러움을 더했습니다. 가격대비 최상의 퀄리티로 제작되어 강력 추천드리는 제품입니다.', 2500, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 50, 'ANGEL WAPPEN ZIP UP HOODIE', 3, '50_1.jpg', '50_2.jpg', 85, 250000, 175000, 'ANGEL WAPPEN ZIP UP HOODIE', 2500, 'cotton', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 51, 'WASHED DENIM TRUCKER', 3, '51_1.jpg', '51_2.jpg', 85, 250000, 175000, 'WASHED DENIM TRUCKER', 2500, 'cotton', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 52, 'labm collar long down jumper', 3, '52_1.jpg', '52_2.jpg', 85, 250000, 175000, 'labm collar long down jumper', 2500, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 53, 'fleece full zip up hoodie', 3, '53_1.jpg', '53_2.jpg', 85, 180000, 126000, 'fleece full zip up hoodie', 1800, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 54, 'ROUND NECK LIGHT DOWN JACKET', 2, '54_1.jpg', '54_2.jpg', 85, 400000, 280000, 'ROUND NECK LIGHT DOWN JACKET', 4000, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 55, 'long down jumper', 3, '55_1.jpg', '55_2.jpg', 85, 180000, 126000, 'long down jumper', 1800, 'polyester', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 56, 'OVERSIZE BLAZER', 2, '56_1.jpg', '56_2.jpg', 85, 400000, 280000, 'OVERSIZE BLAZER', 4000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 57, 'Pocket Knit cardigan', 5, '57_1.jpg', '57_2.jpg', 85, 150000, 105000, 'Pocket Knit cardigan', 1500, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 58, 'STANDARD COAT', 1, '58_1.jpg', '58_2.jpg', 85, 400000, 280000, 'CASHMERE BLEND OVERSIZE SINGLE COAT', 4000, 'wool', 1 ); 
insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) values( 59, 'training jumper', 3, '59_1.jpg', '59_2.jpg', 85, 180000, 126000, 'training jumper', 1800, 'polyester', 1 ); 


insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 47, 21, 'blue', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 48, 21, 'gray', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 49, 22, 'olive', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 50, 22, 'olive', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 51, 22, 'olive', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 52, 23, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 53, 23, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 54, 23, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 55, 24, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 56, 24, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 57, 25, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 58, 25, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 59, 25, 'ivory', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 60, 25, 'ivory', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 61, 26, 'beige', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 62, 26, 'beige', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 63, 27, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 64, 27, 'gray', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 65, 28, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 66, 28, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 67, 29, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 68, 29, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 69, 29, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 70, 30, 'khaki', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 71, 31, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 72, 32, 'gray', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 73, 33, 'mint', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 74, 34, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 75, 34, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 76, 34, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 77, 35, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 78, 35, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 79, 35, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 80, 36, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 81, 36, 'navy', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 82, 36, 'wine', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 83, 37, 'gray', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 84, 37, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 85, 38, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 86, 38, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 87, 38, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 88, 39, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 89, 39, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 90, 40, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 91, 40, 'navy', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 92, 40, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 93, 41, 'brown', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 94, 41, 'brown', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 95, 41, 'brown', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 96, 41, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 97, 41, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 98, 41, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 99, 42, 'gray', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 100, 42, 'gray', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 101, 42, 'gray', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 102, 42, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 103, 42, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 104, 42, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 105, 43, 'khaki', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 106, 43, 'ivory', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 107, 44, 'gray', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 108, 44, 'gray', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 109, 44, 'gray', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 110, 45, 'white', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 111, 45, 'white', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 112, 45, 'white', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 113, 46, 'navy', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 114, 46, 'ivory', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 115, 47, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 116, 47, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 117, 47, 'ivory', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 118, 47, 'ivory', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 119, 48, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 120, 48, 'gray', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 121, 48, 'wine', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 122, 49, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 123, 49, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 124, 49, 'white', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 125, 49, 'wine', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 126, 50, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 127, 50, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 128, 50, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 129, 50, 'gray', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 130, 50, 'gray', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 131, 50, 'gray', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 132, 51, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 133, 51, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 134, 51, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 135, 52, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 136, 52, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 137, 53, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 138, 53, 'beige', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 139, 53, 'pink', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 140, 53, 'ivory', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 141, 54, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 142, 54, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 143, 54, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 144, 54, 'navy', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 145, 54, 'navy', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 146, 54, 'navy', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 147, 55, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 148, 55, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 149, 55, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 150, 56, 'black', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 151, 56, 'black', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 152, 56, 'black', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 153, 56, 'navy', 'S' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 154, 56, 'navy', 'M' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 155, 56, 'navy', 'L' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 156, 57, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 157, 57, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 158, 57, 'gray', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 159, 58, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 160, 58, 'brown', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 161, 58, 'wine', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 162, 59, 'black', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 163, 59, 'navy', 'FREE' ); 
insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize)  values( 164, 59, 'wine', 'FREE' ); 


commit;


create table tbl_product_imagefile(
imgfileno   NUMBER(8)   NOT NULL,
pdno_fk     NUMBER(8)   NOT NULL,
imgfilename VARCHAR2(100),
CONSTRAINT tbl_product_imagefile FOREIGN KEY(pdno_fk) 
REFERENCES TBL_PRODUCT(PDNO) ON DELETE CASCADE
);

select *
from tbl_product_imagefile;

insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1001, 1, '1_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1002, 2, '2_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1003, 3, '3_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1004, 4, '4_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1005, 5, '5_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1006, 6, '6_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1007, 7, '7_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1008, 8, '8_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1009, 9, '9_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1010, 12, '12_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1011, 13, '13_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1012, 15, '15_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1013, 16, '16_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1014, 17, '17_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1015, 18, '18_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1016, 19, '19_3.PNG');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1017, 41, '41_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1018, 42, '42_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1019, 43, '43_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1020, 44, '44_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1021, 45, '45_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1022, 46, '46_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1023, 47, '47_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1024, 48, '48_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1025, 49, '49_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1026, 50, '50_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1027, 51, '51_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1028, 52, '52_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1029, 53, '53_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1030, 54, '54_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1031, 55, '55_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1032, 56, '56_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1033, 57, '57_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1034, 58, '58_3.jpg');
insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) values( 1035, 59, '59_3.jpg');

commit;

select *
from tbl_product;

select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender
from tbl_product
where pdcategory_fk in 1 and pdgender in (1,2) and pdname like '%%'
order by saleprice;


String sql = "select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender\n"+
"from tbl_product\n"+
"where pdcategory_fk in 1 and pdgender in (1,2) and pdname like '%%'\n"+
"order by saleprice";



select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender
from tbl_product
where pdcategory_fk = 1 and pdgender in (1,2) and pdname like '%%'
order by saleprice ;


-- 검색된 제품의 숫자도 넣기 (쓰지않음) --
select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender, 
        ( select count(*) from tbl_product where pdcategory_fk = 1 and pdgender in (1,2) and pdname like '%%' ) AS searchno
from tbl_product
where pdcategory_fk = 1 and pdgender in (1,2) and pdname like '%%'
order by saleprice ;

select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender
from tbl_product;


select pcolor
from tbl_product_info 
where pdno_fk = 1;


-- 검색된 제품의 컬러 넣기 --
select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender
from tbl_product
where pdcategory_fk = 1 and pdgender in (1,2) and pdname like '%%'
order by saleprice ;

select distinct pdno_fk
from tbl_product_info 
order by pdno_fk


String sql = "select pcolor\n"+
"from tbl_product_info \n"+
"where pdno_fk = 1";

select *
from tbl_product
where pdno = 1;

select *
from tbl_product_info 
where pdno_fk = 1;

select distinct pcolor
from tbl_product_info 
where pdno_fk = '1';


commit;



-- 검색된 제품의 사이즈 넣기 --
select *
from tbl_product_info;

select distinct psize
from tbl_product_info 
where pdno_fk = '22'
order by psize desc;

String sql = "select distinct psize\n"+
"from tbl_product_info \n"+
"where pdno_fk = '22'\n"+
"order by psize desc";


select *
from tbl_product;

update tbl_product_info
set psize = 'FREE' 
where
psize = 'free';

commit;


create table tbl_notice
(noticeno  NUMBER(10)     NOT NULL,   -- 시퀀스로 입력받음.
title      VARCHAR2(100)   NOT NULL,
writeday   DATE     default sysdate,
contents VARCHAR2(1000) NOT NULL
);


create sequence seq_tbl_notice_noticeno
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


insert into tbl_notice(noticeno, title, contents) values(seq_tbl_notice_noticeno.nextval, '배송/개별배송 공지사항', 'NEIGE는 전상품 무료배송입니다.');
insert into tbl_notice(noticeno, title, contents) values(seq_tbl_notice_noticeno.nextval, '원단별 세탁 방법', '모든 의류는 드라이크리닝을 권장합니다! 잘못된 세탁으로 인해 상품이 훼손된 경우에는 교환/반품의 사유가 되지 않으니 참고 부탁드립니다.');

commit;


select * from tbl_notice;


select noticeno, title, writeday, contents 
from
(
SELECT noticeno, title, writeday, contents 
FROM tbl_notice 
WHERE noticeno < 2
ORDER BY noticeno DESC
)
where rownum <=1;

SELECT SYSDATE FROM DUAL;

SELECT noticeno FROM tbl_notice ORDER BY noticeno DESC;












