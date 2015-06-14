package edu.br.ufrpe.uag.compiler.music;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import edu.br.ufrpe.uag.compiler.exceptions.LexicalException;
import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmptyException;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -835162173059315622L;
	private JPanel contentPane;
	private JButton btnCompile;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Input", null, scrollPane, null);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		textArea.setText("C partitura(Sol,3/4)\n"
				+ "	scC+1 cD fC\n"
				+ "	sfA+2 fG\n"
				+ "	cG cA+1");

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Output", null, scrollPane_1, null);

		JTextArea textAreaOutput = new JTextArea();
		scrollPane_1.setViewportView(textAreaOutput);

		btnCompile = new JButton("Compile");
		btnCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String source = textArea.getText();
				String out;
				try {
					out = Compiler.compile(source);
					JOptionPane.showMessageDialog(null, "Compiled!",
							"Compiler", JOptionPane.INFORMATION_MESSAGE);

					textAreaOutput.setText(out);
					tabbedPane.setSelectedIndex(1);
				} catch (SyntaxException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),
							"Error!", JOptionPane.ERROR_MESSAGE);
				} catch (TerminalNotFoundException ex) {
					JOptionPane.showMessageDialog(null, ex.getOutput(),
							"Error!", JOptionPane.ERROR_MESSAGE);
				} catch (NonTerminalEmptyException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),
							"Error!", JOptionPane.ERROR_MESSAGE);
				} catch (LexicalException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),
							"Error!", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		panel.add(btnCompile);
	}
}
