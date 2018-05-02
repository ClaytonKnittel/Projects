package oneDayProjects.wuggle.graphics;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Layout {
	
	private Map<Clas, Rectangle2D.Float> map;
	
	public Layout() {
		map = new HashMap<>();
	}
	
	public void add(Class<? extends Drawable> c, Rectangle2D.Float layout) {
		map.put(new Clas(c), layout);
	}
	
	public void add(Class<? extends Drawable> c, Rectangle2D.Float layout, int id) {
		map.put(new Clas(c, id), layout);
	}
	
	public void clear() {
		map.clear();
	}
	
	public Rectangle2D.Float get(Drawable d) {
		return map.get(new Clas(d.getClass(), d.id()));
	}
	
	private class Clas {
		Class<? extends Drawable> clas;
		boolean requireId;
		int id;
		
		public Clas(Class<? extends Drawable> clas, int id) {
			this.clas = clas;
			requireId = true;
			this.id = id;
		}
		
		public Clas(Class<? extends Drawable> clas) {
			this.clas = clas;
			requireId = false;
		}
		
		@Override
		public int hashCode() {
			return clas.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof Layout.Clas))
				return false;
			Clas c = (Layout.Clas) o;
			if (requireId && c.requireId)
				return clas.equals(c.clas) && id == c.id;
			return clas == c.clas;
		}
	}
	
}
