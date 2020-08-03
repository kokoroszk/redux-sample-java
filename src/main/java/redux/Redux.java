package redux;

import java.util.HashMap;
import java.util.Map;

import main.Main.MyReducer.ReduxActionType;

public class Redux {

	private Map<String,Integer> state = new HashMap<>();

	private Reducer reducer;

	private static Map<String, Integer> initialState;
	static {
		initialState = new HashMap<>();
		initialState.put("value", 0);
	}

	public Redux(Reducer reducer) {
		this.reducer = reducer;
		this.state = Redux.initialState;
	}

	public void dispatch(ReduxAction action) {
		this.state = this.reducer.reduce(this.state, action);
	}

	public Integer get(String key) {
		return state.get(key);
	}

	public static class ReduxAction {
		public final ReduxActionType type;
		public final Integer payload;
		public ReduxAction(ReduxActionType type, Integer payload) {
			this.type = type;
			this.payload = payload;
		}
	}
}
