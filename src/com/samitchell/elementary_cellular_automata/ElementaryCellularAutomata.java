package com.samitchell.elementary_cellular_automata;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ElementaryCellularAutomata 
{
	private int[] rule;
	private int height;
	private int width;
	private int[] currentState;
	private int currentGeneration;
	private int[][] history;
	
	public ElementaryCellularAutomata(int ruleNo, int width, int height, boolean random)
	{
		this.rule = this.ruleFromInt(ruleNo);
		this.width = width;
		this.height = height;
		
		this.currentState = new int[this.width];
		
		if (random) 
		{
			Random rand = new Random();
			
			for (int i = 0; i < width; i++)
			{
				currentState[i] = rand.nextInt(2);
			}
		}
		else
		{
			for (int i = 0; i < width; i++)
			{
				if (i == (width + 1) / 2)
				{
					currentState[i] = 1;
				}
				else
				{
					currentState[i] = 0;
				}
			}
		}
		
		this.history = new int[this.height][this.width];
		this.history[0] = this.currentState;
		this.currentGeneration = 1;
	}
	
	private int applyRule(int[] parents)
	{
		if (parents[0] == 1 && parents[1] == 1 && parents[2] == 1)
		{
			return this.rule[0];
		}
		else if (parents[0] == 1 && parents[1] == 1 && parents[2] == 0)
		{
			return this.rule[1];
		}
		else if (parents[0] == 1 && parents[1] == 0 && parents[2] == 1)
		{
			return this.rule[2];
		}
		else if (parents[0] == 1 && parents[1] == 0 && parents[2] == 0)
		{
			return this.rule[3];
		}
		else if (parents[0] == 0 && parents[1] == 1 && parents[2] == 1)
		{
			return this.rule[4];
		}
		else if (parents[0] == 0 && parents[1] == 1 && parents[2] == 0)
		{
			return this.rule[5];
		}
		else if (parents[0] == 0 && parents[1] == 0 && parents[2] == 1)
		{
			return this.rule[6];
		}
		else
		{
			return this.rule[7];
		}
	}
	
	public void completeHistory()
	{
		for (int i = 1; i < this.height; i++)
		{
			this.nextGeneration();
		}
	}
	
	public BufferedImage createImage(int scale)
	{
		int width = this.width * scale;
		int height = this.height * scale;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Color white = new Color(255, 255, 255);
		Color black = new Color(0, 0, 0);
		int whiteRGB = white.getRGB();
		int blackRGB = black.getRGB();
		
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int rgb;
				
				if (this.history[i / scale][j / scale] == 0)
				{
					rgb = whiteRGB;
				}
				else
				{
					rgb = blackRGB;
				}
				
				image.setRGB(j,i,rgb);
			}
		}
		
		return image;
	}
	
	public int[][] getHistory()
	{
		return this.history;
	}
	
	private int[] getParents(int i)
	{
		int[] parents = new int[3];
		
		if (i == 0)
		{
			parents[0] = currentState[this.width-1];
			parents[1] = currentState[0];
			parents[2] = currentState[1];
		}
		else if (i == this.width - 1)
		{
			parents[0] = currentState[this.width - 2];
			parents[1] = currentState[this.width - 1];
			parents[2] = currentState[0];
		}
		else
		{
			parents[0] = currentState[i - 1];
			parents[1] = currentState[i];
			parents[2] = currentState[i + 1];
		}
		
		return parents;
	}
	
	public static void main(String[] args) 
	{
		int width = 500;
		int height = 200;
		int scale = 2;
		int rule = 22;
		
		ElementaryCellularAutomata elemCA = new ElementaryCellularAutomata(rule, width, height, false);
		elemCA.completeHistory();
		BufferedImage image = elemCA.createImage(scale);
		
		JFrame frame = new JFrame("Elementary Cellular Automata");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(new ImageIcon(image)));
		
		frame.add(imagePanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void nextGeneration()
	{
		int[] nextState = new int[this.width];
		
		for (int i = 0; i < this.width; i++)
		{
			int[] parents = this.getParents(i);
			nextState[i] = this.applyRule(parents);
		}
		
		this.currentState = nextState;
		this.history[this.currentGeneration] = this.currentState;
		this.currentGeneration++;
	}
	
	private int[] ruleFromInt(int decimal) 
	{
		int[] binary = new int[8];
		
		int quotient = decimal;
		int remainder;
		int counter = 7;
		
		while(quotient > 0)
		{
			remainder = quotient % 2;
			binary[counter] = remainder;
			quotient /= 2;
			counter--;
		}
		
		for (int i = counter; i >= 0; i--) {
			binary[i] = 0;
		}
		
		return binary;
	}
}
