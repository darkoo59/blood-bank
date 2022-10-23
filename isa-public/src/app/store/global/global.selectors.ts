import { createSelector } from "@ngrx/store";
import { AppState } from "..";
import { GlobalState } from "./global.reducer";

const globalFeature = (state: AppState) => state.global;

export const selectLoading    = createSelector(globalFeature, (state: GlobalState) => state.loading);
export const selectDarkTheme  = createSelector(globalFeature, (state: GlobalState) => state.darkTheme);