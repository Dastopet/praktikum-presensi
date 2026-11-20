package dao;

import db.koneksi;
import exeption.DataAccessException;
import model.Presensi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Pemilik: Rizky Ramadhan (6304250057). */
public class PresensiDao extends BaseDao<Presensi> {

    @Override
    protected String namaTabel() {
        return "presensi";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"id_pertemuan", "id_mahasiswa", "status"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Presensi p) throws SQLException {
        // Urutan wajib sama dengan kolomData()
        ps.setInt(1, p.getIdPertemuan());
        ps.setInt(2, p.getIdMahasiswa());
        ps.setString(3, p.getStatus());
    }

    @Override
    protected Presensi dariBaris(ResultSet r) throws SQLException {
        Presensi p = new Presensi();
        p.setId(r.getInt("id"));
        p.setIdPertemuan(r.getInt("id_pertemuan"));
        p.setIdMahasiswa(r.getInt("id_mahasiswa"));
        p.setStatus(r.getString("status"));
        return p;
    }

    /** Method pencarian khas modul presensi berdasarkan ID Pertemuan. */
    public List<Presensi> cariPertemuan(int idPertemuan) throws DataAccessException {
        String sql = "SELECT * FROM presensi WHERE id_pertemuan = ? ORDER BY id";
        List<Presensi> hasil = new ArrayList<Presensi>();
        try (Connection c = koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPertemuan);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    hasil.add(dariBaris(r));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari presensi berdasarkan pertemuan", e);
        }
        return hasil;
    }
}