/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exe1;

/**
 *
 * @author Sebastian
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(890, 500));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Sebastian\\Escritorio"));

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 39, 464, 412);
		contentPane.add(lblNewLabel);
		JPanel listpossible = new JPanel();
		
		JButton btnNewButton = new JButton("Abrir Imagen");
		btnNewButton.setBounds(10, 0, 179, 34);
		btnNewButton.addActionListener((event) -> {
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();

				InputStream is = null;
				try {
					is = new FileInputStream(selectedFile);
					BufferedImage bufferedImage = ImageIO.read(is);
					BufferedImage scaledImage = bufferedImage;
					ImageIcon image = new ImageIcon(scaledImage);
					Image img = image.getImage();
					Image newImg = img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
				
					lblNewLabel.setIcon(new ImageIcon(newImg));
					System.out.println(selectedFile.getAbsolutePath());
					new Util().setImage(selectedFile.getAbsolutePath(),lblNewLabel, listpossible);
					
					is.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		contentPane.add(btnNewButton);
		
		JScrollPane scrollable = new JScrollPane(listpossible);
		scrollable.setBounds(494, 40, 270, 411);
		contentPane.add(scrollable);
		
	
		listpossible.setBounds(0, 0, 270, 411);
		listpossible.setLayout(new BoxLayout(listpossible, BoxLayout.Y_AXIS));

	}
}
