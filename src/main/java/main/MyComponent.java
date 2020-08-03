package main;

import redux.Redux;

public class MyComponent implements Component {

	private Redux redux;

	public void connect(Redux redux) {
		this.redux = redux;
	}

	// 擬似的に、描画=valをprint とする。
	public void render() {
		Integer val = redux.useSelector(this, (state) -> state.get("value"));
		System.out.println(val);
	}

}
