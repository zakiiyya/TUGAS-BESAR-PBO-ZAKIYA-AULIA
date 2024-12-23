import java.util.Date;

// Kelas DairyCow adalah turunan dari kelas Cow dan merepresentasikan sapi perah (dairy cow)
class DairyCow extends Cow {
    private double milkProduction; // Produksi susu harian dalam liter

    // Constructor untuk menginisialisasi objek DairyCow
    public DairyCow(int id, String name, Date birthDate, double maintenanceCost, double milkProduction) {
        super(id, name, birthDate, maintenanceCost); // Memanggil constructor dari kelas induk (Cow)
        this.milkProduction = milkProduction; // Mengatur jumlah produksi susu
    }

    // Getter untuk mendapatkan jumlah produksi susu
    public double getMilkProduction() { 
        return milkProduction; 
    }

    // Setter untuk mengubah jumlah produksi susu
    public void setMilkProduction(double milkProduction) { 
        this.milkProduction = milkProduction; 
    }

    // Override metode getCategory untuk memberikan kategori khusus yaitu "Dairy Cow"
    @Override
    public String getCategory() { 
        return "Dairy Cow"; 
    }

    // Override metode toString untuk menambahkan informasi produksi susu ke dalam representasi string
    @Override
    public String toString() {
        return super.toString() + String.format(", milkProduction=%.2f", milkProduction);
        // Menggabungkan informasi dari kelas induk dengan jumlah produksi susu
    }
}
