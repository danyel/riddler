package be.riddler.v1.fixture;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;

/**
 * Fixture
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public class Fixture {
    public static class Question {
        public static final UUID QUESTION_ID = fromString("11111111-1111-1111-1111-111111111111");
        public static final UUID DB_ID = fromString("a2b20b03-9d96-41d4-8e83-a35b21c21fe7");

    }

    public static class Answer {
        public static final UUID ANSWER_ID = fromString("22222222-2222-2222-2222-222222222222");
    }

    public static class Category {
        public static final UUID CATEGORY_ID = fromString("22222222-2222-2222-2222-222222222222");
    }

    public static class Keyword {
        public static final UUID KEYWORD_ID = fromString("44444444-4444-4444-4444-444444444444");
    }

    public static class Option {
        public static final UUID OPTION_ID = fromString("66666666-6666-6666-6666-666666666666");
    }

    public static class Invitation {
        public static final UUID INVITATION_ID = fromString("d376b894-b259-4714-87c6-cf411e5b10db");
    }

    public static class Publication {
        public static final UUID PUBLICATION_ID = fromString("99999999-9999-9999-9999-999999999999");
    }

    public static class Position {
        public static final UUID POSITION_ID = fromString("00000000-0000-0000-0000-000000000000");
    }

    public static class Level {
        public static final UUID LEVEL_ID = fromString("4c4c6785-3149-44f3-8888-6ebcc8722d72");

    }

    public static class Participant {
        public static final UUID PARTICIPANT_ID = fromString("dbc132cc-b918-40fa-b2e9-81032635c4e8");

        public static final be.riddler.v1.participant.client.model.Participant PARTICIPANT = new be.riddler.v1.participant.client.model.Participant(
                PARTICIPANT_ID,
                "firstName",
                "lastName",
                "user@email.net",
                "photo",
                "cv",
                List.of()
        );
    }

    public static class Solution {
        public static final UUID SOLUTION_ID = fromString("55555555-5555-5555-5555-555555555555");
    }

    public static class Bookmark {
        public static final UUID BOOKMARK_ID = fromString("04c462f3-e79b-484f-9ee8-0222ced10a8e");
    }
}
