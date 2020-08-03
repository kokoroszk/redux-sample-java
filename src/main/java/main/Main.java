package main;

import java.util.HashMap;
import java.util.Map;

import main.Main.MyReducer.ReduxActionType;
import redux.Reducer;
import redux.Redux;
import redux.Redux.ReduxAction;

public class Main {

	public static void main(String[] args) {

		// redux(state管理ライブラリ)に、state操作用の関数(reducer)をプレゼントする。
		Redux redux = new Redux(Main.createReducer());

		// 初期値が出力される
		System.out.println(redux.get("value"));

		// hooksを利用しているコンポーネントを描画する。
		MyComponent c = new MyComponent();
		c.connect(redux); // reduxとcomponentをconnectする。

		System.out.println("-- render結果として、stateの初期値(0)が出力される");
		c.render();

		// state操作用の関数で定義されている通りに、stateが変更される
		// dispatchの度にhooksが動いて、stateが更新された場合は render() が実行される
		System.out.println("-- dispatch 1: +100なので、100と出力される");
		redux.dispatch(new ReduxAction(ReduxActionType.Plus, 100));

		System.out.println("-- dispatch 2: -1なので、99と出力される");
		redux.dispatch(new ReduxAction(ReduxActionType.Minus, 1));

		System.out.println("-- dispatch 1: +0なので、値が変更されておらず、renderされない");
		redux.dispatch(new ReduxAction(ReduxActionType.Plus, 0));
	}

	public static Reducer createReducer() {
		return new MyReducer();
	}

	public static class MyReducer implements Reducer {

		public Map<String, Integer> reduce(Map<String, Integer> state, ReduxAction action) {

			switch (action.type.value) {
				case "plus":
					int val1 = state.get("value");
					val1 = val1 + action.payload;
					Map<String, Integer> newState1 = new HashMap<>();
					newState1.put("value", val1);
					return newState1;

				case "minus":
					int val2 = state.get("value");
					val2 = val2 - action.payload;
					Map<String, Integer> newState2 = new HashMap<>();
					newState2.put("value", val2);
					return newState2;

				default:
					return state;
			}
		}

		public static enum ReduxActionType {
			Plus("plus"),
			Minus("minus");

			ReduxActionType(String value) {
				this.value = value;
			}

			public final String value;
		}
	}

}
