package db;

import exeption.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/db_presensi"
            + "?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String PENGGUNA = "root";
    private static final String SANDI = "";

    private koneksi() {
    }

    public static Connection buka() throws DataAccessException {
        try {
            return DriverManager.getConnection(URL, PENGGUNA, SANDI);
        } catch (SQLException e) {
            throw new DataAccessException("koneksi ke basis data gagal dibuka", e);
        }
    }
}