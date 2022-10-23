import { ActionReducerMap } from "@ngrx/store";
import { GlobalEffects } from "./global/global.effects";
import { globalReducer, GlobalState } from "./global/global.reducer";

export interface AppState {
  global: GlobalState
}

export const reducers: ActionReducerMap<AppState, any> = {
  global: globalReducer,
};

export const effects = [GlobalEffects]