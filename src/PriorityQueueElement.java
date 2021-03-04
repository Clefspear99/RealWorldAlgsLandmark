public class PriorityQueueElement<T> {

	public int priority;
	public T item;

	public PriorityQueueElement(T item, int priority) {
		this.item = item;
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public T getItem() {
		return item;
	}

	public String toString() {
		return "<" + item.toString() + ", " + priority + ">";
	}
}