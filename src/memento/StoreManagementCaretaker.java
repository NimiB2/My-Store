package memento;

import java.util.Stack;

public class StoreManagementCaretaker {
	private Stack<StoreManagementMemento> mementos = new Stack<>();

	public void addMemento(StoreManagementMemento memento) {
		mementos.push(memento);
	}

	public StoreManagementMemento getMemento() {
		if (!mementos.isEmpty()) {
			return mementos.pop();
		}
		return null;
	}
}
