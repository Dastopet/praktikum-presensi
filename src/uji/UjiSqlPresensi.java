package uji;

import dao.PresensiDao;

public class UjiSqlPresensi {
    public static void main(String[] args) {
        PresensiDao dao = new PresensiDao();
        System.out.println("INSERT :");
        System.out.println("  " + dao.lihatSqlInsert());
        System.out.println("UPDATE :");
        System.out.println("  " + dao.lihatSqlUpdate());
    }
}