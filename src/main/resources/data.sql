-- User
INSERT INTO users (user_id, username, access_at, role)
VALUES
    (1, 'host1', CURRENT_TIMESTAMP, 'HOST'),
    (2, 'host2', CURRENT_TIMESTAMP, 'HOST'),
    /*아무 이벤트도 만들지 않은 host*/
    (3, 'host2', CURRENT_TIMESTAMP, 'HOST'),

    (4, 'guest_1', CURRENT_TIMESTAMP, 'GUEST'),
    (5, 'guest_2', CURRENT_TIMESTAMP, 'GUEST'),

    /*아무것도 이벤트에도 참여하지 않는 guest */
    (6, 'guest_3', CURRENT_TIMESTAMP, 'GUEST'),
    (7, 'guest_4', CURRENT_TIMESTAMP, 'GUEST');

-- DatingEvent
INSERT INTO dating_event (dating_event_id, title, host_user_id, max_participants, event_date)
VALUES
    (100, '봄맞이 데이팅', 1, 3, CURRENT_TIMESTAMP),
    (101, '한강 야외 데이팅', 2, 5, CURRENT_TIMESTAMP);

-- DatingParticipant
INSERT INTO dating_participant (id, dating_event_id, user_id, participation_date_time)
VALUES
    (1000, 100, 4, CURRENT_TIMESTAMP),
    (1001, 100, 5, CURRENT_TIMESTAMP),
    (1002, 101, 4, CURRENT_TIMESTAMP);

-- Vote
INSERT INTO vote (id, dating_event_id, title, is_closed)
VALUES
    (500, 100, '장소 투표', false);

-- VoteOption
INSERT INTO vote_option (id, option_value, vote_id)
VALUES
    (10000, '한강공원', 500),
    (10001, '북서울 꿈의숲', 500);

-- VoteParticipant
INSERT INTO vote_participant (id, user_id, vote_id, option_id)
VALUES
    (20000, 2, 500, 10000),
    (20001, 3, 500, 10001);