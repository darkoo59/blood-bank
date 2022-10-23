import { createAction, props } from "@ngrx/store";

export const activateLoading    = createAction('[App] Activate Loading');
export const deactivateLoading  = createAction('[App] Deactivate Loading');
export const setDarkTheme       = createAction('[App] Set Dark Theme', props<{ darkTheme: boolean }>());
export const loadApp            = createAction('[App] Load App');