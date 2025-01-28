package db.interfaces;

import models.City;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ICityDatabase {
    void connect(String connectionUrl, String user, String pass) throws SQLException,ClassNotFoundException;

    ArrayList<City> getAllCities() throws SQLException;
    String getCityById(int id)throws SQLException;

}
