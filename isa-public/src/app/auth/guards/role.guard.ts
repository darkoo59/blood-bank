import { Injectable } from "@angular/core";
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable, take, map } from "rxjs";
import { UserService } from "src/app/services/user.service";

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private m_UserService: UserService, private m_Router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.m_UserService.m_Data$.pipe(take(1), map(userData => {
      if(userData){
        const routeRoles = route.data['roles'];
        if(routeRoles?.length > 0){
          for (let r of userData?.roles){
            if(routeRoles?.includes(r.name)){
              return true;
            }
          }
          return this.m_Router.createUrlTree(['/error']);
        }
        return true;
      }
      return this.m_Router.createUrlTree(['/error']);
    }));
  }
}