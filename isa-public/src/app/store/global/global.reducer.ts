import { createReducer, on } from "@ngrx/store";
import * as GlobalActions from "./global.actions";

export interface GlobalState {
  loading: boolean;
  darkTheme: boolean;
}

const initialState: GlobalState = {
  loading: true,
  darkTheme: localStorage.getItem('darkTheme') == undefined || localStorage.getItem('darkTheme') == 'true'
}

export const globalReducer = createReducer(
  initialState,
  on(GlobalActions.activateLoading,    (state, _)        => ({...state, loading: true })),
  on(GlobalActions.deactivateLoading,  (state, _)        => ({...state, loading: false })),
  on(GlobalActions.setDarkTheme,       (state, payload)  => ({...state, darkTheme: payload.darkTheme }))
);