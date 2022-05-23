package Main;

import javax.swing.*;

/**
 * Main entry for assignment 4.
 *
 * @author Skriv ditt namn här!!
 */
public class Controller {
	private MainPanel mainPanel;
	private BoundedBuffer buffer;


	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo lnf : UIManager.getInstalledLookAndFeels()) {
				if (lnf.getName().contains("Nimbus")) {
					UIManager.setLookAndFeel(lnf.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

		Controller controller = new Controller();
		controller.showFrame();
	}

	private void showFrame() {
		mainPanel = new MainPanel(this);

		JFrame frame = new JFrame("Text File Editor V.1 by Jakob Hagman");
		frame.add(mainPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Creates the bounded buffer and reader/writer/modifier threads.
	 * It also records the time the threads work take and displays it in the console
	 * @param target where to store final result (GUI)
	 * @param lines source lines
	 * @param find string to find
	 * @param replace replace with this string
	 */
	public void execute(JTextArea target, String[] lines, String find, String replace) {
		//System.out.println("Called");
		long start = System.currentTimeMillis();
		this.buffer = new BoundedBuffer(15,find, replace);
		Reader reader =	new Reader(target, buffer, lines.length);
		Writer writer = new Writer(buffer, lines);
		Modifier modifier = new Modifier(buffer, lines.length);
		reader.startThread();
		writer.startThread();
		modifier.startThread();
		Thread modifierThread = modifier.getThread();
		Thread writerThread = writer.getThread();
		Thread readerThread = reader.getThread();
		try {
			readerThread.join();
			modifierThread.join();
			writerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("The threads took:" +  (end - start) + "ms");
	}
}
