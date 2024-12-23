import java.util.Date;

// Kelas BeefCow merupakan turunan dari kelas Cow dan merepresentasikan sapi potong (beef cow)
class BeefCow extends Cow {
    private double weight; // Berat sapi dalam kilogram

    // Constructor untuk menginisialisasi objek BeefCow
    public BeefCow(int id, String name, Date birthDate, double maintenanceCost, double weight) {
        super(id, name, birthDate, maintenanceCost); // Memanggil constructor kelas induk (Cow)
        this.weight = weight; // Mengatur berat sapi
    }

    // Getter untuk mendapatkan berat sapi
    public double getWeight() { 
        return weight; 
    }

    // Setter untuk mengubah berat sapi
    public void setWeight(double weight) { 
        this.weight = weight; 
    }

    // Override metode getCategory untuk memberikan kategori khusus yaitu "Beef Cow"
    @Override
    public String getCategory() { 
        return "Beef Cow"; 
    }

    // Override metode toString untuk menambahkan informasi berat sapi ke dalam representasi string
    @Override
    public String toString() {
        return super.toString() + String.format(", weight=%.2f", weight);
        // Menggabungkan informasi dari kelas induk dengan berat sapi
    }
}
