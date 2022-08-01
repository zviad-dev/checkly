CREATE TABLE person
(
    person_id bigserial primary key,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    password text not null,
    email varchar(100) not null unique,
    registration_time timestamp default now()
);

CREATE TABLE recruiter
(
    recruiter_id bigint primary key not null references person(person_id),
    company_name varchar(150) not null
);

CREATE TYPE recommendation_form_status AS ENUM
(
  'OPEN',
  'PENDING',
  'CLOSED'
);

CREATE TABLE recommendation_form
(
    recommendation_form_id bigserial primary key,
    recruiter_id bigint not null references recruiter(recruiter_id),
    creation_time timestamp default now(),
    update_time timestamp default now(),
    status recommendation_form_status not null default 'OPEN',
    candidate_first_name varchar(150) not null,
    candidate_last_name varchar(150) not null,
    recommender_email varchar(100) not null
);

CREATE TABLE recommendation_form_question
(
    recommendation_form_question_id bigserial primary key,
    recommendation_form_id bigint not null references recommendation_form(recommendation_form_id),
    question text not null,
    answer text
);

