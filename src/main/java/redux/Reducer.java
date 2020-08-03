package redux;

import java.util.Map;

import redux.Redux.ReduxAction;

public interface Reducer {

	public Map<String,Integer> reduce(Map<String,Integer> state, ReduxAction action);

}
