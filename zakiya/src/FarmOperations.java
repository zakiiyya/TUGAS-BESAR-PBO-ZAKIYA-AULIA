import java.util.List;

// Interface untuk operasi peternakan
interface FarmOperations {
    void addCow(Cow cow); // Menambahkan sapi
    List<Cow> getCows(); // Mengambil daftar sapi
    void updateCow(Cow cow); // Memperbarui data sapi
    void deleteCow(int id); // Menghapus sapi berdasarkan ID
}