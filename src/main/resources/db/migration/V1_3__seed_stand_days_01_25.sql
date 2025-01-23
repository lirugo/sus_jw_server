INSERT INTO stand_days(id, date, stand_id) VALUES
    (1, '2025-01-20', 1), -- mn mkuw-metro
    -- Skipping 2025-01-21 (Tuesday - Free Day)
    (2, '2025-01-22', 2), -- we mkuw-novus
    (3, '2025-01-22', 3), -- we mkuw-buzova
    (4, '2025-01-23', 1), -- th mkuw-metro
    (5, '2025-01-24', 4), -- fr mkuw-dachna-varus
    (6, '2025-01-24', 5), -- fr mkuw-dachna-park
    (7, '2025-01-25', 6), -- sa mkuw-chaika
    (8, '2025-01-25', 7), -- sa mkuw-velmart
    (9, '2025-01-26', 7), -- su mkuw-velmart
    --- new week
    (10, '2025-01-27', 1), -- mn mkuw-metro
    -- Skipping 2025-01-28 (Tuesday - Free Day)
    (11, '2025-01-29', 2), -- we mkuw-novus
    (12, '2025-01-29', 3), -- we mkuw-buzova
    (13, '2025-01-30', 1), -- th mkuw-metro
    (14, '2025-01-31', 4), -- fr mkuw-dachna-varus
    (15, '2025-01-31', 5), -- fr mkuw-dachna-park
    (16, '2025-02-01', 6), -- sa mkuw-chaika
    (17, '2025-02-01', 7), -- sa mkuw-velmart
    (18, '2025-02-02', 7); -- su mkuw-velmart

INSERT INTO stand_day_time_frames(stand_day_id, time_frame_id) VALUES
    -- 2025-01-20 (Metro)
    (1, 9), (1, 10),
    -- 2025-01-21 free day
    -- 2025-01-22 (Novus)
    (2, 9), (2, 10),
    -- 2025-01-22 (Buzova)
    (3, 7), (3, 8), (3, 9), (3, 10),
    -- 2025-01-23 (Metro)
    (4, 9), (4, 10),
    -- 2025-01-24 (Dachna-Varus)
    (5, 9), (5, 10),
    -- 2025-01-24 (Dachna-Park)
    (6, 15), (6, 16), (6, 17), (6, 18),
    -- 2025-01-25 (Chaika)
    (7, 12), (7, 13),
    -- 2025-01-25 (Velmart)
    (8, 11), (8, 12), (8, 13), (8, 14), (8, 15), (8, 16), (8, 17), (8, 18),
    -- 2025-01-26 (Velmart)
    (9, 15), (9, 16), (9, 17), (9, 18),
    -- new week
    -- 2025-01-27 (Metro)
    (10, 9), (10, 10),
    -- 2025-01-28 free day
    -- 2025-01-29 (Novus)
    (11, 9), (11, 10),
    -- 2025-01-29 (Buzova)
    (12, 7), (12, 8), (12, 9), (12, 10),
    -- 2025-01-30 (Metro)
    (13, 9), (13, 10),
    -- 2025-01-31 (Dachna-Varus)
    (14, 9), (14, 10),
    -- 2025-01-31 (Dachna-Park)
    (15, 15), (15, 16), (15, 17), (15, 18),
    -- 2025-02-01 (Chaika)
    (16, 12), (16, 13),
    -- 2025-02-01 (Velmart)
    (17, 11), (17, 12), (17, 13), (17, 14), (17, 15), (17, 16), (17, 17), (17, 18),
    -- 2025-02-02 (Velmart)
    (18, 15), (18, 16), (18, 17), (18, 18);