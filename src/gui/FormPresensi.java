package gui;

import dao.PresensiDao;
import exeption.AppException;
import exeption.ValidationException;
import model.Presensi;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.util.List;

/** Pemilik: Rizky Ramadhan (6304250057). */
public class FormPresensi extends BaseForm {
    private final PresensiDao dao = new PresensiDao();

    // Jangan diinisialisasi di baris deklarasi
    private JTextField txtIdPertemuan;
    private JTextField txtIdMahasiswa;
    private JTextField txtStatus;
    private GenericTableModel<Presensi> model;
    private JTable tabel;
    private int idTerpilih;

    public FormPresensi() {
        super("Presensi Praktikum — Data Kehadiran", 720, 440);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtIdPertemuan = new JTextField();
        txtIdMahasiswa = new JTextField();
        txtStatus = new JTextField();

        String[] label = {"ID Pertemuan", "ID Mahasiswa", "Status (Hadir/Izin/Sakit/Alpha)"};
        JComponent[] isian = {txtIdPertemuan, txtIdMahasiswa, txtStatus};
        String[] kolom = {"ID", "ID Pertemuan", "ID Mahasiswa", "Status"};

        model = new GenericTableModel<Presensi>(kolom,
                p -> new Object[]{p.getId(), p.getIdPertemuan(), p.getIdMahasiswa(), p.getStatus()});
        tabel = new JTable(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() >= 0) {
                pilihBaris(model.baris(tabel.getSelectedRow()));
            }
        });

        JButton btnBaru = new JButton("Baru");
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        btnBaru.addActionListener(e -> kosongkan());
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        JPanel atas = new JPanel(new BorderLayout());
        atas.add(panelIsian("Isian Presensi", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Presensi> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtIdPertemuan.setText("");
        txtIdMahasiswa.setText("");
        txtStatus.setText("");
        tabel.clearSelection();
        txtIdPertemuan.requestFocus();
    }

    private void pilihBaris(Presensi p) {
        idTerpilih = p.getId();
        txtIdPertemuan.setText(String.valueOf(p.getIdPertemuan()));
        txtIdMahasiswa.setText(String.valueOf(p.getIdMahasiswa()));
        txtStatus.setText(p.getStatus());
    }

    private Presensi dariIsian() throws ValidationException {
        int idPertemuan;
        int idMahasiswa;
        try {
            idPertemuan = Integer.parseInt(txtIdPertemuan.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("ID Pertemuan", "harus berupa angka bulat");
        }
        try {
            idMahasiswa = Integer.parseInt(txtIdMahasiswa.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("ID Mahasiswa", "harus berupa angka bulat");
        }

        Presensi p = new Presensi(idPertemuan, idMahasiswa, txtStatus.getText());
        p.setId(idTerpilih);
        return p;
    }

    private void simpan() {
        try {
            Presensi p = dariIsian();
            p.setId(0);
            dao.simpan(p);
            pesan("Tersimpan dengan id " + p.getId() + ".");
            kosongkan();
            muatTabel();
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void ubah() {
        if (idTerpilih == 0) {
            pesan("Pilih dulu satu baris di tabel.");
            return;
        }
        try {
            if (dao.ubah(dariIsian())) {
                pesan("Perubahan disimpan.");
                muatTabel();
            } else {
                pesan("Tidak ada baris yang berubah.");
            }
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void hapus() {
        if (idTerpilih == 0) {
            pesan("Pilih dulu satu baris di tabel.");
            return;
        }
        if (!konfirmasi("Hapus data presensi dengan id " + idTerpilih + "?")) {
            return;
        }
        try {
            if (dao.hapus(idTerpilih)) {
                pesan("Satu baris dihapus.");
                kosongkan();
                muatTabel();
            } else {
                pesan("Tidak ada baris yang terhapus.");
            }
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormPresensi().setVisible(true);
            }
        });
    }
}