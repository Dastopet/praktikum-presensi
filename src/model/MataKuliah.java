package model;


import exception.ValidationException;
// MODUL MASTER: mataKuliah. Pemilik: Vixma Marlleandha.
public class MataKuliah extends BaseEntity {
    private String Kode;
    private String Nama;



    public MataKuliah() {
    }
    public MataKuliah(String Kode, String Nama) {
        this.Kode = Kode;
        this.Nama = Nama;

    }
    public String getKode() { return Kode; }
    public void setKode(String Kode) { this.Kode = Kode; }
    public String getNama() { return Nama; }
    public void setNama(String Nama) { this.Nama = Nama; }


    @Override
    public void validasi() throws ValidationException {
        if (Kode == null || Kode.trim().isEmpty()) {
            throw new ValidationException("kode", "tidak boleh kosong");
        }

        if (Nama == null || Nama.trim().isEmpty()) {
            throw new ValidationException("Nama", "tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return Nama + " (" + Kode + ")";
    }
    }

