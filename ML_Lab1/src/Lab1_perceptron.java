import java.util.*;
import java.io.File;
import java.io.IOException;
import java.math.*;
public class Lab1_perceptron {
	
	static int numDP;
	static int numIn;
	
	public static void main(String[] args) throws IOException
	{
		Scanner file = new Scanner(new File("src/DataSet4.txt"));

		while(file.hasNext())
		{
			String s = file.nextLine().trim();
			System.out.println(s);
		}
		/*
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter number of data Points");
		 numDP = sc.nextInt();
		
		System.out.println("Ener number of inputs for each data point");
		 numIn = sc.nextInt();
		 
		sc.nextLine(); // Consumes last bits after nextInt
		
		
		
		
		Double X[][] = new Double [numDP][numIn+1];
		int T[] = new int[numDP];
		Double W[] = new Double[numIn+1];
		double learnR = 0.25;
		String line;
		
		System.out.println("Enter training Data");
		for(int i=0; i<numDP;i++)
		{
		    line = sc.nextLine();
		    String[] split = line.split(" ");
			for(int j=0;j<numIn+1;j++)
			{		
				//System.out.println(in);
				
				X[i][j] =  Double.parseDouble(split[j]);			
			
			}
		}
		
		for(int k =0; k<numDP ;k++) // swap and augment
		{
			double temp = X[k][numIn];
			T[k] = (int) temp;
			X[k][numIn]= -1.0;
		}
		
		//printTrix2D(X);
		//printTrix1D(T);
		
		System.out.println("Random Weights");
		for(int l=0; l< W.length;l++)
		{
			W[l]= Math.random()*(2) -1;
			System.out.println(W[l]);
		}
		System.out.println();
		//System.out.println(X[0]);
		PerceptLearn(X,T,W,learnR); */
		
		
	}
	
	public static void printWeights(Double[] W)
	{
		for(int i=0;i<W.length;i++)
		{
			System.out.println(W[i]);
		}
	}
	
	public static void printTrix2D(Double[][] x)
	{   
		System.out.println(" X = ");
		for(int i =0; i<numDP; i++)
		{
			for(int j=0; j<= numIn; j++)
			{
				System.out.print(x[i][j]+ " ");
			}
			System.out.println();
		}
	}
	
	public static void printTrix1D(int[] arr)
	{
		System.out.println(" T = ");
		for(int i=0; i<arr.length;i++)
		{
			System.out.println(arr[i]);
		}
	}
	
	public static void PerceptLearn(Double[][] x, int[] T, Double[] w, double learnR)
	{
		int n=0;
		while(n<100) // insert stopping condition here
		{
			for(int k=0; k<numDP; k++)
			{
				int row = k;
				int y = percept(w, x , row);
				
				for(int i=0;i<numIn+1; i++)
				{
					w[i]= w[i] + learnR*(T[k]-y)*x[k][i];
				}
			}
			System.out.println("Iteration No : " + n);
			printWeights(w);
			System.out.println();
			n++;
		}
	}
	
	public static int percept(Double[] w, Double[][] x, int row)
	{
		int shot=0;
		double thresh =0;// w[w.length-1]; //The fuck is the threshold value?
		double total=0;
		
	     for(int j=0; j<numIn+1;j++)
			{
			  total = total + w[j]*x[row][j];
			}
		
		
		System.out.println(" Calculated percept value = "+ total + "\n");
		
		if(total>thresh){
			System.out.println("Hit");
			shot =1;
		}
		else{
			System.out.println("Miss");
			shot = 0;
		}

		return shot;
	}
	
	
}
