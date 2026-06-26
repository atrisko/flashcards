# Todo – Flashcard App

Active task list. Architecture decisions → see `Planning.md`.

---

## 🚧 Step 0 – Project Setup (Maven)
- [x] Configure Java version in `pom.xml`
- [x] Add required dependencies (SQLite JDBC, Flyway, jBCrypt, Picocli, JUnit 5, SLF4J + Logback)

---

## ✅ Step 1 – Domain Model (Walking Skeleton)
- [x] Create abstract class `Card` (fields: id, question, subject, createdAt, updatedAt)
- [x] Define abstract method `checkAnswer()`
- [x] Implement `MultipleChoiceCard` (incl. `checkAnswer()` → automatic comparison)
- [x] Implement `OpenQuestion` (incl. `checkAnswer()` → show sample answer, user decides)
- [x] Create helper class `AnswerOption` (text + isCorrect)

---

## ✅ Step 2 – Test Core Quiz (hardcoded, no service needed)
- [x] In `Main.java`: hardcode one `MultipleChoiceCard` and one `OpenQuestion`
- [x] Simple CLI loop: show question → read input → call `checkAnswer()` → print result
- [x] Manual test: does the core logic work for both types?

---

## 🗄️ Step 3 – Database
- [x] Set up Flyway (create migrations folder)
- [x] Write SQL migration: create all tables (`user`, `flashcard`, `flashcard_open`, `flashcard_mc`, `answer_options`, `user_card_progress`)
- [ ] Set up SQLite connection (connection helper)
- [ ] Define `CardRepository` interface
- [ ] Implement `SQLiteCardRepository`

---

## 🃏 Step 4 – Card Service (CRUD)
- [ ] Implement `CardService` (add, view, edit, delete)
- [ ] CLI menu for card management

---

## 🎯 Step 5 – Quiz Service (complete)
- [ ] Implement `QuizService`
- [ ] User can configure: card count, subject filter, mixed mode
- [ ] Replace hardcoded cards with real DB queries

---

## 📊 Step 6 – Reporting
- [ ] Implement `ReportingService`
- [ ] Session summary (correct/wrong, score)

---

## 🔁 Step 7 – Leitner Algorithm
- [ ] Introduce box system (1–5)
- [ ] Adapt quiz logic to Leitner principle (`next_review_at`, `leitner_box`)

---

## 👥 Step 8 – Multi-User / Authentication
- [ ] Implement registration & login (`UserService`)
- [ ] BCrypt password hashing (jBCrypt)
- [ ] Session context (which user is logged in?)
- [ ] i18n: set up `ResourceBundle` (`messages_de.properties` / `messages_en.properties`)
- [ ] Ask language preference at registration, store in `user.language`

---

## 🖥️ Step 9 – UI (lowest priority)
- [ ] Decide technology (Swing / JavaFX / web)
- [ ] Build UI on top of existing service layer
