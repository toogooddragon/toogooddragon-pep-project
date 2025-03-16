package DAO;

import java.sql.PreparedStatement;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    
    public Account addAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "insert into account (username, password) values (?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            ResultSet pkrs = ps.getGeneratedKeys();
            if(pkrs.next()){
                int ID = pkrs.getInt(1);
                return new Account(ID, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "select * from account where username = ? and password = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account account2 = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account2;
            }   


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

        
    }
}
