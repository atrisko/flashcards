# Architekturplan: Lernkarten-App (Java CLI)

Dieses Dokument fasst die gemeinsam erarbeiteten Design-Entscheidungen zusammen.
Es dient als Grundlage für die Implementierung.

---

## 1. Domain Model

```
Card  (abstract class)
├── Fields: id, question, subject, createdAt, updatedAt
└── Abstract method: checkAnswer(userInput) → boolean/Result

    ├── MultipleChoiceCard  extends Card
    │   └── Fields: List<AnswerOption>  (text + isCorrect flag)
    │   └── checkAnswer() → automatic comparison
    │
    └── OpenQuestion  extends Card
        └── Fields: sampleAnswer (String)
        └── checkAnswer() → shows sample answer, user decides

AnswerOption  (helper class)
├── Fields: text (String), isCorrect (boolean)
```

> **Note:** Statistics fields (correct/wrong, Leitner box etc.) do **not** belong
> to the Card itself — they live in user-specific progress (see DB schema).

---

## 2. Layer Architecture

```
┌─────────────────────────────┐
│        CLI Layer            │  ← User interaction, input/output
├─────────────────────────────┤
│      Service Layer          │
│  ├── UserService            │  ← Login, registration, authentication
│  ├── CardService            │  ← Add, edit, delete cards
│  ├── QuizService            │  ← Orchestrate session, select cards
│  ├── LeitnerService         │  ← Pure scheduling logic (box + next date)
│  └── ReportingService       │  ← Session summary, stats
├─────────────────────────────┤
│     Repository Layer        │
│  └── CardRepository         │  ← Interface ("socket")
│      └── SQLiteCardRepo     │  ← First implementation
│      └── PostgresCardRepo   │  ← Later implementation (optional)
├─────────────────────────────┤
│        Database             │  ← SQLite (local, no server required)
└─────────────────────────────┘
```

> **Key principle:** Each layer only knows the layer directly below it.
> `QuizService` only knows a `CardRepository` exists —
> not whether SQLite or PostgreSQL is behind it.
>
> **Note on `LeitnerService`:** Pure business logic — no DB access.
> Takes `UserCardProgress` as input, returns updated box + `next_review_at`.
> Used by `QuizService`. Easily unit-testable and reusable.

---

## 3. Database Schema

### Table: `user`
| Column        | Type     | Description                               |
|---------------|----------|-------------------------------------------|
| id            | INTEGER  | Primary Key                               |
| username      | TEXT     | Unique (UNIQUE)                           |
| password_hash | TEXT     | BCrypt hash (no plaintext, no MD5!)       |
| language      | TEXT     | `"DE"` or `"EN"` (for i18n via ResourceBundle) |
| created_at    | DATETIME |                                           |

### Table: `flashcard` (base)
| Column     | Type     | Description                               |
|------------|----------|-------------------------------------------|
| id         | INTEGER  | Primary Key                               |
| type       | TEXT     | `"MC"` or `"OPEN"` (discriminator)        |
| question   | TEXT     | The question                              |
| subject    | TEXT     | Assigned subject/learning field           |
| created_by | INTEGER  | Foreign Key → `user.id` (owner)           |
| is_public  | BOOLEAN  | Visible to all users? (default: false)    |
| created_at | DATETIME |                                           |
| updated_at | DATETIME |                                           |

### Table: `flashcard_open`
| Column        | Type    | Description                               |
|---------------|---------|-------------------------------------------|
| card_id       | INTEGER | Foreign Key → `flashcard.id`              |
| sample_answer | TEXT    | Reference answer for self-evaluation      |

### Table: `flashcard_mc`
*(Link to base only — specific data in `answer_options`)*
| Column  | Type    | Description                               |
|---------|---------|-------------------------------------------|
| card_id | INTEGER | Foreign Key → `flashcard.id`              |

### Table: `answer_options` (1:n per MC card)
| Column     | Type    | Description                               |
|------------|---------|-------------------------------------------|
| id         | INTEGER | Primary Key                               |
| card_id    | INTEGER | Foreign Key → `flashcard.id`              |
| text       | TEXT    | Answer text                               |
| is_correct | BOOLEAN | Multiple correct answers possible         |

### Table: `user_card_progress` (n:m junction)
| Column           | Type     | Description                           |
|------------------|----------|---------------------------------------|
| user_id          | INTEGER  | Foreign Key → `user.id`               |
| card_id          | INTEGER  | Foreign Key → `flashcard.id`          |
| correct_count    | INTEGER  | User-specific                         |
| wrong_count      | INTEGER  | User-specific                         |
| leitner_box      | INTEGER  | Box 1–5 (for Leitner algorithm)       |
| next_review_at   | DATETIME | For Leitner repetition interval       |

---

## 4. Tools & Abhängigkeiten

| Tool / Library    | Zweck                                               |
|-------------------|-----------------------------------------------------|
| **Maven**         | Build-Tool, Dependency-Management                   |
| **SQLite JDBC**   | Datenbanktreiber für SQLite                         |
| **Flyway**        | Versionierte DB-Migrationen (Schema-Änderungen)     |
| **jBCrypt**       | Sichere Passwort-Hashing-Bibliothek                 |
| **Picocli**       | CLI-Argument-Parsing (Flags, Optionen, Subcommands) |
| **JUnit 5**       | Unit-Tests                                          |
| **SLF4J + Logback**| Logging (statt `System.out.println` überall)       |

---

## 5. Planned Features (prioritized)

### Phase 1 – MVP (CLI, single user)
- [ ] Implement domain model (`Card`, `MultipleChoiceCard`, `OpenQuestion`, `AnswerOption`)
- [ ] Set up database schema (SQLite + Flyway)
- [ ] `CardRepository` interface + SQLite implementation
- [ ] Card CRUD (add, view, edit, delete)
- [ ] Quiz mode (count, subject filter, mixed mode)
- [ ] Session reporting (summary at end)
- [ ] Default user (no login needed, but schema is multi-user-ready)

### Phase 1.5 – Authentication
- [ ] Activate `user` table (registration, login)
- [ ] Implement BCrypt password hashing (jBCrypt)
- [ ] Session context (which user is logged in?)
- [ ] i18n via `ResourceBundle` (`messages_de.properties` / `messages_en.properties`)
- [ ] Store `language` preference in `user` table

### Phase 2 – Leitner Algorithm
- [ ] Introduce card box system (boxes 1–5)
- [ ] Adapt quiz logic to Leitner principle
- [ ] Use `next_review_at` and `leitner_box` from `user_card_progress`

### Phase 3 – UI (optional, lowest priority)
- [ ] Decide technology: Swing / JavaFX / web frontend
- [ ] Repository pattern allows easy reuse of existing service logic
