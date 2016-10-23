import java.util.*;
public class Lab1 {

	public static int percept(ArrayList<Integer> weight, ArrayList<Double> input)
	{
		int shot=0;
		
		double total=0;
		double threshold = weight.get(weight.size()-1);
		
		for(int i =0; i< input.size(); i++)
		{
			total = total + weight.get(i)*input.get(i);
		}
		
		if(total >threshold)
			shot = 1;
		else{
			shot = 0;
		}
		
		return shot;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		ArrayList<Double> input = new ArrayList<Double>();
		ArrayList<Integer> weight = new ArrayList<Integer>();
		
		System.out.println("Number of Inputs");
		int numIn = Integer.parseInt(sc.nextLine());
		
		System.out.println("Enter Weight Array");
		for(int i=0; i < numIn ; i++)
		{
			weight.add(Integer.parseInt(sc.nextLine()));
		}
		weight.add(0);
		
		System.out.println("Enter Input Array");
		for(int i=0; i < numIn ; i++)
		{
			input.add(Double.parseDouble(sc.nextLine()));
		}
		input.add((double) -1);
		
		System.out.println("Outcome : " + percept(weight, input));
		
		sc.close();
	}
	
	
}
