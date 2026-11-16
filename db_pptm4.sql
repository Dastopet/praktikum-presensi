-- ==========================================================
-- SKEMA BASIS DATA TIM 4 (ORANG PASRAH)
-- Tema: Aplikasi Presensi Praktikum Mahasiswa
-- Pesan : Tolong Dibaca Dengan Seksama Sekedar Indo Ini
--         Untuk Jobsheet Sisa Masih Dikerjakan 
-- ==========================================================

-- 1. Sesuai entity MataKuliah (atribut: Kode, Nama)
CREATE TABLE matakuliah (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode CHAR(10) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 2. Tabel Master: mahasiswa
CREATE TABLE mahasiswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim CHAR(16) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    kelas VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

-- 3. Sesuai entity Pertemuan (atribut: nomor, tanggal, topik, matakuliahId)
CREATE TABLE pertemuan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomor VARCHAR(10) NOT NULL,
    tanggal VARCHAR(20) NOT NULL,
    topik VARCHAR(150) NOT NULL,
    matakuliah_id INT NOT NULL,
    CONSTRAINT fk_pertemuan_matakuliah 
        FOREIGN KEY (matakuliah_id) REFERENCES matakuliah(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 4. Tabel Transaksi: presensi
CREATE TABLE presensi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pertemuan INT NOT NULL,
    id_mahasiswa INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT unik_presensi_mahasiswa UNIQUE (id_pertemuan, id_mahasiswa),
    CONSTRAINT fk_presensi_pertemuan 
        FOREIGN KEY (id_pertemuan) REFERENCES pertemuan(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_presensi_mahasiswa 
        FOREIGN KEY (id_mahasiswa) REFERENCES mahasiswa(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Data Dummy Awal yang disesuaikan
INSERT INTO matakuliah (kode, nama) VALUES 
('RPL233306', 'Pemrograman Berorientasi Objek'),
('RPL233301', 'Basis Data Lanjut');

INSERT INTO mahasiswa (nim, nama, kelas) VALUES 
('6304250057', 'Rizky Ramadhan', 'RPL 3C'),
('6304250058', 'Budi Santoso', 'RPL 3C');

INSERT INTO pertemuan (nomor, tanggal, topik, matakuliah_id) VALUES 
('10', '2026-11-09', 'Abstract Class dan Interface', 1),
('11', '2026-11-16', 'Enkapsulasi dan Exception JDBC', 1);

INSERT INTO presensi (id_pertemuan, id_mahasiswa, status) VALUES 
(2, 1, 'Hadir');