package latihan;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class FormPertemuanTampilan extends JFrame {
    private JTextField fNomor;
    private JTextField fTanggal;
    private JTextField fTopik;
    private JTextField fMatakuliahId;

    public FormPertemuanTampilan() {
        super("Data Pertemuan Praktikum - Tim Aurora");
        setSize(760, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        susunKomponen();
    }

    private void susunKomponen() {
        setLayout(new BorderLayout(8, 8));

        // 2. PANEL ATAS (NORTH) - Pencarian Berdasarkan Topik Pertemuan
        JPanel panelAtas = new JPanel();
        panelAtas.add(new JLabel("Topik Pertemuan:"));
        panelAtas.add(new JTextField(20));
        panelAtas.add(new JButton("Cari"));
        add(panelAtas, BorderLayout.NORTH);

        // 3. TABEL DATA TENGAH (CENTER) - Log Contoh Data Riwayat Pertemuan
        String[] judulKolom = {"Nomor", "Tanggal", "Topik", "ID Matakuliah"};
        Object[][] dataSementara = {
                {"1", "28 Oktober 2026", "Pengenalan Java Swing", "MK01"},
                {"2", "02 November 2026", "Layout Manager Bersarang", "MK01"}
        };
        JTable tabel = new JTable(dataSementara, judulKolom);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        fNomor        = new JTextField(8);
        fTanggal      = new JTextField(8);
        fTopik        = new JTextField(8);
        fMatakuliahId = new JTextField(8);

        JTextField[] isian = {fNomor, fTanggal, fTopik, fMatakuliahId};

        String[] labelIsian = {"Nomor", "Tanggal", "Topik", "ID Matakuliah"};
        add(buatPanelIsian(labelIsian, isian), BorderLayout.EAST);

        JPanel panelBawah = new JPanel();
        panelBawah.add(new JButton("Baru"));
        panelBawah.add(new JButton("Simpan"));
        panelBawah.add(new JButton("Hapus"));
        add(panelBawah, BorderLayout.SOUTH);
    }

    private JPanel buatPanelIsian(String[] judul, JTextField[] isian) {
        JPanel kolomLabel = new JPanel(new GridLayout(judul.length, 1, 6, 6));
        JPanel kolomIsian = new JPanel(new GridLayout(isian.length, 1, 6, 6));

        for (int i = 0; i < judul.length; i++) {
            kolomLabel.add(new JLabel(judul[i]));
            kolomIsian.add(isian[i]);
        }

        JPanel panel = new JPanel(new BorderLayout(6, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Isian Pertemuan"));
        panel.add(kolomLabel, BorderLayout.WEST);
        panel.add(kolomIsian, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPertemuanTampilan().setVisible(true));
    }
}