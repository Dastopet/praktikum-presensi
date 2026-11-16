package konsol;

import db.koneksi;
import exeption.DataAccessException;
import exeption.ValidationException;
import model.Presensi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class presensikonsol {

    public void simpan(Presensi p) throws ValidationException, DataAccessException {
        p.validasi();
        String sql = "INSERT INTO presensi (id_pertemuan, id_mahasiswa, status) VALUES (?, ?, ?)";
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getIdPertemuan());
            ps.setInt(2, p.getIdMahasiswa());
            ps.setString(3, p.getStatus());
            ps.executeUpdate();
            try (ResultSet kunci = ps.getGeneratedKeys()) {
                if (kunci.next()) {
                    p.setId(kunci.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal menyimpan data presensi", e);
        }
    }

    public List<Presensi> ambilSemua() throws DataAccessException {
        String sql = "SELECT id, id_pertemuan, id_mahasiswa, status FROM presensi ORDER BY id";
        List<Presensi> hasil = new ArrayList<Presensi>();
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet r = ps.executeQuery()) {
            while (r.next()) {
                hasil.add(dariBaris(r));
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal membaca daftar presensi", e);
        }
        return hasil;
    }

    public Presensi cariId(int id) throws DataAccessException {
        String sql = "SELECT id, id_pertemuan, id_mahasiswa, status FROM presensi WHERE id = ?";
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari data presensi", e);
        }
    }

    public boolean ubah(Presensi p) throws ValidationException, DataAccessException {
        p.validasi();
        String sql = "UPDATE presensi SET id_pertemuan = ?, id_mahasiswa = ?, status = ? WHERE id = ?";
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getIdPertemuan());
            ps.setInt(2, p.getIdMahasiswa());
            ps.setString(3, p.getStatus());
            ps.setInt(4, p.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataAccessException("gagal mengubah data presensi", e);
        }
    }

    public boolean hapus(int id) throws DataAccessException {
        String sql = "DELETE FROM presensi WHERE id = ?";
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataAccessException("gagal menghapus data presensi", e);
        }
    }

    private Presensi dariBaris(ResultSet r) throws SQLException {
        Presensi p = new Presensi();
        p.setId(r.getInt("id"));
        p.setIdPertemuan(r.getInt("id_pertemuan"));
        p.setIdMahasiswa(r.getInt("id_mahasiswa"));
        p.setStatus(r.getString("status"));
        return p;
    }
}