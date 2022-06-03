import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;


public class Main {
	public static int difficulty = 4;
	private static String previousHash ="0";
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static ArrayList<Record> records = new ArrayList<Record>();
	public static ArrayList<users> users_list = new ArrayList<users>();
	public static ArrayList<users> doctors = new ArrayList<users>();
	public static int p = 11;
	public static int g = 2;

public static void main(String[] args) 
{
	addDoctors();
	
	String choice="";
	do{
    	
    	try{	
    			System.out.println("1) view");
    			System.out.println("2) add");
    			System.out.println("3) register");
				
				// change to numbers */
    			Scanner sc = new Scanner(System.in); 
        		String option = sc.nextLine();
				// change to int */
   
				
				//Code to register new patients for a single transaction
				if(option.equals("3"))
				{
					regUser();
				}

				//Code to add patient details under a particular doctor
				else if(option.equals("2"))
				{
					System.out.println("Enter doc name:");
					sc = new Scanner(System.in); 
					String dname = sc.nextLine();

					System.out.println("Enter doc pass:");
					sc = new Scanner(System.in); 
					String dpass = sc.nextLine();

					System.out.println("Enter patient name:");
					sc = new Scanner(System.in); 
					String pname = sc.nextLine();
					int y=0;
					int userFound = 0;
					for(int j=0;j<users_list.size();j++)
					{
						if(users_list.get(j).getName().equals(pname))
						{
							userFound = 1;
							int x=Integer.parseInt(users_list.get(j).getPass());
							y=expo(g,x,p);
						}else{
							
						}		
					}
					if(userFound == 0){
						System.out.println("User not found.");
							break;
					}
					if(zeroKnowledgeProof(y) == false)
					{
						continue;
					}
					
					int flag =0;
					
					Record new_rec = new Record();
					new_rec.addUsers(dname, pname);
					
					for(int i=0;i<doctors.size();i++)
					{
						if(doctors.get(i).getName().equals(dname) && doctors.get(i).getPass().equals(dpass))
						{
							for(int j=0;i<users_list.size();j++)
							{
								if(users_list.get(j).getName().equals(pname))
								{
									while(true)
									{
										System.out.println("Enter data: y/n");
										sc = new Scanner(System.in);
										String ip = sc.nextLine();
										if(ip.equals("y"))
										{
											sc = new Scanner(System.in);
											String newData = sc.nextLine();
											new_rec.addData(newData);
			
										}
										else
										{
											records.add(new_rec);
											previousHash = createBlock(records,previousHash,new_rec);
											flag = 1;
											break;
										}
									}
									
								}
								if(flag ==1)
									break;
							}
							
						}
						if(flag ==1)
							break;
					}
					if (flag == 0)
					{
						System.out.println("something wrong");
					}
					
				}

				//Code to view transactions (patient details)
				else if(option.equals("1"))
				{
					System.out.println("Are you a doctor or patient?(d/p)");
					sc = new Scanner(System.in);
					String value = sc.nextLine();
					if(value.equals("d"))
					{
						sc = new Scanner(System.in);
						System.out.println("Enter your name:");
						String doct = sc.nextLine();
						System.out.println("Enter your pass:");
						String pass = sc.nextLine();
						int f1=0;
						for(int i=0;i<doctors.size();i++)
						{
							if(doctors.get(i).getName().equals(doct)&&doctors.get(i).getPass().equals(pass))
							{
								for(int k=0;k<blockchain.size();k++)
								{
									if(blockchain.get(k).docname().equals(doct))
									{
										System.out.println("Doctor:"+doct);
										System.out.println("Patient:"+blockchain.get(k).patient_name());
										System.out.println("His Medical Data:");
										System.out.println("TimeStamp at which data  was recorded:"+blockchain.get(k).timeStamp);
										blockchain.get(k).printData();	
										System.out.println();
										f1=1;

									}
								}
								if(f1==1)
									break;
							}	
						}
						if(f1==0)
							System.out.println("Doctor Not Found");
					}

					else if(value.equals("p"))
					{
						sc = new Scanner(System.in);
						System.out.println("Enter your name:");
						String pat = sc.nextLine();
						int y=0;
						for(int j=0;j<users_list.size();j++)
						{
							if(users_list.get(j).getName().equals(pat))
							{
								int x=Integer.parseInt(users_list.get(j).getPass());
								y=expo(g,x,p);
							}			
						}
						if(zeroKnowledgeProof(y) == false)
						{
							continue;
						}
						int f2=0;
						for(int i=0;i<users_list.size();i++)
						{
							if(users_list.get(i).getName().equals(pat))
							{
								for(int k=0;k<blockchain.size();k++)
								{
									if(blockchain.get(k).patient_name().equals(pat))
									{
										System.out.println("Time:"+blockchain.get(k).timeStamp);
										System.out.println("Doctor:"+blockchain.get(k).docname());
										System.out.println("Patient:"+blockchain.get(k).patient_name());
										System.out.println("Patient's Medical Data:");
										blockchain.get(k).printData();
										System.out.println();
										f2=1;
									}
								}
								if(f2==1)
									break;
							}
							
						}
						if(f2==0)
							System.out.println("Patient Not Found");
							
					}
				
					else
					{
						System.out.println("invalid");

					}
				}
				

			}
		catch(Exception e){
			System.out.println("Details entered are invalid");
		}
    	
    	Scanner sc1 = new Scanner(System.in); 
		System.out.println("Do you want to continue?(y/n)");
    	choice=sc1.nextLine();

	}while(choice.equals("y"));
}

//Function to create block and add it to blockchain
private static void addDoctors(){
	users doc1 =  new users();
	doc1.setName("mishra");
	doc1.setPass("1");
	doctors.add(doc1);
	users_list.add(doc1);

	users doc2 =  new users();
	doc2.setName("gupta");
	doc2.setPass("2");
	doctors.add(doc2);
	users_list.add(doc2);

	users doc3 =  new users();
	doc3.setName("batra");
	doc3.setPass("3");
	doctors.add(doc3);
	users_list.add(doc3);
}
private static void regUser(){
					Scanner sc = new Scanner(System.in); 
					users new_patient = new users();
					System.out.println("Enter name:");
					sc = new Scanner(System.in); 
					String name = sc.nextLine();
					new_patient.setName(name);

					System.out.println("Enter pass:(only integer)");
					sc = new Scanner(System.in); 
					String pass = sc.nextLine();
					new_patient.setPass(pass);

					System.out.println("Verify pass:");
					sc = new Scanner(System.in); 
					String vpass = sc.nextLine();
					if(new_patient.validate(pass,vpass)){
						 users_list.add(new_patient);
						 System.out.println("User created...");
					}

}

public static String createBlock(ArrayList<Record> record,String previousHash,Record data)
{
	System.out.println("creating block...");
			
	Block block = new Block(record, previousHash,data);
			
	block.mineBlock(difficulty);
	if (verifyTransaction(block)) 
	{
		blockchain.add(block);
	}

    return block.getBlockHash();
}
	
//Function to verify chain
public static boolean verifyTransaction(Block block)
{
	for(int i=1;i<blockchain.size();i++)
	{
		if(!(blockchain.get(i).getPreviousHash()==blockchain.get(i-1).getBlockHash()))
		{
			return false;
		}

    }

    if(blockchain.size()>0) 
    {
        if (!(blockchain.get(blockchain.size()-1).getBlockHash() == block.getPreviousHash())) 
        {
			return false;
		}

    }
	return true;
}
 public static int expo(int a,int b,int c)
    {
    	int ans=1;
    	for(int i=0;i<b;i++)
    	{
    		ans=((ans%c)*(a%c))%c;
    	}
    	return ans;
    	
    }
    

public static Boolean zeroKnowledgeProof(int y1)
    {

        Random rand = new Random();
        Scanner sc=new Scanner(System.in);
        System.out.println("\nKindly verify yourself as a user");
        System.out.println("Zero Knowledge Proof");
        System.out.println("Choose a random number between 0 and 9(r): ");
		int r = sc.nextInt();
        System.out.println("computing h=(2^r)(mod p) [ h=(2*" + r + ")(mod " + p + ") ]");
		int h = ((((int)Math.pow(2,r)) % p) + p) % p;
		System.out.println(" h : "  +  h);
		// change to variables i.e p = 11 */
        //int h=sc.nextInt();
        //System.out.println("h is "+ h );
        int b=rand.nextInt(2);
        System.out.println("Random bit(b) is: "+ b );
        System.out.println("compute  s=(r+ b*x)mod(10).Here x is password(known): ");
		// print with the calculated values */
        int s=sc.nextInt();
		System.out.println("s is : " + s);
        int val1=expo(2,s,11);
        int val2=(h*expo(y1,b,11))%11;
        //int val3=(h*expo(y2,b,11))%11;A

        if(val1==val2)
        {
             System.out.println("Zero Knowledge Proof Successful.You are verified as registered user\n");
             return true;
        }
       
        else
        {
            System.out.println("Zero Knowledge Proof Failed.Please try again\n");
            return false;
        }
    }
       
}