package model;

import exeption.ValidationException;

public class Presensi extends BaseEntity {
    private int idPertemuan;
    private int idMahasiswa;
    private String status;

    public Presensi() {
    }

    public Presensi(int idPertemuan, int idMahasiswa, String status) {
        this.idPertemuan = idPertemuan;
        this.idMahasiswa = idMahasiswa;
        this.status = status;
    }

    public int getIdPertemuan() { return idPertemuan; }
    public void setIdPertemuan(int idPertemuan) { this.idPertemuan = idPertemuan; }

    public int getIdMahasiswa() { return idMahasiswa; }
    public void setIdMahasiswa(int idMahasiswa) { this.idMahasiswa = idMahasiswa; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String label() {
        return "Pertemuan ID: " + idPertemuan + " | Mahasiswa ID: " + idMahasiswa + " (" + status + ")";
    }

    @Override
    public void validasi() throws ValidationException {
        if (idPertemuan <= 0) {
            throw new ValidationException("ID Pertemuan", "harus diisi dan valid");
        }
        if (idMahasiswa <= 0) {
            throw new ValidationException("ID Mahasiswa", "harus diisi dan valid");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException("Status", "tidak boleh kosong");
        }
    }
}