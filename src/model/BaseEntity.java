package model;

import exeption.ValidationException;
public abstract class BaseEntity implements Identifiable {
    private int id; @Override public int getId() {
        return id;
    }

    @Override public void setId(int id) {
        this.id = id;
    }

    public boolean baru() {
        return id == 0;
    }

    public abstract void validasi() throws ValidationException;

    public abstract String label();
    @Override public boolean equals(Object lain) {
        if (this == lain) return true;
        if (lain == null || getClass() != lain.getClass()) return false;
        return id != 0 && id == ((BaseEntity) lain).id;
    }

    @Override public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override public String toString() {
        return label();
    }
}
