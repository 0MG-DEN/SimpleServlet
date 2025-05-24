package simpleservlet.util;

public class Pair<Item1, Item2> {
	public final Item1 item1;
	public final Item2 item2;

	public Pair(Item1 item1, Item2 item2) {
		this.item1 = item1;
		this.item2 = item2;
	}

	public int hashCode() {
		final int prime = 31;
		final int hashCode1 = (item1 == null) ? 0 : item1.hashCode();
		final int hashCode2 = (item2 == null) ? 0 : item2.hashCode();
		int result = 1;
		result = prime * result + hashCode1;
		result = prime * result + hashCode2;
		return result;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		Class<?> thisClass = this.getClass();
		Class<?> objClass = obj.getClass();
		if (!thisClass.equals(objClass))
			return false;

		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (this.item1 == null && other.item1 != null)
			return false;
		if (this.item1 != null && !this.item1.equals(other.item1))
			return false;
		if (this.item2 == null && other.item2 != null)
			return false;
		if (this.item2 != null && !this.item2.equals(other.item2))
			return false;
		return true;
	}
}
