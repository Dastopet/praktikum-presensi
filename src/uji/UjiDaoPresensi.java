package uji;

import dao.PresensiDao;
import exeption.AppException;
import model.Presensi;
import java.util.List;

public class UjiDaoPresensi {
    public static void main(String[] args) {
        PresensiDao dao = new PresensiDao();
        try {
            Presensi baru = new Presensi(1, 2, "Hadir");
            dao.simpan(baru);
            System.out.println("[simpan        ] id dari AUTO_INCREMENT = " + baru.getId());

            Presensi dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId        ] " + dibaca.label());

            List<Presensi> perPertemuan = dao.cariPertemuan(1);
            System.out.println("[cariPertemuan ] jumlah di pertemuan 1 = " + perPertemuan.size());

            dibaca.setStatus("Izin");
            System.out.println("[ubah          ] berhasil? " + dao.ubah(dibaca));

            List<Presensi> semua = dao.ambilSemua();
            System.out.println("[ambilSemua    ] jumlah baris = " + semua.size());
            for (Presensi p : semua) {
                System.out.println("  " + p.getId() + ". " + p.label());
            }

            System.out.println("[hapus         ] berhasil? " + dao.hapus(baru.getId()));
            System.out.println("[hapus         ] id tak ada, berhasil? " + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }

        // Data yang ditolak validasi sebelum menyentuh basis data
        try {
            dao.simpan(new Presensi(1, 2, "Bolos"));
            System.out.println("[validasi      ] TIDAK TERJADI — seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi      ] " + e.pesanPengguna());
        }
    }
}