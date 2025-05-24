package com.example.demo.Config.Admin;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
@Service
public interface IDBAdmin {
    boolean authenticate(String username, String password) throws SQLException;
}