
public class users {
	
	private String name;
	private String password;
	
	void setPass(String pass)
	{
		this.password = pass;
	}
	void setName(String name)
	{
		this.name = name;
	}
	String getPass()
	{
		return this.password ;
	}
	String getName()
	{
		return this.name ;
	}
	boolean validate(String a,String b)
	{
		if(a.equals(b))
			return true;
		return false;
	}
}
