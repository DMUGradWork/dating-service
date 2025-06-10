-- -- Users 테이블
-- CREATE TABLE IF NOT EXISTS users (
--                                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                      username VARCHAR(255) NOT NULL,
--                                      access_at TIMESTAMP NOT NULL,
--                                      role VARCHAR(50) NOT NULL,
--                                      gender VARCHAR(10)
-- );
--
-- CREATE TABLE IF NOT EXISTS dating_event (
--                                             dating_event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                             title VARCHAR(255) NOT NULL,
--                                             host_user_id BIGINT NOT NULL,
--                                             max_participants BIGINT,
--                                             location VARCHAR(255),
--                                             event_date_time TIMESTAMP,
--                                             max_male_participants_count BIGINT,
--                                             max_female_participants_count BIGINT,
--                                             FOREIGN KEY (host_user_id) REFERENCES users(user_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS dating_participant (
--                                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                                   dating_event_id BIGINT NOT NULL,
--                                                   user_id BIGINT NOT NULL,
--                                                   participation_date_time TIMESTAMP NOT NULL,
--                                                   FOREIGN KEY (dating_event_id) REFERENCES dating_event(dating_event_id),
--                                                   FOREIGN KEY (user_id) REFERENCES users(user_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS vote (
--                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                     dating_event_id BIGINT NOT NULL,
--                                     title VARCHAR(255) NOT NULL,
--                                     is_closed BOOLEAN DEFAULT FALSE,
--                                     FOREIGN KEY (dating_event_id) REFERENCES dating_event(dating_event_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS vote_option (
--                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                            option_value VARCHAR(255) NOT NULL,
--                                            vote_id BIGINT NOT NULL,
--                                            FOREIGN KEY (vote_id) REFERENCES vote(id)
-- );
--
-- CREATE TABLE IF NOT EXISTS vote_participant (
--                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                                 user_id BIGINT NOT NULL,
--                                                 vote_id BIGINT NOT NULL,
--                                                 option_id BIGINT NOT NULL,
--                                                 FOREIGN KEY (user_id) REFERENCES users(user_id),
--                                                 FOREIGN KEY (vote_id) REFERENCES vote(id),
--                                                 FOREIGN KEY (option_id) REFERENCES vote_option(id)
-- );
--
-- INSERT INTO users (user_id, username, access_at, role)
-- VALUES
--     (1, 'GrewMeet Official', CURRENT_TIMESTAMP, 'HOST'),
--     (2, '책벌레들', CURRENT_TIMESTAMP, 'HOST'),
--     /*아무 이벤트도 만들지 않은 host*/
--     (3, 'host2', CURRENT_TIMESTAMP, 'HOST'),
--
--     (4, 'guest_1', CURRENT_TIMESTAMP, 'GUEST'),
--     (5, 'guest_2', CURRENT_TIMESTAMP, 'GUEST'),
--
--     /*아무것도 이벤트에도 참여하지 않는 guest */
--     (6, 'guest_3', CURRENT_TIMESTAMP, 'GUEST'),
--     (7, 'guest_4', CURRENT_TIMESTAMP, 'GUEST');
--
-- -- -- DatingEvent
-- -- INSERT INTO dating_event (dating_event_id, title, host_user_id, max_participants, event_date)
-- -- VALUES
-- --     (100, '봄맞이 데이팅', 1, 3, CURRENT_TIMESTAMP),
-- --     (101, '한강 야외 데이팅', 2, 5, CURRENT_TIMESTAMP);
--
-- -- 목업 이벤트 3건 삽입
-- INSERT INTO dating_event
-- (dating_event_id, title, host_user_id,   max_participants, location, event_date_time)
-- VALUES
--     (100, '제4회 제주에 혼저옵서예🍊',     1, 20, '제주',   TIMESTAMP '2025-06-22 00:00:00'),
--     (101, 'GrewMeet 공식 데이팅',       1, 10, '서울',   TIMESTAMP '2025-06-29 00:00:00'),
--     (102, '독서 스터디 모집',           2, 10, '부산',   TIMESTAMP '2025-07-05 00:00:00');
--
-- -- DatingParticipant
-- INSERT INTO dating_participant (id, dating_event_id, user_id, participation_date_time)
-- VALUES
--     (1000, 100, 4, CURRENT_TIMESTAMP),
--     (1001, 100, 5, CURRENT_TIMESTAMP),
--     (1002, 101, 4, CURRENT_TIMESTAMP);
--
-- -- Vote
-- INSERT INTO vote (id, dating_event_id, title, is_closed)
-- VALUES
--     (500, 100, '장소 투표', false);
--
-- -- VoteOption
-- INSERT INTO vote_option (id, option_value, vote_id)
-- VALUES
--     (10000, '한강공원', 500),
--     (10001, '북서울 꿈의숲', 500);
--
-- -- VoteParticipant
-- INSERT INTO vote_participant (id, user_id, vote_id, option_id)
-- VALUES
--     (20000, 2, 500, 10000),
--     (20001, 3, 500, 10001);

-- Users 테이블
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL,
                                     access_at TIMESTAMP NOT NULL,
                                     role VARCHAR(50) NOT NULL,
                                     gender VARCHAR(10)
);

-- DatingEvent 테이블 (max_participants 컬럼 제거)
CREATE TABLE IF NOT EXISTS dating_event (
                                            dating_event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            title VARCHAR(255) NOT NULL,
                                            host_user_id BIGINT NOT NULL,
                                            location VARCHAR(255),
                                            event_date_time TIMESTAMP,
                                            max_male_participants_count BIGINT,
                                            max_female_participants_count BIGINT,
                                            description TEXT,
                                            FOREIGN KEY (host_user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS dating_participant (
                                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                  dating_event_id BIGINT NOT NULL,
                                                  user_id BIGINT NOT NULL,
                                                  participation_date_time TIMESTAMP NOT NULL,
                                                  FOREIGN KEY (dating_event_id) REFERENCES dating_event(dating_event_id),
                                                  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS vote (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    dating_event_id BIGINT NOT NULL,
                                    title VARCHAR(255) NOT NULL,
                                    is_closed BOOLEAN DEFAULT FALSE,
                                    FOREIGN KEY (dating_event_id) REFERENCES dating_event(dating_event_id)
);

CREATE TABLE IF NOT EXISTS vote_option (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           option_value VARCHAR(255) NOT NULL,
                                           vote_id BIGINT NOT NULL,
                                           FOREIGN KEY (vote_id) REFERENCES vote(id)
);

CREATE TABLE IF NOT EXISTS vote_participant (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                user_id BIGINT NOT NULL,
                                                vote_id BIGINT NOT NULL,
                                                option_id BIGINT NOT NULL,
                                                FOREIGN KEY (user_id) REFERENCES users(user_id),
                                                FOREIGN KEY (vote_id) REFERENCES vote(id),
                                                FOREIGN KEY (option_id) REFERENCES vote_option(id)
);

-- Users 데이터 (호스트와 게스트)
INSERT INTO users (user_id, username, access_at, role, gender)
VALUES
    -- 호스트들
    (1, 'GrewMeet Official', CURRENT_TIMESTAMP, 'HOST', 'MALE'),
    (2, '책벌레들', CURRENT_TIMESTAMP, 'HOST', 'MALE'),
    (3, '영화광클럽', CURRENT_TIMESTAMP, 'HOST', 'MALE'),
    (4, '맛집탐방대', CURRENT_TIMESTAMP, 'HOST', 'MALE'),

    -- 게스트들 (성별 포함)
    (5, '김민준', CURRENT_TIMESTAMP, 'GUEST', 'MALE'),
    (6, '이서연', CURRENT_TIMESTAMP, 'GUEST', 'FEMALE'),
    (7, '박준호', CURRENT_TIMESTAMP, 'GUEST', 'MALE'),
    (8, '최은지', CURRENT_TIMESTAMP, 'GUEST', 'FEMALE'),
    (9, '정현우', CURRENT_TIMESTAMP, 'GUEST', 'MALE'),
    (10, '강수진', CURRENT_TIMESTAMP, 'GUEST', 'FEMALE'),
    (11, '이동현', CURRENT_TIMESTAMP, 'GUEST', 'MALE'),
    (12, '김지은', CURRENT_TIMESTAMP, 'GUEST', 'FEMALE'),

    -- 참가하지 않은 게스트들
    (13, '홍길동', CURRENT_TIMESTAMP, 'GUEST', 'MALE'),
    (14, '김영희', CURRENT_TIMESTAMP, 'GUEST', 'FEMALE');

-- DatingEvent 데이터 (DatingEventResponseNew 구조에 맞게)
-- max_participants 컬럼 제거
INSERT INTO dating_event
(dating_event_id, title, host_user_id, location, event_date_time,
 max_male_participants_count, max_female_participants_count, description)
VALUES
    -- GrewMeet Official 주최
    (100, '제4회 제주에 혼저옵서예🍊', 1, '제주 서귀포시 중문관광단지',
     TIMESTAMP '2025-06-22 14:00:00', 10, 10,
     '제주도의 아름다운 자연과 함께하는 특별한 만남! 오름 트레킹과 맛집 탐방이 준비되어 있습니다.'),

    (101, 'GrewMeet 공식 여름 데이팅', 1, '서울 한강공원 여의도지구',
     TIMESTAMP '2025-06-29 18:00:00', 8, 8,
     '한강에서 즐기는 시원한 여름밤! 치맥과 보드게임, 그리고 불꽃놀이까지!'),

    -- 책벌레들 주최
    (102, '독서 모임 & 북카페 데이트', 2, '서울 성수동 북카페 ''책과 커피''',
     TIMESTAMP '2025-07-05 15:00:00', 6, 6,
     '좋아하는 책 한 권을 가져와 서로 소개하고, 편안한 분위기에서 대화를 나눠요.'),

    -- 영화광클럽 주최
    (103, '영화 관람 & 와인바 데이팅', 3, '강남 CGV & 와인바 ''비노''',
     TIMESTAMP '2025-07-12 17:00:00', 4, 4,
     '최신 영화를 함께 보고, 와인 한 잔과 함께 영화 이야기를 나눠보아요.'),

    -- 맛집탐방대 주최
    (104, '미슐랭 레스토랑 투어', 4, '서울 청담동 일대',
     TIMESTAMP '2025-07-19 18:30:00', 5, 5,
     '미슐랭 스타 레스토랑에서 코스 요리를 즐기며 특별한 인연을 만들어보세요.'),

    -- 종료된 이벤트
    (105, '봄맞이 벚꽃 데이팅', 1, '서울 여의도 윤중로',
     TIMESTAMP '2025-04-05 14:00:00', 10, 10,
     '벚꽃이 만개한 여의도에서 함께 걷고, 피크닉을 즐겨요!');

-- DatingParticipant 데이터 (참가자들)
INSERT INTO dating_participant (id, dating_event_id, user_id, participation_date_time)
VALUES
    -- 제주 이벤트 (100) 참가자 - 남3, 여3
    (1000, 100, 5, TIMESTAMP '2025-06-01 10:00:00'),  -- 김민준(남)
    (1001, 100, 6, TIMESTAMP '2025-06-01 10:30:00'),  -- 이서연(여)
    (1002, 100, 7, TIMESTAMP '2025-06-01 11:00:00'),  -- 박준호(남)
    (1003, 100, 8, TIMESTAMP '2025-06-01 14:00:00'),  -- 최은지(여)
    (1004, 100, 9, TIMESTAMP '2025-06-02 09:00:00'),  -- 정현우(남)
    (1005, 100, 10, TIMESTAMP '2025-06-02 10:00:00'), -- 강수진(여)

    -- 한강 이벤트 (101) 참가자 - 남2, 여2
    (1006, 101, 5, TIMESTAMP '2025-06-15 14:00:00'),  -- 김민준(남) - 중복 참가
    (1007, 101, 8, TIMESTAMP '2025-06-15 15:00:00'),  -- 최은지(여) - 중복 참가
    (1008, 101, 11, TIMESTAMP '2025-06-16 10:00:00'), -- 이동현(남)
    (1009, 101, 12, TIMESTAMP '2025-06-16 11:00:00'), -- 김지은(여)

    -- 독서 모임 (102) 참가자 - 남1, 여1
    (1010, 102, 7, TIMESTAMP '2025-06-20 16:00:00'),  -- 박준호(남)
    (1011, 102, 10, TIMESTAMP '2025-06-20 17:00:00'), -- 강수진(여)

    -- 영화 데이팅 (103) 참가자 - 만원
    (1012, 103, 5, TIMESTAMP '2025-06-25 10:00:00'),  -- 김민준(남)
    (1013, 103, 6, TIMESTAMP '2025-06-25 10:30:00'),  -- 이서연(여)
    (1014, 103, 9, TIMESTAMP '2025-06-25 11:00:00'),  -- 정현우(남)
    (1015, 103, 10, TIMESTAMP '2025-06-25 11:30:00'), -- 강수진(여)
    (1016, 103, 11, TIMESTAMP '2025-06-26 09:00:00'), -- 이동현(남)
    (1017, 103, 12, TIMESTAMP '2025-06-26 09:30:00'), -- 김지은(여)
    (1018, 103, 7, TIMESTAMP '2025-06-26 10:00:00'),  -- 박준호(남)
    (1019, 103, 8, TIMESTAMP '2025-06-26 10:30:00');  -- 최은지(여)

-- Vote 데이터
INSERT INTO vote (id, dating_event_id, title, is_closed)
VALUES
    -- 제주 이벤트 투표
    (500, 100, '오름 트레킹 코스 선택', false),
    (501, 100, '저녁 식사 메뉴', false),

    -- 한강 이벤트 투표
    (502, 101, '보드게임 종류 선택', false),
    (503, 101, '치킨 브랜드 선택', true),  -- 마감된 투표

    -- 독서 모임 투표
    (504, 102, '다음 모임 도서 선택', false);

-- VoteOption 데이터
INSERT INTO vote_option (id, option_value, vote_id)
VALUES
    -- 오름 트레킹 코스 (500)
    (10000, '성산일출봉', 500),
    (10001, '한라산 영실코스', 500),
    (10002, '사려니숲길', 500),

    -- 저녁 식사 메뉴 (501)
    (10003, '흑돼지 구이', 501),
    (10004, '갈치조림', 501),
    (10005, '해물탕', 501),

    -- 보드게임 종류 (502)
    (10006, '루미큐브', 502),
    (10007, '할리갈리', 502),
    (10008, '스플렌더', 502),
    (10009, '다빈치코드', 502),

    -- 치킨 브랜드 (503)
    (10010, 'BBQ', 503),
    (10011, '교촌치킨', 503),
    (10012, 'BHC', 503),

    -- 다음 도서 선택 (504)
    (10013, '데미안 - 헤르만 헤세', 504),
    (10014, '1984 - 조지 오웰', 504),
    (10015, '코스모스 - 칼 세이건', 504);

-- VoteParticipant 데이터
INSERT INTO vote_participant (id, user_id, vote_id, option_id)
VALUES
    -- 오름 트레킹 투표 참여
    (20000, 5, 500, 10002),  -- 김민준 -> 사려니숲길
    (20001, 6, 500, 10000),  -- 이서연 -> 성산일출봉
    (20002, 7, 500, 10002),  -- 박준호 -> 사려니숲길

    -- 저녁 식사 투표 참여
    (20003, 5, 501, 10003),  -- 김민준 -> 흑돼지
    (20004, 8, 501, 10003),  -- 최은지 -> 흑돼지
    (20005, 9, 501, 10005),  -- 정현우 -> 해물탕

    -- 보드게임 투표 참여
    (20006, 5, 502, 10006),  -- 김민준 -> 루미큐브
    (20007, 11, 502, 10008), -- 이동현 -> 스플렌더

    -- 치킨 브랜드 투표 (마감됨)
    (20008, 5, 503, 10010),  -- 김민준 -> BBQ
    (20009, 8, 503, 10010),  -- 최은지 -> BBQ
    (20010, 11, 503, 10011), -- 이동현 -> 교촌
    (20011, 12, 503, 10010); -- 김지은 -> BBQ

-- 통계 확인용 쿼리
-- 각 이벤트별 참가자 수 (성별 구분)
SELECT
    de.dating_event_id,
    de.title,
    de.max_male_participants_count,
    de.max_female_participants_count,
    COUNT(CASE WHEN u.gender = 'MALE' THEN 1 END) as current_male_count,
    COUNT(CASE WHEN u.gender = 'FEMALE' THEN 1 END) as current_female_count
FROM dating_event de
         LEFT JOIN dating_participant dp ON de.dating_event_id = dp.dating_event_id
         LEFT JOIN users u ON dp.user_id = u.user_id
GROUP BY de.dating_event_id, de.title, de.max_male_participants_count, de.max_female_participants_count;