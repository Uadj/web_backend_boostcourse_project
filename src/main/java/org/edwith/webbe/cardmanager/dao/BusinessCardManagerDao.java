package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
    private static String dburl = "jdbc:mysql://localhost:3306/sample?useUnicode=true&serverTimezone=Asia/Seoul";
    private static String dbUser = "root";
    private static String dbpasswd = "1234";

    public List<BusinessCard> searchBusinessCard(String keyword){
            List<BusinessCard> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "SELECT name, phone, companyName, createDate FROM BusinessCard WHERE name LIKE ?";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
             PreparedStatement ps = conn.prepareStatement(sql)) {
             String names = '%' + keyword + '%';
             ps.setString(1, names);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String name = rs.getString(1);
                    String phone = rs.getString(2);
                    String companyName =  rs.getString(3);
                    BusinessCard businessCard = new BusinessCard(name, phone, companyName);
                    list.add(businessCard); // list에 반복할때마다 businessCard 생성하여 list에 추가한다.
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


    public BusinessCard addBusinessCard(BusinessCard businessCard){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO BusinessCard (name, phone, companyName, createDate) VALUES ( ?, ?, ?,? )";

        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
            java.util.Date utilDate = businessCard.getCreateDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate(4, sqlDate);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return businessCard;
    }

}
