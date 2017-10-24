-- table: race

-- DROP TABLE race;

-- COPY race TO 'race.csv'
-- COPY race FROM 'race.csv' DELIMITER ',' CSV;

CREATE TABLE race (
    raceid bigserial PRIMARY KEY,
    raceName varchar(50),
    raceType varchar(7) check (raceType in ('trail', 'relay')),
    raceDate date,
    raceWebsite varchar(25),
    createdById varchar(50) NOT NULL,
    createdByName varchar(70) NOT NULL,
    created_at timestamp with time zone default current_timestamp,
    recordstatus varchar(2) default 'A'
);

-- table: raceleg

-- DROP TABLE raceleg;

-- COPY raceleg TO 'raceleg.csv'
-- COPY raceleg FROM 'raceleg.csv' DELIMITER ',' CSV;

CREATE TABLE raceleg (
    racelegid bigserial PRIMARY KEY,
    race_leg int NOT NULL,
    race_segment int NOT NULL,
    run int NOT NULL,
    van int,
    status varchar(25) check (status in ('default', 'info','danger')),
    distance numeric(4,2),
    difficulty varchar(25) check (difficulty in ('Easy', 'Moderate','Hard', 'Very Hard')),
    elevationGain int,
    elevationLoss int,
    relativeDistance numeric(4,2),
    legFactor numeric(3,2),
    raceid bigint references race(raceid),
    created_at timestamp with time zone default current_timestamp,
    recordstatus varchar(2) default 'A'
);

-- table: team

-- DROP TABLE team;

-- COPY team TO 'team.csv'
-- COPY team FROM 'team.csv' DELIMITER ',' CSV;

CREATE TABLE team (
    id varchar(40) PRIMARY KEY,
    teamName varchar(50),
    startTime varchar(8),
    ultra boolean,
    leg_overrides boolean,
    calcMethod varchar(25) check (calcMethod in ('actual', 'relative')) default 'actual',
    totalEstimatedDuration varchar(10),
    totalEstimatedCompletionTime varchar(19),
    totalRelativeEstimatedDuration varchar(10),
    totalRelativeEstimatedCompletionTime varchar(19),
    totalActualDuration varchar(10),
    totalActualCompletionTime varchar(19),
    totalActualVsEstimate varchar(10),
    totalRelativeActualDuration varchar(10),
    totalRelativeActualCompletionTime varchar(19),
    totalActualVsRelative varchar(10),
    race_id varchar(40) references race(id),
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);

-- table: team_secret

-- DROP TABLE team_secret;

-- COPY team_secret TO 'team_secret.csv'
-- COPY team_secret FROM 'team_secret.csv' DELIMITER ',' CSV;

CREATE TABLE team_secret (
    id varchar(40) references team(id) PRIMARY KEY,
    teamSecret varchar(20)
);

-- table: runners

-- DROP TABLE runners;

-- COPY runners TO 'runners.csv'
-- COPY runners FROM 'runners.csv' DELIMITER ',' CSV;

CREATE TABLE runners (
    id varchar(40) PRIMARY KEY,
    runnerName varchar(50),
    pace varchar(5),
    runningorder int,
    team_id varchar(40) references team(id),
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);

-- table: race_legs

-- DROP TABLE team_legs;

-- COPY team_legs TO 'team_legs.csv'
-- COPY team_legs FROM 'team_legs.csv' DELIMITER ',' CSV;

CREATE TABLE team_legs (
    id varchar(40) PRIMARY KEY,
    race_leg int NOT NULL,
    race_segment int NOT NULL,
    run int NOT NULL,
    van int,
    leg_status varchar(25) check (leg_status in ('default', 'info','danger','success')),
    distance numeric(4,2),
    difficulty varchar(25) check (difficulty in ('Easy', 'Moderate','Hard', 'Very Hard')),
    completed boolean,
    overridden boolean,
    elevationGain int,
    elevationLoss int,
    relativeDistance numeric(4,2),
    legFactor numeric(3,2),
    estimatedPace varchar(5),
    estimatedDuration varchar(10),
    estimatedStartTime varchar(19),
    estimatedFinishTime varchar(19),
    estimatedVanTime varchar(19),
    estimatedRelativeDuration varchar(10),
    estimatedRelativeStartTime varchar(19),
    estimatedRelativeFinishTime varchar(19),
    estimatedRelativeVanTime varchar(19),
    actualPace varchar(5),
    actualDuration varchar(10),
    actualStartTime varchar(19),
    actualFinishTime varchar(19),
    actualRelativeDuration varchar(10),
    actualRelativeStartTime varchar(19),
    actualRelativeFinishTime varchar(19),
    runner varchar(40),
    runnerName varchar(50),
    killsId varchar(40),
    kills int,
    teamId varchar(40) references team(id),
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);

-- table: kills

-- DROP TABLE kills;

-- COPY kills TO 'kills.csv'
-- COPY kills FROM 'kills.csv' DELIMITER ',' CSV;

CREATE TABLE kills (
    id varchar(40) PRIMARY KEY,
    raceid varchar(40),
    teamId varchar(40),
    teamName varchar(50),
    teamLeg varchar(40),
    runner varchar(40),
    runnerName varchar(50),
    kills int,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);

-- table: comments

-- DROP TABLE comments;

-- COPY comments TO 'comments.csv'
-- COPY comments FROM 'comments.csv' DELIMITER ',' CSV;

CREATE TABLE comments (
    id varchar(40) PRIMARY KEY,
    raceid varchar(40),
    teamId varchar(40),
    teamName varchar(50),
    teamLeg varchar(40),
    race_leg int,
    user_id varchar(50),
    username varchar(70),
    created_at timestamp with time zone,
    updated_at timestamp with time zone,                
    comment varchar(140)
);

-- table: users_auth

-- DROP TABLE users_auth;

-- COPY users_auth TO 'users_auth.csv'
-- COPY users_auth FROM 'users_auth.csv' DELIMITER ',' CSV;

CREATE TABLE users_auth (
    id varchar(40) PRIMARY KEY,
    user_id varchar(50) NOT NULL,
    username varchar(70),
    role varchar(25) check (role in ('coach', 'runner','fan')),
    raceId varchar(40),
    raceName varchar(50),
    teamId varchar(40),
    teamName varchar(50),
    teamStartTime varchar(8),
    teamSecret varchar(20),
    runnerId varchar(40),
    runnerName varchar(50),
    pace varchar(5),
    coach boolean,
    runner boolean,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);

-- TODO need to run this before next deploy
ALTER TABLE comments ADD race_leg integer;
