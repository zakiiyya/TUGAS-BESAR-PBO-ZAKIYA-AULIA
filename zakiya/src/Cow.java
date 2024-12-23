import java.util.Date;

// Kelas Cow merepresentasikan informasi dasar dari seekor sapi
class Cow {
    private int id; // ID unik untuk sapi
    private String name; // Nama sapi
    private Date birthDate; // Tanggal lahir sapi
    private double maintenanceCost; // Biaya perawatan sapi

    // Constructor untuk menginisialisasi objek Cow
    public Cow(int id, String name, Date birthDate, double maintenanceCost) {
        this.id = id; // Mengatur ID sapi
        this.name = name; // Mengatur nama sapi
        this.birthDate = birthDate; // Mengatur tanggal lahir sapi
        this.maintenanceCost = maintenanceCost; // Mengatur biaya perawatan
    }

    // Getter untuk mendapatkan ID sapi
    public int getId() { 
        return id; 
    }

    // Getter untuk mendapatkan nama sapi
    public String getName() { 
        return name; 
    }

    // Getter untuk mendapatkan tanggal lahir sapi
    public Date getBirthDate() { 
        return birthDate; 
    }

    // Getter untuk mendapatkan biaya perawatan sapi
    public double getMaintenanceCost() { 
        return maintenanceCost; 
    }

    // Mendapatkan kategori sapi (default adalah "General Cow")
    public String getCategory() { 
        return "General Cow"; 
    }

    // Setter untuk mengubah nama sapi
    public void setName(String name) { 
        this.name = name; 
    }

    // Setter untuk mengubah biaya perawatan sapi
    public void setMaintenanceCost(double maintenanceCost) { 
        this.maintenanceCost = maintenanceCost; 
    }

    // Override method toString untuk mencetak informasi sapi dalam format string
    @Override
    public String toString() {
        return String.format(
            "Cow{id=%d, name='%s', birthDate=%s, maintenanceCost=%.2f}", 
            id, name, birthDate, maintenanceCost
        );
    }
}
