CREATE TABLE event
(
    id                uuid         not null
        constraint event_pkey primary key,
    created           timestamp    not null,
    message           varchar(300) not null,
    viewed            boolean      not null,
    person_id         bigint       not null references person (person_id),
    recommendation_id bigint references recommendation (recommendation_id),
    survey_id         bigint references survey (survey_id)
);

CREATE INDEX event_person_id_viewed_index ON event (person_id, viewed);
