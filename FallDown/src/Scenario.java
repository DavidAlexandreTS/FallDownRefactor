import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Scenario
{
	public static final int WIDTH = 50;
	public static final int HEIGHT = 25;
	
	@SuppressWarnings("unused")
	private final int x;
	@SuppressWarnings("unused")
	private final int y;

	private Scenario(Builder builder)
	{
		this.x = builder.x;
		this.y = builder.y;
	}

	public static class Builder
	{
		private final int x;
		private final int y;
		
		public Builder(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		

		public Builder move(int dx, int dy)
		{
			return new Builder(x+dx, y+dy);
		}

		public Rectangle getBounds()
		{
			return new Rectangle(x-WIDTH/2, y-HEIGHT/2, WIDTH, HEIGHT);
		}

		public boolean intersects(Ball b)
		{
			return b.getBounds().intersects(getBounds());
		}

		public Ball affect(Ball b)
		{
			Ball ret = b;
			while(intersects(ret))
				ret = ret.move(0, -1);

			return ret;
		}

		public Point getLocation()
		{
			return new Point(x,y);
		}

		public void draw(Graphics g)
		{
			g.setColor(Color.BLUE);
			g.fillRect(x-WIDTH/2, y-HEIGHT/2, WIDTH-1, HEIGHT-1);
		}
	}
	
}
