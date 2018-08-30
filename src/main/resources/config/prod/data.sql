SET NAMES 'utf8';
SET CHARACTER SET utf8;

/* Populate USER_PROFILE Table */
INSERT INTO ROLE (type)
VALUES ('USER');

INSERT INTO ROLE (type)
VALUES ('ADMIN');

INSERT INTO ROLE (type)
VALUES ('MANAGER');

INSERT INTO ROLE (type)
VALUES ('TOP_MANAGEMENT');

INSERT INTO ROLE (type)
VALUES ('HEADMAN');

INSERT INTO business_unit (name) VALUE ('Воркута');
INSERT INTO business_unit (name) VALUE ('ЧерМК');
INSERT INTO business_unit (name) VALUE ('"АО ""Воркутауголь"" "');
INSERT INTO business_unit (name) VALUE ('ПАО Северсталь');
INSERT INTO business_unit (name) VALUE ('"АО ""Северсталь - Менеджмент"""');

INSERT INTO Organisation (name, business_unit_id) VALUE ('TEST-TREST', 1);
INSERT INTO Organisation (name, business_unit_id) VALUE ('AVANGERS', 2);
INSERT INTO organisation (name, business_unit_id) VALUE ('"АО ""Воркутауголь"" "', 3);
INSERT INTO organisation (name, business_unit_id) VALUE ('ПАО Северсталь', 4);
INSERT INTO organisation (name, business_unit_id) VALUE ('"""АО Северсталь Менеджмент"""', 5);

/* Populate SUBDIVISION Table */
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('1 be,г.Воркута, 3ий металлообрабатывающий цех', 1, 1);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('1 be,г.Воркута, 1ый сталелитейный цех', 1, 1);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('1 be,г.Череповец, горнодобывающий комбинат', 1, 1);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('1 be,г.Череповец, 10ый качегарный цех', 1, 1);
INSERT INTO subdivision (name, business_unit_id, organisation_id) VALUES ('1 be,г.Воркута, команда шахтеров', 1, 1);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('2 be,г.Воркута, 3ий металлообрабатывающий цех', 2, 2);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('2 be,г.Воркута, 1ый сталелитейный цех', 2, 2);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('2 be,г.Череповец, горнодобывающий комбинат', 2, 2);
INSERT INTO subdivision (name, business_unit_id, organisation_id)
VALUES ('2 be,г.Череповец, 10ый качегарный цех', 2, 2);
INSERT INTO subdivision (name, business_unit_id, organisation_id) VALUES ('2 be,г.Воркута, команда шахтеров', 2, 2);
INSERT INTO subdivision (name, business_unit_id, organisation_id) VALUES ('vorkuta test', 3, 3);
INSERT INTO subdivision (name, business_unit_id, organisation_id) VALUES ('pao svs test', 4, 4);
INSERT INTO subdivision (name, business_unit_id, organisation_id) VALUES ('svsmg test', 5, 5);

/* Populate User */
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES ('root', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Sam', '098765', '12.03.76', 1, 1);

INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('vorkuta', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'AO Воркута', '9999999', '12.03.76', 3,
   11);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('svs', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'ПAO Северсталь', '0000000', '12.03.76', 4,
   12);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('svsmg', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'AO Северсталь - Менеджмент', '0000001',
   '12.03.76', 5, 13);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('headman', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Le Su Huan', '123456', '13.02.89', 2, 2);

INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('director', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Данила Бодров', '111111', '01.04.43', 1,
   1);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('Ashot', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Ашот Ваншот', '666666', '22.12.88', 1, 1);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('topmgmvorkuta', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'TOP MGM VORKUTA', '333333', '09.06.70', 3,
   11);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
	('topmgmsvs', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'TOP MGM SVS', '222222', '08.07.60', 4, 12);
INSERT INTO User (login, password, full_name, personal_number, DOB, organisation_id, subdivision_id)
VALUES
  ('MasterVorkuta', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'AO Воркута', '8888888', '13.03.76', 3, 11);


/* Populate JOIN Table */
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'root' AND role.type = 'ADMIN';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'vorkuta' AND role.type = 'ADMIN';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'svs' AND role.type = 'ADMIN';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'svsmg' AND role.type = 'ADMIN';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'Danila' AND role.type = 'MANAGER';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'Ashot' AND role.type = 'MANAGER';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'Cap' AND role.type = 'MANAGER';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'manager' AND role.type = 'ADMIN';
  INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'topmgmvorkuta' AND role.type = 'TOP_MANAGEMENT';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
  SELECT
    User.id,
    role.id
  FROM user User, role role
  WHERE user.login = 'topmgmsvs' AND role.type = 'TOP_MANAGEMENT';
INSERT INTO USER_TO_ROLE (user_id, user_role_id)
	SELECT
		User.id,
		role.id
	FROM user User, role role
	WHERE user.login = 'MasterVorkuta' AND role.type = 'HEADMAN';

/* Populate TYPE QUESTION Table */
INSERT INTO type_question (article, value) VALUES ('MR', 'MR');
INSERT INTO type_question (article, value) VALUES ('MC', 'MC');
INSERT INTO type_question (article, value) VALUES ('TF', 'TF');
INSERT INTO type_question (article, value) VALUES ('RNK', 'RNK');
/* Populate SUBJECT Table */
INSERT INTO subject (name,business_unit_id) VALUE ('1 be,Пожарная безопасность',1);
INSERT INTO subject (name,business_unit_id) VALUE ('1 be,Правила поведения', 1);
INSERT INTO subject (name,business_unit_id) VALUE ('1 be,Общие', 1);
INSERT INTO subject (name,business_unit_id) VALUE ('1 be,Эвакуация', 1);
INSERT INTO subject (name,business_unit_id) VALUE ('2 be,Пожарная безопасность', 2);
INSERT INTO subject (name,business_unit_id) VALUE ('2 be,Правила поведения', 2);
INSERT INTO subject (name,business_unit_id) VALUE ('2 be,Общие', 2);
INSERT INTO subject (name,business_unit_id) VALUE ('2 be,Эвакуация', 2);
/* Populate COMPLEXITY Table */
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Простой', 1, -4, 1);
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Сложный', 2, -7, 1);
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Простой', 1, -4, 2);
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Сложный', 2, -7, 2);

INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Простой', 0, 0, 3);
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Сложный', 0, 0, 3);

INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Простой', 0, 0, 4);
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Сложный', 0, 0, 4);

INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Простой', 0, 0, 5);
INSERT INTO complexity (name, points, penalty, business_unit_id) VALUE ('Сложный', 0, 0, 5);

/* Populate TIME_SETTINGS Table */
INSERT INTO time_settings (name, date, business_unit_id) VALUE ('текущий год', '2018-01-01 00:00:01', 1);
INSERT INTO time_settings (name, date, business_unit_id) VALUE ('последние 3 месяца', '2018-05-01 00:00:01', 2);


INSERT INTO time_settings (name, date, business_unit_id) VALUE ('текущий год', '2018-01-01 00:00:01', 3);
INSERT INTO time_settings (name, date, business_unit_id) VALUE ('текущий год', '2018-01-01 00:00:01', 4);
INSERT INTO time_settings (name, date, business_unit_id) VALUE ('текущий год', '2018-01-01 00:00:01', 5);

/* Populate QUESTION Table */
INSERT INTO question (text, active_status, subject_id, type_question_id, complexity_id) VALUE ('1 be,Как одевать противогаз?', true, 2, 1, 2);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('1 be,Можно ли работать без спецодежды?', true, 2, 1, 1);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id)
  VALUE ('Какая птица помогала шахтера определять наличие газа в шахте?', true, 3, 2, 2);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id)
  VALUE ('1 be,Название компании, на которую вы работаете', true, 3, 2, 1);
INSERT INTO question (text, active_status, subject_id, type_question_id, complexity_id) VALUE ('1 be,Где весит огнетушитель?', true, 1, 2, 1);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('1 be,Как тушить пожар возникши при замыкании?', true, 1, 4, 2);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('1 be,Куда бежать спасаться?', true, 4, 3, 2);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('1 be,Что делать в случае чп?', true, 4, 3, 2);

INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('2 be,Как одевать противогаз?', true, 6, 1, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('2 be,Можно ли работать без спецодежды?', true, 6, 1, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id)
  VALUE ('2 be,Какая птица помогала шахтера определять наличие газа в шахте?', true, 7, 2, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id)
  VALUE ('2 be,Название компании, на которую вы работаете', true, 7, 2, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('2 be,Где весит огнетушитель?', true, 5, 2, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('2 be,Как тушить пожар возникши при замыкании?', true, 5, 4, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('2 be,Куда бежать спасаться?', true, 8, 3, 3);
INSERT INTO question (text, active_status,subject_id, type_question_id, complexity_id) VALUE ('2 be,Что делать в случае чп?', true, 8, 3, 4);

/* Populate ANSWER Table */
INSERT INTO answer (value, correctness, question_id) VALUE ('На голову (прав)', TRUE, 1);
INSERT INTO answer (value, correctness, question_id) VALUE ('Он не нужен', FALSE, 1);
INSERT INTO answer (value, correctness, question_id) VALUE ('Что такое противогаз?', FALSE, 1);
INSERT INTO answer (value, correctness, question_id) VALUE ('не знаю', FALSE, 1);

INSERT INTO answer (value, correctness, question_id) VALUE ('Нельзя (прав)', TRUE, 2);
INSERT INTO answer (value, correctness, question_id) VALUE ('Можно', FALSE, 2);
INSERT INTO answer (value, correctness, question_id) VALUE ('Можно одевать не все', FALSE, 2);
INSERT INTO answer (value, correctness, question_id) VALUE ('У меня нет спецодежды', FALSE, 2);

INSERT INTO answer (value, correctness, question_id) VALUE ('утка', FALSE, 3);
INSERT INTO answer (value, correctness, question_id) VALUE ('попугай', FALSE, 3);
INSERT INTO answer (value, correctness, question_id) VALUE ('канарейка (прав)', TRUE, 3);
INSERT INTO answer (value, correctness, question_id) VALUE ('синица', FALSE, 3);

INSERT INTO answer (value, correctness, question_id) VALUE ('НЛМК', FALSE, 4);
INSERT INTO answer (value, correctness, question_id) VALUE ('АвтоВАЗ', FALSE, 4);
INSERT INTO answer (value, correctness, question_id) VALUE ('Стройбат', FALSE, 4);
INSERT INTO answer (value, correctness, question_id) VALUE ('Северсталь (прав)', TRUE, 4);

INSERT INTO answer (value, correctness, question_id) VALUE ('На стене', FALSE, 5);
INSERT INTO answer (value, correctness, question_id) VALUE ('У пожарного щита (прав)', TRUE, 5);
INSERT INTO answer (value, correctness, question_id) VALUE ('В магазине', FALSE, 5);
INSERT INTO answer (value, correctness, question_id) VALUE ('В пожарной части', FALSE, 5);

INSERT INTO answer (value, correctness, question_id) VALUE ('Позвать на помощь', FALSE, 6);
INSERT INTO answer (value, correctness, question_id) VALUE ('Само потухнет', FALSE, 6);
INSERT INTO answer (value, correctness, question_id) VALUE ('Залить водой', FALSE, 6);
INSERT INTO answer (value, correctness, question_id) VALUE ('Отключить питание и потушить (прав)', TRUE, 6);

INSERT INTO answer (value, correctness, question_id) VALUE ('За границу', FALSE, 7);
INSERT INTO answer (value, correctness, question_id) VALUE ('На улицу', FALSE, 7);
INSERT INTO answer (value, correctness, question_id) VALUE ('Под кровать', FALSE, 7);
INSERT INTO answer (value, correctness, question_id) VALUE ('Следовать плану эвакуации (прав)', TRUE, 7);

INSERT INTO answer (value, correctness, question_id) VALUE ('Не паниковать', TRUE, 8);
INSERT INTO answer (value, correctness, question_id) VALUE ('Ждать', FALSE, 8);
INSERT INTO answer (value, correctness, question_id) VALUE ('Надеяться', FALSE, 8);
INSERT INTO answer (value, correctness, question_id) VALUE ('Расслабиться', FALSE, 8);

INSERT INTO answer (value, correctness, question_id) VALUE ('На голову (прав)', TRUE, 9);
INSERT INTO answer (value, correctness, question_id) VALUE ('Он не нужен', FALSE, 9);
INSERT INTO answer (value, correctness, question_id) VALUE ('Что такое противогаз?', FALSE, 9);
INSERT INTO answer (value, correctness, question_id) VALUE ('не знаю', FALSE, 9);

INSERT INTO answer (value, correctness, question_id) VALUE ('Нельзя (прав)', TRUE, 10);
INSERT INTO answer (value, correctness, question_id) VALUE ('Можно', FALSE, 10);

INSERT INTO answer (value, correctness, question_id) VALUE ('утка', FALSE, 11);
INSERT INTO answer (value, correctness, question_id) VALUE ('канарейка (прав)', TRUE, 11);
INSERT INTO answer (value, correctness, question_id) VALUE ('синица', FALSE, 11);

INSERT INTO answer (value, correctness, question_id) VALUE ('НЛМК', FALSE, 12);
INSERT INTO answer (value, correctness, question_id) VALUE ('АвтоВАЗ', FALSE, 12);
INSERT INTO answer (value, correctness, question_id) VALUE ('Северсталь (прав)', TRUE, 12);

INSERT INTO answer (value, correctness, question_id) VALUE ('На стене', FALSE, 13);
INSERT INTO answer (value, correctness, question_id) VALUE ('У пожарного щита (прав)', TRUE, 13);
INSERT INTO answer (value, correctness, question_id) VALUE ('В пожарной части', FALSE, 13);

INSERT INTO answer (value, correctness, question_id) VALUE ('Залить водой', FALSE, 14);
INSERT INTO answer (value, correctness, question_id) VALUE ('Отключить питание и потушить (прав)', TRUE, 14);

INSERT INTO answer (value, correctness, question_id) VALUE ('На улицу', FALSE, 15);
INSERT INTO answer (value, correctness, question_id) VALUE ('Следовать плану эвакуации (прав)', TRUE, 15);

INSERT INTO answer (value, correctness, question_id) VALUE ('Не паниковать', TRUE, 16);
INSERT INTO answer (value, correctness, question_id) VALUE ('Ждать', FALSE, 16);
INSERT INTO answer (value, correctness, question_id) VALUE ('Надеяться', FALSE, 16);
INSERT INTO answer (value, correctness, question_id) VALUE ('Расслабиться', FALSE, 16);

/* Populate TEST Table */
INSERT INTO test (count_right, count_false, date, points, user_id, question_id) VALUE (1, 3, '2018-05-31 12:35:56', 0, 1, 1);
INSERT INTO test (count_right, count_false, date, points, user_id, question_id) VALUE (2, 0,'2018-05-07 12:35:56', 0, 2, 2);

/* Populate Statistic Table */
INSERT INTO statistic (sum_right, sum_false, user_id) VALUES (1, 0, 1);

/* Populate PROFESSION Table */
INSERT INTO profession (name) VALUES ('1 be,Сварщик');
INSERT INTO profession (name) VALUES ('1 be,Шахтер');
INSERT INTO profession (name) VALUES ('1 be,Водитель');
INSERT INTO profession (name) VALUES ('1 be,Оператор станка с поддержкой ЧПУ');
INSERT INTO profession (name)
VALUES ('1 be,Человек отвечающий за все организационные вопросы');
INSERT INTO profession (name) VALUES ('2 be,Сварщик');
INSERT INTO profession (name) VALUES ('2 be,Шахтер');
INSERT INTO profession (name) VALUES ('2 be,Водитель');
INSERT INTO profession (name) VALUES ('2 be,Оператор станка с поддержкой ЧПУ');
INSERT INTO profession (name)
VALUES ('2 be,Человек отвечающий за все организационные вопросы');

/* Populate PROFESSION JOIN SUBDIVISION Table */

INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (1, 1);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (2, 2);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (3, 3);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (4, 4);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (5, 5);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (6, 6);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (7, 7);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (8, 8);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (9, 9);
INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUES (10, 10);

/* Populate SUBJECT JOIN SUBDIVISION TO PROFESSION Table */
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (1, 1);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (1, 2);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (2, 1);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (2, 2);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (3, 3);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (3, 4);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (4, 1);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (4, 2);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (4, 3);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (4, 4);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (5, 10);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (5, 9);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (6, 7);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (6, 8);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (6, 6);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (7, 7);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (7, 6);
INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id) VALUE (7, 8);

/* Populate PROFESSION JOIN USER Table */
INSERT INTO user_to_profession (user_id, profession_id) VALUE (1, 1);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (1, 2);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (2, 8);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (2, 6);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (3, 7);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (3, 5);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (4, 2);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (4, 1);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (5, 1);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (5, 4);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (5, 2);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (6, 4);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (6, 5);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (7, 2);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (7, 1);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (8, 1);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (8, 4);
INSERT INTO user_to_profession (user_id, profession_id) VALUE (8, 2);
