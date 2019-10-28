package com.wildcodeschool.wildandwizard.controller;

import com.wildcodeschool.wildandwizard.entity.Wizard;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WizardController {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "leshorcruxescestlavie";

    @GetMapping("/wizards")
    public String getWizardsByFamily(Model model,
                                     @RequestParam(defaultValue = "%") String family) {

        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM wizard WHERE last_name LIKE ?"
            );
            statement.setString(1, family);
            ResultSet resultSet = statement.executeQuery();

            List<Wizard> wizards = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                wizards.add(new Wizard(id, firstName, lastName));
            }

            model.addAttribute("wizards", wizards);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "wizards";
    }
}
