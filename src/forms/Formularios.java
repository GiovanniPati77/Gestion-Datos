package forms;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.util.StringUtils;

import Models.Cliente;
import dao.ClienteDao;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JSeparator;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import java.awt.Toolkit;

public class Formularios extends JFrame {

	private JLayeredPane contentPane;
	private JTextField TxtNombre;
	private JList listClientes;
	private JLabel lblNewLabel_1;
	private JTextField txtApellido;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel tele;
	private JTextField txtTelefono;

	/**
	 * Launch the application.
	 */
	private List<Cliente> lista = new ArrayList<>();
	private JLabel lblNewLabel_3;
	private JLabel lblId;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Formularios frame = new Formularios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void actualizarLista() {
		ClienteDao dao = new ClienteDao();
		lista = dao.listar();

		DefaultListModel datos = new DefaultListModel();
		for (int i = 0; i < lista.size(); i++) {
			Cliente cliente = lista.get(i);
			datos.addElement(cliente.getNombreCompleto());
		}

		listClientes.setModel(datos);

	}

	/**
	 * Create the frame.
	 */
	public Formularios() {
		setTitle("Administrador de Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 430);
		contentPane = new JLayeredPane();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		TxtNombre = new JTextField();
		TxtNombre.setColumns(10);

		listClientes = new JList();
		listClientes.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				actualizarLista();
			}
		});
		
		listClientes.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listClientes.setToolTipText("");

		JLabel lblNewLabel = new JLabel("Nombre:");

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Cliente cliente = new Cliente();
				cliente.setNombre(TxtNombre.getText());
				cliente.setApellido(txtApellido.getText());
				cliente.setEmail(txtEmail.getText());
				cliente.setTelefono(txtTelefono.getText());
				
				if(!StringUtils.isEmptyOrWhitespaceOnly(lblId.getText())) {
					cliente.setId(lblId.getText());
				} 
			

				ClienteDao dao = new ClienteDao();
				dao.guardar(cliente);

				actualizarLista();
				JOptionPane.showMessageDialog(btnGuardar, "El cliente se guardo correctamente");
				limpiarCajasDeTexto();

			}

			private void limpiarCajasDeTexto() {
				TxtNombre.setText("");
				txtApellido.setText("");
				txtEmail.setText("");
				txtTelefono.setText("");
				lblId.setText("");
			}

		});
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indice = listClientes.getSelectedIndex();
				Cliente cliente = lista.get(indice);
				ClienteDao dao = new ClienteDao();
				dao.eliminar(cliente.getId());
				actualizarLista();
				JOptionPane.showMessageDialog(btnGuardar, "El cliente se Elimino correctamente");

			}

		});

		lblNewLabel_1 = new JLabel("Apellido:");

		txtApellido = new JTextField();
		txtApellido.setColumns(10);

		lblEmail = new JLabel("Email:");

		txtEmail = new JTextField();
		txtEmail.setColumns(10);

		tele = new JLabel("Telefono:");

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Id:");
		
		lblId = new JLabel("");

		JSeparator separator = new JSeparator();
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indice = listClientes.getSelectedIndex();
				Cliente cliente = lista.get(indice);
				
				TxtNombre.setText(cliente.getNombre());
				txtApellido.setText(cliente.getApellido());
				txtTelefono.setText(cliente.getTelefono());
				txtEmail.setText(cliente.getEmail());
				lblId.setText(cliente.getId());
				
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Datos:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		
		JLabel lblNewLabel_4 = new JLabel("Administrador de Clientes");
		lblNewLabel_4.setFont(new Font("Sitka Text", Font.PLAIN, 24));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnGuardar)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnEditar)
									.addGap(18)
									.addComponent(btnEliminar)
									.addGap(98))
								.addComponent(listClientes, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_2)
									.addGap(142))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
													.addComponent(tele, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(txtApellido)
												.addComponent(txtEmail)
												.addComponent(txtTelefono, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
										.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel)
												.addComponent(lblNewLabel_3))
											.addGap(27)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(TxtNombre, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
												.addComponent(lblId))))
									.addGap(35))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(209)
							.addComponent(lblNewLabel_4)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(9)
							.addComponent(lblNewLabel_2)
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_3)
								.addComponent(lblId))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(TxtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(txtApellido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(tele)
								.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEmail)
								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(listClientes, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGuardar)
						.addComponent(btnEliminar)
						.addComponent(btnEditar))
					.addGap(13))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
