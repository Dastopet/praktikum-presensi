package dao;

import db.koneksi;
import exeption.DataAccessException;
import exeption.ValidationException;
import model.BaseEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Pemilik: Koordinator Basis Data. Anggota lain menurunkan, tidak mengubah. */
public abstract class BaseDao<T extends BaseEntity> {

    protected abstract String namaTabel();
    protected abstract String[] kolomData();
    protected abstract void isiParameter(PreparedStatement ps, T objek) throws SQLException;
    protected abstract T dariBaris(ResultSet r) throws SQLException;

    public void simpan(T objek) throws ValidationException, DataAccessException {
        objek.validasi();
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sqlInsert(), Statement.RETURN_GENERATED_KEYS)) {
            isiParameter(ps, objek);
            ps.executeUpdate();
            try (ResultSet kunci = ps.getGeneratedKeys()) {
                if (kunci.next()) {
                    objek.setId(kunci.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal menyimpan data " + namaTabel(), e);
        }
    }

    public boolean ubah(T objek) throws ValidationException, DataAccessException {
        objek.validasi();
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sqlUpdate())) {
            isiParameter(ps, objek);
            ps.setInt(kolomData().length + 1, objek.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataAccessException("gagal mengubah data " + namaTabel(), e);
        }
    }

    public boolean hapus(int id) throws DataAccessException {
        String sql = "DELETE FROM " + namaTabel() + " WHERE id = ?";
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataAccessException("gagal menghapus data " + namaTabel(), e);
        }
    }

    public List<T> ambilSemua() throws DataAccessException {
        String sql = "SELECT * FROM " + namaTabel() + " ORDER BY id";
        List<T> hasil = new ArrayList<T>();
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet r = ps.executeQuery()) {
            while (r.next()) {
                hasil.add(dariBaris(r));
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal membaca daftar " + namaTabel(), e);
        }
        return hasil;
    }

    public T cariId(int id) throws DataAccessException {
        String sql = "SELECT * FROM " + namaTabel() + " WHERE id = ?";
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari data " + namaTabel(), e);
        }
    }

    private String sqlInsert() {
        String[] kolom = kolomData();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(namaTabel()).append(" (");
        for (int i = 0; i < kolom.length; i++) {
            if (i > 0) { sb.append(", "); }
            sb.append(kolom[i]);
        }
        sb.append(") VALUES (");
        for (int i = 0; i < kolom.length; i++) {
            if (i > 0) { sb.append(", "); }
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }

    private String sqlUpdate() {
        String[] kolom = kolomData();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(namaTabel()).append(" SET ");
        for (int i = 0; i < kolom.length; i++) {
            if (i > 0) { sb.append(", "); }
            sb.append(kolom[i]).append(" = ?");
        }
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    public String lihatSqlInsert() { return sqlInsert(); }
    public String lihatSqlUpdate() { return sqlUpdate(); }
}