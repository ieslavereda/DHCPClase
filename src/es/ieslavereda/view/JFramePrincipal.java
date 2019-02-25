package es.ieslavereda.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JFramePrincipal extends JFrame {

	private JPanel contentPane;
	private String configuracionGlobal;

	/**
	 * Create the frame.
	 */
	public JFramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int opcion = JOptionPane.showConfirmDialog(null, "Esta usted seguro de cargar los datos?", "Info",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);

				if (opcion == JOptionPane.YES_OPTION) {

					File f = seleccionarArchivo();
					if (f != null)
						cargarDatosDesdeFichero(f);
					System.out.println(configuracionGlobal);

				}
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void cargarDatosDesdeFichero(File file) {

		BufferedReader is = null;
		String linea = "";

		try {

			is = new BufferedReader(new FileReader(file));
			while ((linea = is.readLine()) != null) {

				if (linea.contains("# Configuracion global")) {
					cargarConfiguracionGlobal(is);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private File seleccionarArchivo() {

		File file = null;
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Configuracion .conf", "conf"));

		int opcion = jfc.showOpenDialog(this);

		if (opcion == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
		}

		return file;
	}

	private void cargarConfiguracionGlobal(BufferedReader is) {
		configuracionGlobal = "";
		String linea;
		try {
			while ((linea = is.readLine()) != null && !linea.contains("# Fin configuracion global")) {
				configuracionGlobal += linea + "\n";
			}

		} catch (Exception e) {
			try {
				is.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
		}

	}

}
