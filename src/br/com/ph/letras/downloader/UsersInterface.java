package br.com.ph.letras.downloader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UsersInterface {
	private JFrame janela;
	private JTextField searchField;
	private JButton btnSearch;
	private JLabel lbName;
	private JTextField nameField;
	private JButton btnCopyName;
	private JLabel lbAutor;
	private JTextField autorField;
	private JButton btnCopyAutor;
	private JLabel lbMusic;
	private JTextArea musicField;
	private JButton btnCopyMusic;
	private Music musicMain;
	
	public UsersInterface() {
		setUpJanela();
		setUpBoxSearch();
		setUpSearchButton();
		setUpLabelName();
		setUpNameField();
		setUpBtnCopyName();
		setUpLabelAutor();
		setUpAutorField();
		setUpBtnCopyAutor();
		setUpLabelMusic();
		setUpMusicField();
		setUpBtnCopyMusic();
		setVisible(true);
	}
	
	private void setVisible(boolean b) {
		this.janela.setVisible(b);
	}

	private void setUpBtnCopyMusic() {
		this.btnCopyMusic = new JButton("COPIAR");
		this.btnCopyMusic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(musicMain != null) {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection sl = new StringSelection(musicMain.getLetra());
					clipboard.setContents(sl, null);
				}
			}
		});
		this.btnCopyMusic.setBounds(769, 192, 85, 48);
		this.janela.add(btnCopyMusic);
	}

	private void setUpMusicField() {
		this.musicField = new JTextArea();
		this.musicField.setBounds(85, 192, 683, 288);
		this.musicField.setEditable(false);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(85, 192, 683, 288);
		scroll.getViewport().add(this.musicField);
		this.janela.add(scroll);
	}

	private void setUpLabelMusic() {
		this.lbMusic = new JLabel("Música");
		this.lbMusic.setBounds(0, 192, 85, 48);
		this.janela.add(lbMusic);
	}

	private void setUpBtnCopyAutor() {
		btnCopyAutor = new JButton("COPIAR");
		this.btnCopyAutor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(musicMain != null) {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection sl = new StringSelection(musicMain.getAutor());
					clipboard.setContents(sl, null);
				}
			}
		});
		btnCopyAutor.setBounds(769, 144, 85, 48);
		this.janela.add(btnCopyAutor);
	}
	
	private void setUpAutorField() {
		this.autorField = new JTextField();
		this.autorField.setEditable(false);
		this.autorField.setBounds(85, 144, 683, 48);
		this.janela.add(autorField);
	}

	private void setUpLabelAutor() {
		this.lbAutor = new JLabel("Autor");
		this.lbAutor.setBounds(0, 144, 85, 48);
		this.janela.add(lbAutor);
	}

	private void setUpBtnCopyName() {
		this.btnCopyName = new JButton("COPIAR");
		this.btnCopyName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(musicMain != null) {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection sl = new StringSelection(musicMain.getTitulo());
					clipboard.setContents(sl, null);
				}
			}
		});
		this.btnCopyName.setBounds(769, 96, 85, 48);
		this.janela.add(btnCopyName);
	}

	private void setUpNameField() {
		this.nameField = new JTextField();
		this.nameField.setBounds(85, 96, 683, 48);
		this.nameField.setEditable(false);
		this.janela.add(nameField);
	}

	private void setUpLabelName() {
		lbName = new JLabel("Nome");
		lbName.setBounds(0, 96, 85, 48);
		this.janela.add(lbName);
	}
	
	private void setUpSearchButton() {
		this.btnSearch = new JButton("PROCURAR");
		this.btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean concluido = false;
				Exception exception = null;
				
				if(!searchField.getText().trim().equals("")) {
					if(searchField.getText().trim().indexOf("https://www.letras.mus.br") != -1) {
						try {
							musicMain = new Music(new URL(searchField.getText()));
							concluido = true;
						} catch (Exception e1) {
							concluido = false;
							exception = e1;
						}
					}
				}
				
				if(concluido) {
					updateUI();
				}else {
					if(exception != null) {
						JOptionPane.showMessageDialog(janela, "Ouve um erro ao obter os dados:\n"+e.toString());
					} else {
						JOptionPane.showMessageDialog(janela, "Ouve um erro ao digitar a url");
					}
				}
			}
		});
		this.btnSearch.setToolTipText("Procurar a url digitada...");
		this.btnSearch.setBounds(new Rectangle(769, 0, 85, 48));
		this.janela.add(btnSearch);
	}
	
	private void updateUI() {
		this.nameField.setText(this.musicMain.getTitulo());
		this.autorField.setText(this.musicMain.getAutor());
		this.musicField.setText(this.musicMain.getLetra());
	}
	
	private void setUpBoxSearch() {
		this.searchField = new JTextField();
		this.searchField.setToolTipText("Digite aqui o endereço da música...");
		this.searchField.setText("URL...");
		this.searchField.setBounds(new Rectangle(0, 0, 769, 48));
		this.janela.add(this.searchField);
	}

	private void setUpJanela() {
		this.janela = new JFrame("Letras Downloader");
		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.janela.setBounds(getScreenSize());
		this.janela.setLayout(null);
		this.janela.setResizable(false);
	}

	private Rectangle getScreenSize() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle(d.width / 2 - 854 / 2, d.height / 2 - 480 / 2, 854, 480);
	}
}
