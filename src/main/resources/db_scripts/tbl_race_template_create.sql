-- table: racetemplate

-- DROP TABLE racetemplate;

CREATE TABLE racetemplate (
    id bigserial PRIMARY KEY,
    race_leg int NOT NULL,
    race_segment int NOT NULL,
    run int NOT NULL,
    van int,
    status varchar(7) check (status in ('default','info','danger')),
    series varchar(5) check (series in ('trail', 'relay')),
    legfactor numeric(3,2)
);
