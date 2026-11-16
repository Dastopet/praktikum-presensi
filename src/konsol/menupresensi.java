    package konsol;

    import exeption.AppException;
    import model.Presensi;
    import java.util.List;
    import java.util.Scanner;

    public class menupresensi {
        private static final Scanner MASUK = new Scanner(System.in);
        private static final presensikonsol DATA = new presensikonsol();

        public static void main(String[] args) {
            boolean jalan = true;
            while (jalan) {
                System.out.println();
                System.out.println("=== Presensi Praktikum — Data Kehadiran ===");
                System.out.println("1. Tambah Presensi");
                System.out.println("2. Tampilkan Semua");
                System.out.println("3. Cari berdasarkan ID");
                System.out.println("4. Ubah Status Presensi");
                System.out.println("5. Hapus Presensi");
                System.out.println("0. Keluar");
                System.out.print("Pilihan: ");
                String pilihan = MASUK.nextLine().trim();
                try {
                    if ("1".equals(pilihan)) {
                        tambah();
                    } else if ("2".equals(pilihan)) {
                        tampilkanSemua();
                    } else if ("3".equals(pilihan)) {
                        cari();
                    } else if ("4".equals(pilihan)) {
                        ubah();
                    } else if ("5".equals(pilihan)) {
                        hapus();
                    } else if ("0".equals(pilihan)) {
                        jalan = false;
                    } else {
                        System.out.println("Pilihan tidak dikenal.");
                    }
                } catch (AppException e) {
                    System.out.println(e.pesanPengguna());
                } catch (NumberFormatException e) {
                    System.out.println("Input angka tidak valid.");
                }
            }
            System.out.println("Selesai.");
        }

        private static void tambah() throws AppException {
            System.out.print("ID Pertemuan : ");
            int idPertemuan = Integer.parseInt(MASUK.nextLine().trim());
            System.out.print("ID Mahasiswa : ");
            int idMahasiswa = Integer.parseInt(MASUK.nextLine().trim());
            System.out.print("Status (Hadir/Izin/Sakit/Alpha): ");
            String status = MASUK.nextLine().trim();

            Presensi p = new Presensi(idPertemuan, idMahasiswa, status);
            DATA.simpan(p);
            System.out.println("Tersimpan dengan ID: " + p.getId());
        }

        private static void tampilkanSemua() throws AppException {
            List<Presensi> daftar = DATA.ambilSemua();
            System.out.println("Total data: " + daftar.size());
            for (Presensi p : daftar) {
                System.out.println("ID: " + p.getId()
                        + " | Pertemuan ID: " + p.getIdPertemuan()
                        + " | Mahasiswa ID: " + p.getIdMahasiswa()
                        + " | Status: " + p.getStatus());
            }
        }

        private static void cari() throws AppException {
            System.out.print("Masukkan ID Presensi: ");
            int id = Integer.parseInt(MASUK.nextLine().trim());
            Presensi p = DATA.cariId(id);
            if (p != null) {
                System.out.println("Ditemukan -> Pertemuan ID: " + p.getIdPertemuan()
                        + ", Mahasiswa ID: " + p.getIdMahasiswa()
                        + ", Status: " + p.getStatus());
            } else {
                System.out.println("Data presensi tidak ditemukan.");
            }
        }

        private static void ubah() throws AppException {
            System.out.print("Masukkan ID Presensi yang diubah: ");
            int id = Integer.parseInt(MASUK.nextLine().trim());
            Presensi lama = DATA.cariId(id);
            if (lama == null) {
                System.out.println("Data tidak ditemukan.");
                return;
            }
            System.out.print("ID Pertemuan baru (" + lama.getIdPertemuan() + "): ");
            int idPertemuan = Integer.parseInt(MASUK.nextLine().trim());
            System.out.print("ID Mahasiswa baru (" + lama.getIdMahasiswa() + "): ");
            int idMahasiswa = Integer.parseInt(MASUK.nextLine().trim());
            System.out.print("Status baru (" + lama.getStatus() + "): ");
            String status = MASUK.nextLine().trim();

            lama.setIdPertemuan(idPertemuan);
            lama.setIdMahasiswa(idMahasiswa);
            lama.setStatus(status);

            if (DATA.ubah(lama)) {
                System.out.println("Satu baris berhasil diubah.");
            } else {
                System.out.println("Tidak ada baris yang berubah.");
            }
        }

        private static void hapus() throws AppException {
            System.out.print("Masukkan ID Presensi yang dihapus: ");
            int id = Integer.parseInt(MASUK.nextLine().trim());
            if (DATA.hapus(id)) {
                System.out.println("Satu baris berhasil dihapus.");
            } else {
                System.out.println("Data tidak ditemukan atau gagal dihapus.");
            }
        }
    }