package model;

import exeption.ValidationException;
public class Penumpang extends BaseEntity {
    private String nik;
    private String nama;
    private String telepon;
    private String alamat;

    public Penumpang() {

    }

    public Penumpang(String nik, String nama, String telepon, String alamat) {
        this.nik = nik;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public void validasi() throws ValidationException {

        if (nik == null || nik.trim().isEmpty()) {
            throw new ValidationException("NIK", "tidak boleh kosong");
        }
        if (!nik.matches("\\d{16}")) {
            throw new ValidationException("NIK", "harus 16 angka");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException("nama", "tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return nama + " (" + nik + ")";
    }
}