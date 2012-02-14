package fr.free.rocheteau.jerome.dunamis.fields;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class IntegerField implements Field {

	private String name;
	
	@Override
	public  void setName(String name) {
		this.name = name;
		this.getBorder().setTitle(FieldFactory.getTitle(name));
	}
	
	@Override
	public String getName() {
		return this.name;
	}
		
	private Integer value;
	
	@Override
	public void setValue(Object value) {
		if (value instanceof Integer) {
			this.value = (Integer) value;
			// this.getText().setText(this.value.toString());
			JComponent editor = this.getSpinner().getEditor();
			if (editor instanceof JSpinner.NumberEditor) {
	            ((JSpinner.NumberEditor) editor).getTextField().setText(value.toString());
	        }
		}
	}
	
	@Override
	public Integer getValue() {
		String value = null;
		JComponent editor = this.getSpinner().getEditor();
        if (editor instanceof JSpinner.NumberEditor) {
            value = ((JSpinner.NumberEditor) editor).getTextField().getText();
        } else {
            return null;
        }
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}

	public IntegerField() {
		this.setBorder();
		// this.setText();
		this.setModel();
		this.setSpinner();
		this.setComponent();
	}
	
	private TitledBorder border;
	
	private void setBorder() {
		Border border = BorderFactory.createEmptyBorder();
		int position = TitledBorder.DEFAULT_POSITION;
		int justrification = TitledBorder.DEFAULT_JUSTIFICATION;
		this.border = BorderFactory.createTitledBorder(border,"",justrification,position);		
	}
	
	private TitledBorder getBorder() {
		return this.border;
	}

	/*
	private JTextField text;
	
	private void setText() {
		this.text = new JTextField(33);
	}

	public JTextField getText() {
		return this.text;
	}
	*/
	
	private SpinnerNumberModel model;
	
	private void setModel() {
		this.model = new SpinnerNumberModel();
	}
	
	private SpinnerNumberModel getModel() {
		return this.model;
	}
	
	private JSpinner spinner;
	
	private void setSpinner() {
		this.spinner = new JSpinner(this.getModel());
		NumberEditor editor = new JSpinner.NumberEditor(this.spinner, "#");
		DecimalFormat format = editor.getFormat();
		format.setParseIntegerOnly(true);
		this.spinner.setEditor(editor);
		this.spinner.setPreferredSize(new Dimension(300,25));
	}
	
	private JSpinner getSpinner() {
		return this.spinner;
	}
	
	private JPanel component;
	
	private void setComponent() {
		this.component = new JPanel(new GridBagLayout());
		this.component.setBorder(this.getBorder());
		this.component.add(this.getSpinner());
	}

	@Override
	public JPanel getComponent() {
		return this.component;
	}
	
	@Override
	public boolean isModified() {
		if (this.getValue() == null) {
			return false;
		} else {
			return !this.getValue().equals(this.value);
		}
	}

}
