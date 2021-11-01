package com.samitchell.elementary_cellular_automata;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionPanel extends JPanel 
{
	private JTextField rule;
	private JCheckBox random;
	private JButton displayButton;
	
	public OptionPanel()
	{
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(new JLabel("Rule"), gbc);
		
		this.rule = new JTextField(3);
		gbc.gridx = 1;
		this.add(rule, gbc);
		
		this.random = new JCheckBox("Random", false);
		gbc.gridx = 2;
		this.add(random, gbc);
		
		this.displayButton = new JButton("Display");
		gbc.gridx = 3;
		this.add(displayButton, gbc);
	}
	
	public JButton getDisplayButton()
	{
		return this.displayButton;
	}
	
	public JCheckBox getRandom()
	{
		return this.random;
	}
	
	public JTextField getRule()
	{
		return this.rule;
	}
}
