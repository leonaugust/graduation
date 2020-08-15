package ru.graduation;

import ru.graduation.model.Vote;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER =
            TestMatcher.usingFieldsComparator(Vote.class, "restaurant", "user");

    public static final int NOT_FOUND = 10;
    public static final int VOTE1_ID = START_SEQ + 20;
    public static final int VOTE2_ID = START_SEQ + 21;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, of(2020, Month.AUGUST, 14, 16, 37));
    public static final Vote VOTE2 = new Vote(VOTE2_ID, of(2020, Month.AUGUST, 14, 17, 20));

    public static Vote getNew() {
        return new Vote(null, of(2000, Month.OCTOBER, 31, 1, 30));
    }
}
