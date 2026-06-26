CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    language TEXT,
    created_at DATETIME
);

CREATE TABLE flashcards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT NOT NULL,
    question TEXT NOT NULL,
    subject TEXT,
    created_by INTEGER NOT NULL,
    is_public BOOLEAN DEFAULT false,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE flashcards_open (
    card_id INTEGER PRIMARY KEY,
    sample_answer TEXT NOT NULL,
    FOREIGN KEY (card_id) REFERENCES flashcards(id)
);

CREATE TABLE flashcards_mc (
    card_id INTEGER PRIMARY KEY,
    FOREIGN KEY (card_id) REFERENCES flashcards(id)
);

CREATE TABLE flashcards_mc_answer_options (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    card_id INTEGER NOT NULL,
    text TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (card_id) REFERENCES flashcards_mc(card_id)
);

CREATE TABLE user_card_progress (
    user_id INTEGER NOT NULL,
    card_id INTEGER NOT NULL,
    correct_count INTEGER NOT NULL DEFAULT 0,
    wrong_count INTEGER NOT NULL DEFAULT 0,
    leitner_box INTEGER NOT NULL DEFAULT 1,
    next_review_at DATETIME NOT NULL,
    PRIMARY KEY (card_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (card_id) REFERENCES flashcards(id)
);
