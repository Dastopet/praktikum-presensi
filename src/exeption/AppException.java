package exeption;

public class AppException extends Exception {

    public AppException(String pesan) {
        super(pesan);
    } public AppException(String pesan, Throwable penyebab) {
        super(pesan, penyebab);
    }

    public String pesanPengguna() {
        return getMessage();
    }
}
