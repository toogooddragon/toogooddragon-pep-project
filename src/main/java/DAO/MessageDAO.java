package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message createMessage(Message message){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            while(rs.next()){
                int ID = rs.getInt(1);
                return new Message(ID, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message getMessage(int id){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "select * from message where message_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean accountExists(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where account_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from message";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));            
            }
        
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message deleteMessage(int id){
        Connection conn = ConnectionUtil.getConnection();
        Message msg = this.getMessage(id);
        if(msg != null){
            try{
            String sql = "delete from message where message_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            return msg;
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    public Message updateMessage(String text, int id){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            return this.getMessage(id);
        
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllAccountMessagesByID(int accountID){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from message where posted_by = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));            
            }
        
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
