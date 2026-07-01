package com.flashcards.repository;

import com.flashcards.model.AnswerOption;
import com.flashcards.model.Card;
import com.flashcards.model.OpenQuestionCard;
import com.flashcards.model.MultipleChoiceCard;
import com.flashcards.repository.db.ConnectionHelper;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class SQLiteCardRepo implements CardRepository {

    @Override
    public Optional<Card> findById(int id) {
        try (
                Connection conn = ConnectionHelper.getConnection();
                PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM flashcards WHERE id = ?");

        ) {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }

            Card card = null;

            String type = rs.getString("type");

            // logic for OpenQuestionCard
            if ("OPEN".equals(type)) {
                try (
                        PreparedStatement stmnt2 = conn
                                .prepareStatement("SELECT sample_answer FROM flashcards_open WHERE card_id = ?")) {
                    stmnt2.setInt(1, id);
                    ResultSet rs2 = stmnt2.executeQuery();
                    rs2.next();
                    String sampleAnswer = rs2.getString("sample_answer");
                    card = new OpenQuestionCard(
                            rs.getString("question"),
                            rs.getString("subject"),
                            sampleAnswer);

                }

                // Set other fields
                if (rs.getString("created_at") != null) {
                    card.setCreatedAt(LocalDateTime.parse(rs.getString("created_at")));
                }
                if (rs.getString("updated_at") != null) {
                    card.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at")));
                }

            }

            // logic for MultipleChoiceCard
            else if ("MC".equals(type)) {
                MultipleChoiceCard mcCard = new MultipleChoiceCard(
                        rs.getString("question"),
                        rs.getString("subject"));

                // Fetch answer options for this MC card
                try (PreparedStatement stmnt3 = conn
                        .prepareStatement("SELECT * FROM flashcards_mc_answer_options WHERE card_id = ?")) {
                    stmnt3.setInt(1, id);
                    try (ResultSet rs3 = stmnt3.executeQuery()) {
                        while (rs3.next()) {
                            String text = rs3.getString("text");
                            boolean isCorrect = rs3.getBoolean("is_correct");
                            mcCard.addAnswerOption(text, isCorrect);
                        }
                    }
                }

                // Set other fields
                if (rs.getString("created_at") != null) {
                    mcCard.setCreatedAt(LocalDateTime.parse(rs.getString("created_at")));
                }
                if (rs.getString("updated_at") != null) {
                    mcCard.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at")));
                }

                card = mcCard;

            }

            return Optional.ofNullable(card);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Card> findAll() {
        try (
                Connection conn = ConnectionHelper.getConnection();
                PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM flashcards");
                ResultSet rs = stmnt.executeQuery()) {
            List<Card> cards = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");

                // Logic for OpenQuestionCard
                if ("OPEN".equals(type)) {
                    try (PreparedStatement stmnt2 = conn
                            .prepareStatement("SELECT sample_answer FROM flashcards_open WHERE card_id = ?")) {
                        stmnt2.setInt(1, id);
                        try (ResultSet rs2 = stmnt2.executeQuery()) {
                            if (rs2.next()) {
                                String sampleAnswer = rs2.getString("sample_answer");
                                cards.add(new OpenQuestionCard(
                                        rs.getString("question"),
                                        rs.getString("subject"),
                                        sampleAnswer));
                            }
                        }
                    }
                }

                // Logic for MultipleChoiceCard
                else if ("MC".equals(type)) {
                    MultipleChoiceCard mcCard = new MultipleChoiceCard(
                            rs.getString("question"),
                            rs.getString("subject"));

                    // Fetch answer options for this MC card
                    try (PreparedStatement stmnt3 = conn
                            .prepareStatement("SELECT * FROM flashcards_mc_answer_options WHERE card_id = ?")) {
                        stmnt3.setInt(1, id);
                        try (ResultSet rs3 = stmnt3.executeQuery()) {
                            while (rs3.next()) {
                                String text = rs3.getString("text");
                                boolean isCorrect = rs3.getBoolean("is_correct");
                                mcCard.addAnswerOption(text, isCorrect);
                            }
                        }
                    }
                    cards.add(mcCard);
                }
            }

            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Card> findBySubject(String subject) {

        try (
                Connection conn = ConnectionHelper.getConnection();
                PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM flashcards WHERE subject = ?")) {
            List<Card> cards = new ArrayList<>();

            stmnt.setString(1, subject);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");

                // Logic for OpenQuestionCard
                if ("OPEN".equals(type)) {
                    try (PreparedStatement stmnt2 = conn
                            .prepareStatement("SELECT sample_answer FROM flashcards_open WHERE card_id = ?")) {
                        stmnt2.setInt(1, id);
                        try (ResultSet rs2 = stmnt2.executeQuery()) {
                            if (rs2.next()) {
                                String sampleAnswer = rs2.getString("sample_answer");
                                OpenQuestionCard oqCard = new OpenQuestionCard(
                                        rs.getString("question"),
                                        rs.getString("subject"),
                                        sampleAnswer);
                                //Fetch other fields
                                if (rs.getString("created_at") != null) {
                                    oqCard.setCreatedAt(LocalDateTime.parse(rs.getString("created_at")));
                                }
                                if (rs.getString("updated_at") != null) {
                                    oqCard.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at")));
                                }
                                cards.add(oqCard);
                            }
                        }
                    }
                }

                // Logic for MultipleChoiceCard
                else if ("MC".equals(type)) {
                    MultipleChoiceCard mcCard = new MultipleChoiceCard(
                            rs.getString("question"),
                            rs.getString("subject"));

                    // Fetch answer options for this MC card
                    try (PreparedStatement stmnt3 = conn
                            .prepareStatement("SELECT * FROM flashcards_mc_answer_options WHERE card_id = ?")) {
                        stmnt3.setInt(1, id);
                        try (ResultSet rs3 = stmnt3.executeQuery()) {
                            while (rs3.next()) {
                                String text = rs3.getString("text");
                                boolean isCorrect = rs3.getBoolean("is_correct");
                                mcCard.addAnswerOption(text, isCorrect);
                            }
                        }
                    }

                    //Set other fields
                    if (rs.getString("created_at") != null) {
                        mcCard.setCreatedAt(LocalDateTime.parse(rs.getString("created_at")));
                    }
                    if (rs.getString("updated_at") != null) {
                        mcCard.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at")));
                    }

                    cards.add(mcCard);
                }
            }

            return cards;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Card card) {
        // Logic to insert a card into the database
        Connection conn = null;
        try {
            conn = ConnectionHelper.getConnection();

            conn.setAutoCommit(false);
            try (
                    PreparedStatement stmnt = conn.prepareStatement("""
                            INSERT INTO flashcards
                            (type, question, subject, created_by, created_at)
                            values (?,?,?,?,?)""",
                            Statement.RETURN_GENERATED_KEYS)) {
                stmnt.setString(1, card.getType());
                stmnt.setString(2, card.getQuestion());
                stmnt.setString(3, card.getSubject());
                stmnt.setLong(4, card.getCreatedBy());
                stmnt.setString(5, card.getCreatedAt().toString());
                int rowsAffected = stmnt.executeUpdate();

                if (rowsAffected == 0)
                    throw new SQLException("Failed to create flashcard");

                // get generated key
                ResultSet generatedKeys = stmnt.getGeneratedKeys();
                generatedKeys.next();
                int key = generatedKeys.getInt(1);

                // Logic for OpenQuestionCard
                if (card instanceof OpenQuestionCard) {
                    try (PreparedStatement stmnt2 = conn.prepareStatement("""
                            INSERT INTO flashcards_open
                            (card_id, sample_answer)
                            VALUES (?,?)""")) {
                        stmnt2.setInt(1, key);
                        stmnt2.setString(2, ((OpenQuestionCard) card).getAnswer());
                        stmnt2.execute();
                    }
                } else if (card instanceof MultipleChoiceCard) {
                    for (AnswerOption answer : ((MultipleChoiceCard) card).getAnswerOptions()) {
                        try (PreparedStatement stmnt3 = conn.prepareStatement("""
                                INSERT INTO flashcards_mc_answer_options
                                (card_id,text,is_correct)
                                VALUES (?,?,?)""")) {
                            stmnt3.setInt(1, key);
                            stmnt3.setString(2, answer.getText());
                            stmnt3.setBoolean(3, answer.isCorrect());
                            stmnt3.execute();
                        }
                    }
                }
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                }

                catch (SQLException rollbacke) {
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException finallye) {
                }
            }
        }

    }

    @Override
    public void update(Card card) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
