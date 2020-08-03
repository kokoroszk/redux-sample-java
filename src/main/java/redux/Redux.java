package redux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Component;
import main.Main.MyReducer.ReduxActionType;

public class Redux {

	private Map<String,Integer> state = new HashMap<>();

	private Reducer reducer;

	private List<SelectHook> hooks = new ArrayList<>();

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
		this.notifyToHooks();
	}

	public Integer useSelector(Component component, Selector selector) {

		// selecterを用いて、stateから値を取得
		Integer val = selector.Select(this.state);

		// stateが更新された時に動くhookとして登録（未登録の場合のみ）
		SelectHook hook = new SelectHook(component, selector, val);
		if (this.hooks.stream().noneMatch(h -> h.equals(hook))) {
			this.hooks.add(hook);
		}

		return val;
	}

	// stateが更新された場合に呼び出される。
	// 各hookごとに値を確認して、差分があれば再描画する。
	public void notifyToHooks() {
		for (SelectHook hook: this.hooks) {
			Integer val = hook.selector.Select(this.state);

			// 値が更新されてなければ何もしない。更新されていれば、描画し直す。
			if (val.equals(hook.lastSelected)) {
				return;
			} else {
				hook.lastSelected = val;
				hook.component.render();
			}
		}
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

	public static class SelectHook {
		public final Component component;
		public final Selector selector;
		public Integer lastSelected;
		public SelectHook(Component component, Selector selector, Integer lastSelected) {
			this.component = component;
			this.selector = selector;
			this.lastSelected = lastSelected;
		}

		public boolean equals(SelectHook obj) {
			return this.component == obj.component && this.selector == obj.selector;
		}
	}
}
