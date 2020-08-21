package ru.graduation.testdata;

import ru.graduation.TestMatcher;
import ru.graduation.model.Vote;

import java.time.LocalDateTime;
import java.time.Month;

import static java.time.LocalDate.of;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER =
            TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class, "restaurant", "user");

    public static final int NOT_FOUND = 10;
    public static final int VOTE1_ID = START_SEQ + 23;
    public static final int VOTE2_ID = START_SEQ + 24;
    public static final int VOTE3_ID = START_SEQ + 25;
    public static final int VOTE4_ID = START_SEQ + 26;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, of(2020, Month.AUGUST, 14));
    public static final Vote VOTE2 = new Vote(VOTE2_ID, of(2020, Month.AUGUST, 14));
    public static final Vote VOTE3 = new Vote(VOTE3_ID, of(2020, Month.AUGUST, 14));
    public static final Vote VOTE4 = new Vote(VOTE4_ID, of(2020, Month.AUGUST, 14));

    public static final LocalDateTime TIME_AFTER_VOTING = LocalDateTime.of(2020, Month.AUGUST, 15, 11, 5);
    public static final LocalDateTime ALLOWED_VOTING_TIME = LocalDateTime.of(2020, Month.AUGUST, 12, 10, 12);
}
