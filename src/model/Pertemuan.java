package model;

import exception.ValidationException;

public class Pertemuan extends BaseEntity {
    private String nomor;
    private String tanggal;
    private String topik;
    private int matakuliahId;
    public Pertemuan() {
    }

    public Pertemuan(String nomor, String tanggal, String topik, int matakuliahId) {
        this.nomor = nomor;
        this.tanggal = tanggal;
        this.topik = topik;
        this.matakuliahId = matakuliahId;
    }

    public String getNomor() { return nomor; }
    public void setNomor(String nomor) { this.nomor = nomor; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getTopik() { return topik; }
    public void setTopik(String topik) { this.topik = topik; }

    public int getMatakuliahId() { return matakuliahId; }
    public void setMatakuliahId(int matakuliahId) { this.matakuliahId = matakuliahId; }

    @Override
    public void validasi() throws ValidationException {
        if (nomor == null || nomor.trim().isEmpty()) {
            throw new ValidationException("Nomor Pertemuan", "tidak boleh kosong");
        }
        if (!nomor.matches("\\d+")) {
            throw new ValidationException("Nomor Pertemuan", "harus berupa angka");
        }

        if (tanggal == null || tanggal.trim().isEmpty()) {
            throw new ValidationException("Tanggal", "tidak boleh kosong");
        }

        if (topik == null || topik.trim().isEmpty()) {
            throw new ValidationException("Topik Pertemuan", "tidak boleh kosong");
        }
        if (topik.trim().length() < 5) {
            throw new ValidationException("Topik Pertemuan", "minimal harus 5 karakter");
        }

        if (matakuliahId <= 0) {
            throw new ValidationException("ID Matakuliah", "harus merujuk pada ID master yang sah");
        }
    }
    @Override
    public String label() {
        return "Pertemuan " + nomor + ": " + topik;
    }
}
