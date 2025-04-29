package atomicmind.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection conexion;

    public static Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Ruta correcta del archivo SQLite
                String url = "jdbc:sqlite:C:\\Users\\pablo\\OneDrive\\Escritorio\\AtomicMind\\mi_base.sqlite";
                conexion = DriverManager.getConnection(url);
                System.out.println("✅ Conexión establecida correctamente con la base de datos SQLite.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
