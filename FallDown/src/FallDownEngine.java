import java.awt.*;
import java.util.*;

public class FallDownEngine
{
	public static final double GRAVITY = .5;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	public static final int BRICK_LAYER_DELAY = 100;
	public static final int SPEED_UP_DELAY = 20;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector<Scenario.Builder> scenario = new Vector();
	private Ball ball;
	private int brickSpeed = 2;
	private int brickDelay = 0;
	private int speedDelay = 0;
	private int points = -1;
	
	private Save savepoint = new Save();
	

	public FallDownEngine()
	{
		ball = Ball.getInstance(WIDTH/2, HEIGHT/2);
		createBrickLayer();
	}

	public void createBrickLayer()
	{
		int hole = (int)((WIDTH/Scenario.WIDTH)*Math.random());
		for(int i = 0; i < (WIDTH/Scenario.WIDTH); i++)
		{
			if(i != hole)
			{
				scenario.add(new Scenario.Builder(i*Scenario.WIDTH+Scenario.WIDTH/2, HEIGHT+Scenario.HEIGHT));
			}
		}
		points++;
	}

	public void removeOldBricks()
	{
		for(int i = 0; i < scenario.size(); i++)
		{
			if(scenario.get(i).getLocation().getY() < 0)
			{
				scenario.remove(i);
				i--;
			}
		}
	}

	public void moveBricks()
	{
		for(int i = 0; i < scenario.size(); i++)
		{
			scenario.add(i, scenario.get(i).move(0,-brickSpeed));
			scenario.remove(i+1);
		}
	}

	public void affectBall()
	{
		for(int i = 0; i < scenario.size(); i++)
		{
			ball = scenario.get(i).affect(ball);
		}
		ball = ball.accelerate(0, GRAVITY);
		if(ball.getLocation().getY() > HEIGHT)
			ball = ball.setPosition((int)ball.getLocation().getX(), HEIGHT);
	}

	public void moveLeft()
	{
		ball = ball.moveLeft();
		while(ball.getLocation().getX() < 0)
			ball = ball.moveRight();
	}

	public void moveRight()
	{
		ball = ball.moveRight();
		while(ball.getLocation().getX() > WIDTH)
			ball = ball.moveLeft();
	}

	public void update()
	{
		if(ball.getLocation().getY() >= -Ball.RADIUS)
		{
			ball = ball.move();
			moveBricks();
			removeOldBricks();
			brickDelay = brickDelay+brickSpeed;
			if(brickDelay > BRICK_LAYER_DELAY)
			{
				brickDelay = 0;
				speedDelay++;
				if(speedDelay > SPEED_UP_DELAY)
				{
					speedDelay = 0;
					brickSpeed++;
				}
				createBrickLayer();
			}
			affectBall();
		}
	}

	public void draw(Graphics g)
	{
		if(ball.getLocation().getY() < -Ball.RADIUS)
		{
			g.setColor(Color.BLUE);
			g.drawString("You Lose", WIDTH/2-27, HEIGHT/2);
		}
		else
			ball.draw(g);
		for(int i = 0; i < scenario.size(); i++)
			scenario.get(i).draw(g);
		g.setColor(Color.BLUE);
		g.drawString("Points: "+points, 10, 20);
		savepoint.addMemento(new Memento(points));
	}
	
	public int[] back() throws Exception
	{
		Memento m = savepoint.back();
		points = m.getPontuation();
		
		return new int[] {points};
	}
	
	public int[] goin() throws Exception
	{
		Memento m = savepoint.goin();
		points = m.getPontuation();
		return new int[] {points};
	}
	
}