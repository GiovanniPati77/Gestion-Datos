package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.util.StringUtils;
import com.mysql.cj.xdevapi.Statement;

import Models.Cliente;

public class ClienteDao {
	public Connection conectar() {
		String baseDeDatos = "java";
		String usuario = "root";
		String password = "";
		String host = "localhost";
		String puerto = "3306";
		String driver = "com.mysql.cj.jdbc.Driver";
		String conexionUrl = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDeDatos + "?useSSL=false";

		Connection conexion = null;

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(conexionUrl, usuario, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexion;
	}

	public void agregar(Cliente cliente) {

		try {
			Connection conexion = conectar();
			String sql = "INSERT INTO `java`.`clientes` (`nombre`, `apellido`, `telefono`, `email`) VALUES ('"
					+ cliente.getNombre() + "', '" + cliente.getApellido() + "', '" + cliente.getTelefono() + "', '"
					+ cliente.getEmail() + "');";
			java.sql.Statement statement = conexion.createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizar(Cliente cliente) {

		try {
			Connection conexion = conectar();
			String sql = "UPDATE `java`.`clientes` SET `nombre` = '"+cliente.getNombre()+"', `apellido` = '"+cliente.getApellido()+"', `telefono` = '"+cliente.getTelefono()+"', `email` = '"+cliente.getEmail()+"' WHERE (`id` = '"+cliente.getId()+"');";
			java.sql.Statement statement = conexion.createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Cliente> listar() {

		List<Cliente> listado = new ArrayList<>();

		try {
			Connection conexion = conectar();
			String sql = "select * from clientes;";
			java.sql.Statement statement = conexion.createStatement();
			ResultSet resultado = statement.executeQuery(sql);

			while (resultado.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(resultado.getString("id"));
				cliente.setNombre(resultado.getString("nombre"));
				cliente.setApellido(resultado.getString("apellido"));
				cliente.setTelefono(resultado.getString("telefono"));
				cliente.setEmail(resultado.getString("email"));
				listado.add(cliente);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listado;
	}

	public void eliminar(String id) {

		try {
			Connection conexion = conectar();
			String sql = "DELETE FROM `java`.`clientes` WHERE `id` =" + id;
			java.sql.Statement statement = conexion.createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardar(Cliente cliente) {
		if(StringUtils.isEmptyOrWhitespaceOnly(cliente.getId())) {
			agregar(cliente);
		} else {
			actualizar(cliente);
		}
		
	}
}
