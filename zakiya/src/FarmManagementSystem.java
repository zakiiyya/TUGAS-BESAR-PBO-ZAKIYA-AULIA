import java.sql.Date; // Import library untuk menangani tanggal menggunakan format SQL
import java.util.Scanner; // Import library untuk membaca input dari pengguna

// Kelas utama untuk sistem manajemen peternakan
public class FarmManagementSystem {
    public static void main(String[] args) {
        // Membuat Scanner untuk membaca input dari pengguna
        try (Scanner scanner = new Scanner(System.in)) {
            Farm farm = new Farm(); // Membuat objek Farm untuk menyimpan data sapi

            // Loop utama untuk menjalankan menu
            while (true) {
                // Menampilkan menu utama
                System.out.println("1. Tambah Sapi");
                System.out.println("2. Tampilkan Semua Sapi");
                System.out.println("3. Perbarui Data Sapi");
                System.out.println("4. Hapus Sapi");
                System.out.println("5. Keluar");
                System.out.print("Pilih opsi: ");
                
                // Validasi input pilihan
                int choice;
                while (true) {
                    if (scanner.hasNextInt()) { // Memeriksa apakah input adalah angka
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Membersihkan newline dari input
                        if (choice >= 1 && choice <= 5) { // Memastikan input berada dalam rentang valid
                            break;
                        } else {
                            System.out.print("Pilihan tidak valid. Silakan pilih antara 1-5: ");
                        }
                    } else {
                        System.out.print("Input tidak valid. Silakan masukkan angka: ");
                        scanner.next(); // Membersihkan input yang tidak valid
                    }
                }

                // Menangani pilihan menu
                switch (choice) {
                    case 1: // Tambah Sapi
                        System.out.println("Pilih Tipe Sapi:");
                        System.out.println("1. Dairy Cow");
                        System.out.println("2. Beef Cow");
                        System.out.print("Pilihan: ");
                        int cowType = scanner.nextInt(); // Membaca tipe sapi
                        scanner.nextLine(); // Membersihkan newline

                        // Membaca data sapi dari pengguna
                        System.out.print("Masukkan Nama Sapi: ");
                        String name = scanner.nextLine();
                        System.out.print("Masukkan Tanggal Lahir (yyyy-mm-dd): ");
                        String birthDateStr = scanner.nextLine();
                        Date birthDate = java.sql.Date.valueOf(birthDateStr); // Mengubah string ke format SQL Date
                        System.out.print("Masukkan Biaya Perawatan: ");
                        double maintenanceCost = scanner.nextDouble();

                        Cow newCow = null; // Objek sapi yang akan dibuat
                        if (cowType == 1) { // Jika tipe sapi adalah Dairy Cow
                            System.out.print("Masukkan Produksi Susu (liter): ");
                            double milkProduction = scanner.nextDouble();
                            newCow = new DairyCow(0, name, birthDate, maintenanceCost, milkProduction); // Membuat objek DairyCow
                        } else if (cowType == 2) { // Jika tipe sapi adalah Beef Cow
                            System.out.print("Masukkan Berat (kg): ");
                            double weight = scanner.nextDouble();
                            newCow = new BeefCow(0, name, birthDate, maintenanceCost, weight); // Membuat objek BeefCow
                        }

                        // Menambahkan sapi ke dalam peternakan
                        if (newCow != null) {
                            farm.addCow(newCow);
                            System.out.println("Sapi berhasil ditambahkan!");
                        }
                        break;

                    case 2: // Tampilkan Semua Sapi
                        // Menampilkan data semua sapi yang ada
                        farm.getCows().forEach(System.out::println);
                        break;

                    case 3: // Perbarui Data Sapi
                        System.out.print("Masukkan ID Sapi yang ingin diperbarui: ");
                        int updateId = scanner.nextInt(); // Membaca ID sapi yang ingin diperbarui
                        scanner.nextLine(); // Membersihkan newline
                        Cow cowToUpdate = null;

                        // Mencari sapi berdasarkan ID
                        for (Cow cow : farm.getCows()) {
                            if (cow.getId() == updateId) {
                                cowToUpdate = cow;
                                break;
                            }
                        }

                        if (cowToUpdate != null) {
                            // Memperbarui data umum sapi
                            System.out.print("Masukkan Nama Sapi Baru (kosongkan untuk tidak mengubah): ");
                            String newName = scanner.nextLine();
                            if (!newName.isEmpty()) {
                                cowToUpdate.setName(newName);
                            }

                            System.out.print("Masukkan Biaya Perawatan Baru (kosongkan untuk tidak mengubah): ");
                            String newMaintenanceCostStr = scanner.nextLine();
                            if (!newMaintenanceCostStr.isEmpty()) {
                                double newMaintenanceCost = Double.parseDouble(newMaintenanceCostStr);
                                cowToUpdate.setMaintenanceCost(newMaintenanceCost);
                            }

                            // Memperbarui data spesifik berdasarkan tipe sapi
                            if (cowToUpdate instanceof DairyCow) {
                                System.out.print("Masukkan Produksi Susu Baru (kosongkan untuk tidak mengubah): ");
                                String newMilkProductionStr = scanner.nextLine();
                                if (!newMilkProductionStr.isEmpty()) {
                                    double newMilkProduction = Double.parseDouble(newMilkProductionStr);
                                    ((DairyCow) cowToUpdate).setMilkProduction(newMilkProduction);
                                }
                            } else if (cowToUpdate instanceof BeefCow) {
                                System.out.print("Masukkan Berat Baru (kosongkan untuk tidak mengubah): ");
                                String newWeightStr = scanner.nextLine();
                                if (!newWeightStr.isEmpty()) {
                                    double newWeight = Double.parseDouble(newWeightStr);
                                    ((BeefCow) cowToUpdate).setWeight(newWeight);
                                }
                            }

                            // Memperbarui data sapi di database
                            farm.updateCow(cowToUpdate);
                            System.out.println("Data sapi berhasil diperbarui!");
                        } else {
                            System.out.println("Sapi dengan ID " + updateId + " tidak ditemukan.");
                        }
                        break;

                    case 4: // Hapus Sapi
                        System.out.print("Masukkan ID Sapi yang ingin dihapus: ");
                        int deleteId = scanner.nextInt(); // Membaca ID sapi yang ingin dihapus
                        scanner.nextLine(); // Membersihkan newline
                        farm.deleteCow(deleteId);
                        System.out.println("Sapi dengan ID " + deleteId + " berhasil dihapus!");
                        break;

                    case 5: // Keluar
                        System.out.println("Keluar program.");
                        return; // Mengakhiri program

                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            }
        }
    }
}
