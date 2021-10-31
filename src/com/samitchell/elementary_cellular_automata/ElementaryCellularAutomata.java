package com.samitchell.elementary_cellular_automata;

import java.util.Random;

public class ElementaryCellularAutomata 
{
	private int[] rule;
	private int[] currentState;
	private int[][] history;
	
	public ElementaryCellularAutomata()
	{
		this(30, false);
	}
	
	public ElementaryCellularAutomata(int ruleNo)
	{
		this(ruleNo, false);
	}
	
	public ElementaryCellularAutomata(int ruleNo, boolean random)
	{
		this.rule = this.ruleFromInt(ruleNo);
		this.currentState = new int[31];
		
		if (random) 
		{
			Random rand = new Random();
			
			for (int i = 0; i < 31; i++)
			{
				currentState[i] = rand.nextInt(2);
			}
		}
		else
		{
			for (int i = 0; i < 31; i++)
			{
				if (i == 16)
				{
					currentState[i] = 1;
				}
				else
				{
					currentState[i] = 0;
				}
			}
		}
		
		this.history = new int[20][31];
		this.history[0] = this.currentState;
	}

	public static void main(String[] args) 
	{
		ElementaryCellularAutomata elemCA = new ElementaryCellularAutomata();
		
	}
	
	private int[] ruleFromInt(int decimal) 
	{
		int[] binary = new int[8];
		
		int quotient = decimal;
		int remainder;
		int counter = 0;
		
		while(quotient > 0)
		{
			remainder = quotient % 2;
			binary[counter] = remainder;
			quotient /= 2;
			counter++;
		}
		
		for (int i = counter; i < 8; i++) {
			binary[i] = 0;
		}
		
		return binary;
	}
	
	public void setCurrentState(int[] state)
	{
		this.currentState = state;
	}
	
	public void setRule(int ruleNo)
	{
		this.rule = this.ruleFromInt(ruleNo);
	}
}
