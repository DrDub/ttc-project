package eu.project.ttc.tools;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import eu.project.ttc.tools.acabit.Acabit;
import eu.project.ttc.tools.katastasis.Katastasis;
import eu.project.ttc.tools.tildetagger.TildeTagger;
import eu.project.ttc.tools.treetagger.TreeTagger;
import eu.project.ttc.tools.ziggurat.Ziggurat;

public class TermSuiteListener implements ActionListener {

	private TermSuite termSuite;
	
	public void setTermSuite(TermSuite termSuite) {
		this.termSuite = termSuite;
	}
	
	private TermSuite getTermSuite() {
		return this.termSuite;
	}
	
	private URI getHelp() throws URISyntaxException {
		String uri = this.getTermSuite().getPreferences().getHelp();
		return new URI(uri);
	}
	
	private void doQuit() {
		String message = "Do you really want to quit " + this.getTermSuite().getPreferences().getTitle() + "?";
		String title = "Quit";
		int response = JOptionPane.showConfirmDialog(this.getTermSuite().getFrame(),message,title,JOptionPane.YES_NO_OPTION);
		if (response == 0) {
			this.getTermSuite().quit();				
		}
	}
	
	private void doHelp() {
		try {
			if (this.getTermSuite().getDesktop() != null && this.getTermSuite().getDesktop().isSupported(Desktop.Action.BROWSE)) {
				this.getTermSuite().getDesktop().browse(this.getHelp());	
			} else {
				SwingUtilities.invokeLater(this.getTermSuite().getHelp());
			}
		} catch (IOException e) {
			this.getTermSuite().error(e);
		} catch (URISyntaxException e) {
			this.getTermSuite().error(e);
		}
	}
	
	private void doTreeTagger() {
		TreeTagger treetagger = new TreeTagger(false);
		treetagger.setParent(this.getTermSuite());
		SwingUtilities.invokeLater(treetagger);
	}
	
	private void doTildeTagger() {
		TildeTagger tildetagger = new TildeTagger(false);
		tildetagger.setParent(this.getTermSuite());
		SwingUtilities.invokeLater(tildetagger);
	}
	
	private void doAcabit() {
		Acabit acabit = new Acabit(false);
		acabit.setParent(this.getTermSuite());
		SwingUtilities.invokeLater(acabit);
	}

	private void doKatastasis() {
		Katastasis katastasis = new Katastasis(false);
		katastasis.setParent(this.getTermSuite());
		SwingUtilities.invokeLater(katastasis);
	}
	
	private void doZiggurat() {
		Ziggurat ziggurat = new Ziggurat(false);
		ziggurat.setParent(this.getTermSuite());
		SwingUtilities.invokeLater(ziggurat);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if (object instanceof JButton) {
			JButton source = (JButton) object;
			String action = source.getActionCommand();
			if (action.equals("quit")) { 
				this.doQuit();
			} else if (action.equals("help")) { 
				this.doHelp();
			} else if (action.equals("about")) {
				this.getTermSuite().getAbout().show();
			} else if (action.equals("treetagger")) {
				this.doTreeTagger();
			} else if (action.equals("tildetagger")) {
				this.doTildeTagger();
			} else if (action.equals("acabit")) {
				this.doAcabit();
			} else if (action.equals("katastasis")) {
				this.doKatastasis();
			} else if (action.equals("ziggurat")) {
				this.doZiggurat();
			} 
		} 
	}
	
}