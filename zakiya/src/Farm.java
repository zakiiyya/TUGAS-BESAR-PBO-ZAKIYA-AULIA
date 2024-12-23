import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Kelas Farm mengimplementasikan antarmuka FarmOperations
// dan bertanggung jawab untuk mengelola data sapi menggunakan database
class Farm implements FarmOperations {
    private Connection connection; // Koneksi ke database

    // Constructor untuk menginisialisasi koneksi ke database
    public Farm() {
        try {
            // Menghubungkan ke database MySQL dengan nama "mydatabase"
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
        } catch (SQLException e) {
            e.printStackTrace(); // Menangani kesalahan jika koneksi gagal
        }
    }

    // Metode untuk menambahkan data sapi ke database
    @Override
    public void addCow(Cow cow) {
        try {
            // Tambahkan data sapi ke tabel "Cows" (tabel utama)
            String sqlCows = "INSERT INTO Cows (name, birth_date, maintenance_cost, type) VALUES (?, ?, ?, ?)";
            PreparedStatement statementCows = connection.prepareStatement(sqlCows, Statement.RETURN_GENERATED_KEYS);
            statementCows.setString(1, cow.getName());
            statementCows.setDate(2, new java.sql.Date(cow.getBirthDate().getTime())); // Konversi tanggal ke format SQL
            statementCows.setDouble(3, cow.getMaintenanceCost());
            statementCows.setString(4, cow.getCategory()); // Menyimpan tipe sapi
            statementCows.executeUpdate();

            // Ambil ID yang dihasilkan secara otomatis setelah data dimasukkan
            ResultSet generatedKeys = statementCows.getGeneratedKeys();
            if (generatedKeys.next()) {
                int cowId = generatedKeys.getInt(1);

                // Jika sapi adalah DairyCow, tambahkan data ke tabel "DairyCows"
                if (cow instanceof DairyCow) {
                    String sqlDairy = "INSERT INTO DairyCows (id, milk_production) VALUES (?, ?)";
                    PreparedStatement statementDairy = connection.prepareStatement(sqlDairy);
                    statementDairy.setInt(1, cowId);
                    statementDairy.setDouble(2, ((DairyCow) cow).getMilkProduction());
                    statementDairy.executeUpdate();
                } 
                // Jika sapi adalah BeefCow, tambahkan data ke tabel "BeefCows"
                else if (cow instanceof BeefCow) {
                    String sqlBeef = "INSERT INTO BeefCows (id, weight) VALUES (?, ?)";
                    PreparedStatement statementBeef = connection.prepareStatement(sqlBeef);
                    statementBeef.setInt(1, cowId);
                    statementBeef.setDouble(2, ((BeefCow) cow).getWeight());
                    statementBeef.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Menangani kesalahan SQL
        }
    }

    // Metode untuk mengambil semua data sapi dari database
    @Override
public List<Cow> getCows() {
    List<Cow> cows = new ArrayList<>();
    try {
        // Query untuk mengambil data dasar da  ri tabel Cows
        String sql = "SELECT * FROM Cows";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Iterasi hasil query
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Date birthDate = resultSet.getDate("birth_date");
            double maintenanceCost = resultSet.getDouble("maintenance_cost");
            String type = resultSet.getString("type");

            // Cek tipe sapi untuk mengambil data tambahan dari tabel yang sesuai
            if ("Dairy Cow".equals(type)) {
                // Query untuk mengambil data dari tabel DairyCows
                String dairySql = "SELECT milk_production FROM DairyCows WHERE id = ?";
                PreparedStatement dairyStmt = connection.prepareStatement(dairySql);
                dairyStmt.setInt(1, id);
                ResultSet dairyResult = dairyStmt.executeQuery();
                if (dairyResult.next()) {
                    double milkProduction = dairyResult.getDouble("milk_production");
                    cows.add(new DairyCow(id, name, birthDate, maintenanceCost, milkProduction));
                }
            } else if ("Beef Cow".equals(type)) {
                // Query untuk mengambil data dari tabel BeefCows
                String beefSql = "SELECT weight FROM BeefCows WHERE id = ?";
                PreparedStatement beefStmt = connection.prepareStatement(beefSql);
                beefStmt.setInt(1, id);
                ResultSet beefResult = beefStmt.executeQuery();
                if (beefResult.next()) {
                    double weight = beefResult.getDouble("weight");
                    cows.add(new BeefCow(id, name, birthDate, maintenanceCost, weight));
                }
            } else {
                // Jika tipe sapi adalah General Cow
                cows.add(new Cow(id, name, birthDate, maintenanceCost));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Menangani kesalahan SQL
    }
    return cows;
}


    // Metode untuk memperbarui data sapi di database
    @Override
public void updateCow(Cow cow) {
    try {
        // Update data di tabel Cows
        String sqlCows = "UPDATE Cows SET name = ?, maintenance_cost = ?, type = ? WHERE id = ?";
        PreparedStatement statementCows = connection.prepareStatement(sqlCows);
        statementCows.setString(1, cow.getName());
        statementCows.setDouble(2, cow.getMaintenanceCost());
        statementCows.setString(3, cow.getCategory());
        statementCows.setInt(4, cow.getId());
        statementCows.executeUpdate();

        // Update data tambahan berdasarkan tipe sapi
        if (cow instanceof DairyCow) {
            // Update data di tabel DairyCows
            String sqlDairy = "UPDATE DairyCows SET milk_production = ? WHERE id = ?";
            PreparedStatement statementDairy = connection.prepareStatement(sqlDairy);
            statementDairy.setDouble(1, ((DairyCow) cow).getMilkProduction());
            statementDairy.setInt(2, cow.getId());
            statementDairy.executeUpdate();
        } else if (cow instanceof BeefCow) {
            // Update data di tabel BeefCows
            String sqlBeef = "UPDATE BeefCows SET weight = ? WHERE id = ?";
            PreparedStatement statementBeef = connection.prepareStatement(sqlBeef);
            statementBeef.setDouble(1, ((BeefCow) cow).getWeight());
            statementBeef.setInt(2, cow.getId());
            statementBeef.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Menangani kesalahan SQL
    }
}


    // Metode untuk menghapus data sapi dari database
    
    @Override
public void deleteCow(int id) {
    try {
        // Hapus data dari tabel DairyCows (jika ada)
        String sqlDairy = "DELETE FROM DairyCows WHERE id = ?";
        PreparedStatement statementDairy = connection.prepareStatement(sqlDairy);
        statementDairy.setInt(1, id);
        statementDairy.executeUpdate();

        // Hapus data dari tabel BeefCows (jika ada)
        String sqlBeef = "DELETE FROM BeefCows WHERE id = ?";
        PreparedStatement statementBeef = connection.prepareStatement(sqlBeef);
        statementBeef.setInt(1, id);
        statementBeef.executeUpdate();

        // Hapus data dari tabel Cows
        String sqlCows = "DELETE FROM Cows WHERE id = ?";
        PreparedStatement statementCows = connection.prepareStatement(sqlCows);
        statementCows.setInt(1, id);
        statementCows.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace(); // Menangani kesalahan SQL
    }
}
}
