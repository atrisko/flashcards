package com.flashcards.repository;

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

public class SQLiteCardRepo implements CardRepository {
    
    @Override
    public Optional<Card> findById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    
    @Override
    public List<Card> findAll() {
        try (Connection conn = ConnectionHelper.getConnection();
        PreparedStatement stmnt =conn.prepareStatement("SELECT * FROM flashcards");
        ResultSet rs = stmnt.executeQuery()){
            List<Card> cards = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");

                //Logic for OpenQuestionCard
                if ("OPEN".equals(type)) {
                    try (PreparedStatement stmnt2 =
                    conn.prepareStatement("SELECT sample_answer FROM flashcards_open WHERE CARD_ID = ?")){
                        stmnt2.setInt(1, id);
                        try(ResultSet rs2 = stmnt2.executeQuery()){
                            if (rs2.next()) {
                                String sampleAnswer = rs2.getString("sample_answer");
                                cards.add(new OpenQuestionCard(
                                    rs.getString("question"),
                                    rs.getString("subject"),
                                    sampleAnswer
                                ));
                            }
                        }
                    }
                }

                //Logic for MultipleChoiceCard
                else if ("MC".equals(type)) {
                    MultipleChoiceCard mcCard = new MultipleChoiceCard(
                        rs.getString("question"), 
                        rs.getString("subject")
                    );

                    //Fetch answer options for this MC card
                    try (PreparedStatement stmnt3 = 
                    conn.prepareStatement("SELECT * FROM flashcards_mc_answer_options WHERE CARD_ID = ?")){
                        stmnt3.setInt(1, id);
                        try (ResultSet rs3 = stmnt3.executeQuery()){
                            while (rs3.next()){
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
        throw new UnsupportedOperationException("Unimplemented method 'findBySubject'");
    }

    @Override
    public void save(Card card) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
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
