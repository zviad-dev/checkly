ALTER TABLE survey
ADD position_name varchar(150) not null default '';

UPDATE survey
SET position_name = 'Бэкенд разработчик'
WHERE survey_id = 1;

UPDATE survey
SET position_name = 'Финансовый директор'
WHERE survey_id = 2;

UPDATE survey
SET position_name = 'Директор по развитиию'
WHERE survey_id = 3;

UPDATE survey
SET position_name = 'QA'
WHERE survey_id = 4;

UPDATE survey
SET position_name = 'Фронтенд разработчик'
WHERE survey_id = 5;

UPDATE survey
SET position_name = 'Тимлид'
WHERE survey_id = 6;

UPDATE survey
SET position_name = 'СТО'
WHERE survey_id = 7;

UPDATE survey
SET position_name = 'Бэкенд разработчик'
WHERE survey_id = 8;


