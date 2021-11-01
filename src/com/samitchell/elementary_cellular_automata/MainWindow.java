package com.samitchell.elementary_cellular_automata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener
{
	private JPanel mainPanel;
	private JPanel imagePanel;
	private OptionPanel optionPanel;
	private int width;
	private int height;
	private int scale;
	
	public MainWindow(String title)
	{
		super(title);
		
		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.mainPanel.setLayout(new BorderLayout());
		
		this.width = 400;
		this.height = 200;
		this.scale = 2;
		
		this.imagePanel = new JPanel();
		BufferedImage blank = new BufferedImage(this.width * this.scale, this.height * this.scale, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < blank.getWidth(); i++)
		{
			for (int j = 0; j < blank.getHeight(); j++)
			{
				blank.setRGB(i, j, Color.WHITE.getRGB());
			}
		}
		
		this.addImageToPanel(blank);
		this.mainPanel.add(imagePanel, BorderLayout.CENTER);
		
		optionPanel = new OptionPanel();
		optionPanel.getDisplayButton().addActionListener(this);
		this.mainPanel.add(optionPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try
		{
			int rule = Integer.parseInt(optionPanel.getRule().getText());
			
			if (rule < 0 || rule > 255)
			{
				JOptionPane.showMessageDialog(this, "Rule must be between 0 and 255", "Input error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{	
				this.drawRule(rule);
				this.revalidate();
			}
		}
		catch (NumberFormatException error)
		{
			error.printStackTrace();
			JOptionPane.showMessageDialog(this, "Rule must be an integer.", "Input error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void addImageToPanel(BufferedImage image)
	{
		this.imagePanel.removeAll();
		this.imagePanel.add(new JLabel(new ImageIcon(image)));
	}
	
	private void drawRule(int rule)
	{
		ElementaryCellularAutomata elemCA = new ElementaryCellularAutomata(rule, this.width, this.height, optionPanel.getRandom().isSelected());
		elemCA.completeHistory();
		BufferedImage image = elemCA.createImage();
		this.addImageToPanel(image);
		this.revalidate();
	}
}
