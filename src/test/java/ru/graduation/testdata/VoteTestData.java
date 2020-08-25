package ru.graduation.testdata;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final int VOTE1_ID = START_SEQ + 23;

    public static final LocalDateTime TIME_AFTER_VOTING = LocalDateTime.of(2020, Month.AUGUST, 15, 11, 5);
    public static final LocalDateTime ALLOWED_VOTING_TIME = LocalDateTime.of(2020, Month.AUGUST, 12, 10, 12);
}
