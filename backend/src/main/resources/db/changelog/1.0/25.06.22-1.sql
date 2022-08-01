DROP TABLE recommendation_form_question;
DROP TABLE recommendation_form;

DROP TYPE recommendation_form_status;

CREATE TYPE survey_status AS ENUM
(
  'OPEN',
  'PENDING',
  'FINISHED',
  'CLOSED'
);

CREATE TYPE recommendation_status AS ENUM
(
  'OPEN',
  'PENDING',
  'CLOSED'
);

CREATE TABLE survey_template
(
    template_id bigserial primary key,
    recruiter_id bigint not null references recruiter(recruiter_id),
    template_name varchar(150) not null
);

CREATE TABLE template_question
(
    question_id bigserial primary key,
    template_id bigint not null references survey_template(template_id),
    question text not null
);

CREATE TABLE survey
(
    survey_id bigserial primary key,
    recruiter_id bigint not null references recruiter(recruiter_id),
    status survey_status not null default 'OPEN',
    creation_time timestamp default now(),
    update_time timestamp default now()
);

CREATE TABLE candidate
(
    candidate_id bigint primary key not null references survey(survey_id),
    candidate_first_name varchar(150) not null,
    candidate_last_name varchar(150) not null,
    candidate_email varchar(100) not null
);

CREATE TABLE recommendation
(
    recommendation_id bigserial primary key,
    survey_id bigint not null references survey(survey_id),
    status recommendation_status not null default 'OPEN'
);

CREATE TABLE recommender
(
    recommender_id bigint primary key not null references recommendation(recommendation_id),
    recommender_first_name varchar(150) not null,
    recommender_last_name varchar(150) not null,
    recommender_email varchar(100) not null
);
	
CREATE TABLE survey_question
(
    question_id bigserial primary key,
    survey_id bigint not null references survey(survey_id),
    question text not null
);	
	
CREATE TABLE answer
(
    answer_id bigserial primary key,
    question_id bigint not null references survey_question(question_id),
    recommendation_id bigint not null references recommendation(recommendation_id),
    answer text,
    unique(question_id, recommendation_id)
);
