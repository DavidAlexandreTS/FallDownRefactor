import java.util.ArrayList;
import java.util.List;

public class Save
{
	private List<Memento> mementos = new ArrayList<Memento>();
	private int cont = 1;
	
	public void addMemento(Memento m)
	{
		mementos.add(m);
	}
	
	public Memento back() throws Exception
	{
		if(mementos.size() - cont > 0)
		{
			cont ++;
			return mementos.get(mementos.size() - cont);
		}
		throw new Exception("Cant Back!");	
	}
	
	public Memento goin() throws Exception
	{
		if(cont > 1)
		{
			cont --;
			return mementos.get(mementos.size() - cont);
		}
		throw new Exception("Cant Go!");
	}
}
