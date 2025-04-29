package atomicmind.database; // Asegúrate de que coincide con tu estructura

public class TestConexion {
    public static void main(String[] args) {
        // Intentar conectar a la base de datos
        if (ConexionBD.getConnection() != null) {
            System.out.println("✅ La conexión a SQLite funciona correctamente.");
        } else {
            System.out.println("❌ Error: No se pudo establecer conexión con SQLite.");
        }
    }
}
