package exeption;

import exeption.AppException;

public class DataAccessException extends AppException {
    public DataAccessException(String pesan, Throwable penyebab) {
        super(pesan, penyebab);
    }

    @Override public String pesanPengguna() {
        return "Gagal menghubungi basis data. Hubungi petugas. (" + getMessage() + ")";
    }
}