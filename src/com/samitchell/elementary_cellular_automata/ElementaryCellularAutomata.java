package com.samitchell.elementary_cellular_automata;

import java.util.Random;

public class ElementaryCellularAutomata 
{
	private int[] rule;
	private int generations;
	private int width;
	private int[] currentState;
	private int currentGeneration;
	private int[][] history;
	
	public ElementaryCellularAutomata()
	{
		this(30, 31, 20, false);
	}
	
	public ElementaryCellularAutomata(int ruleNo)
	{
		this(ruleNo, 31, 20, false);
	}
	
	public ElementaryCellularAutomata(int ruleNo, int width, int generations, boolean random)
	{
		this.rule = this.ruleFromInt(ruleNo);
		this.width = width;
		this.generations = generations;
		
		this.currentState = new int[this.width];
		
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
		
		this.history = new int[this.generations][this.width];
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
		for (int i = 1; i < this.generations; i++)
		{
			this.nextGeneration();
		}
	}
	
	public int getCurrentGeneration()
	{
		return this.currentGeneration;
	}
	
	public int[] getCurrentState()
	{
		return this.currentState;
	}
	
	public int getGenerations()
	{
		return this.generations;
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
	
	public int[] getRule()
	{
		return this.rule;
	}
	
	public int getWidth()
	{
		return this.width;
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

	public static void main(String[] args) 
	{
		ElementaryCellularAutomata elemCA = new ElementaryCellularAutomata();
		elemCA.completeHistory();
		int[][] history = elemCA.getHistory();
		
		System.out.println("Rule: ");
		int[] rule = elemCA.getRule();
		
		for (int i = 0; i < 8; i++)
		{
			System.out.print(rule[i]);
		}
		
		System.out.println("\nOutput:\n");
		
		for (int i = 0; i < elemCA.getGenerations(); i++)
		{
			for (int j = 0; j <elemCA.getWidth(); j++)
			{
				System.out.print(history[i][j] + " ");
			}
			
			System.out.print("\n");
		}
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
	
	public void setCurrentState(int[] state)
	{
		this.currentState = state;
	}
	
	public void setRule(int ruleNo)
	{
		this.rule = this.ruleFromInt(ruleNo);
	}
}
